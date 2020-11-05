package com.ofdbox.convertor.img;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.fontbox.ttf.GlyphData;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.fontbox.util.BoundingBox;
import org.ujmp.core.Matrix;

import com.ofdbox.convertor.utils.FontUtils;
import com.ofdbox.convertor.utils.ImageUtils;
import com.ofdbox.core.*;
import com.ofdbox.core.utils.FormatUtils;
import com.ofdbox.core.utils.MatrixUtils;
import com.ofdbox.core.utils.Tuple2;
import com.ofdbox.core.xmlobj.base.page.CT_PageBlock;
import com.ofdbox.core.xmlobj.base.page.NLayer;
import com.ofdbox.core.xmlobj.base.page.NTemplate;
import com.ofdbox.core.xmlobj.base.page.object.NImageObject;
import com.ofdbox.core.xmlobj.base.page.object.NPathObject;
import com.ofdbox.core.xmlobj.base.page.object.NTextObject;
import com.ofdbox.core.xmlobj.enums.LayerType;
import com.ofdbox.core.xmlobj.object.text.CT_CGTransform;
import com.ofdbox.core.xmlobj.object.text.CT_Font;
import com.ofdbox.core.xmlobj.object.text.NTextCode;
import com.ofdbox.core.xmlobj.pagedesc.color.CT_Color;
import com.ofdbox.core.xmlobj.st.ST_Box;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: 张家尧
 * @create: 2020/10/01 14:14
 */
@Slf4j
public class Ofd2Img {

    private Config config = new Config();

    public Config getConfig() {
        return config;
    }

    public BufferedImage toImage(Page page, int dpi) {

        ST_Box box = page.getPhysicalBox();
        BufferedImage image = new BufferedImage(box.getW().intValue() * dpi, box.getH().intValue() * dpi,
                        BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) image.getGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));

        renderPage(graphics, page, dpi);

        return image;

    }

    public BufferedImage toImage(Page page, int dpi, BufferedImage seal) {
        ST_Box box = page.getPhysicalBox();
        BufferedImage image = new BufferedImage(box.getW().intValue() * dpi, box.getH().intValue() * dpi,
                        BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) image.getGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
        // 添加印章
        Graphics2D invoice = (Graphics2D) image.getGraphics();
        invoice.drawImage(seal, null, image.getWidth() / 2 - seal.getWidth() / 2, image.getHeight() / 18);
        renderPage(graphics, page, dpi);

        return image;

    }

    private void renderPage(Graphics2D graphics, Page page, int dpi) {
        List<NLayer> bodyLayer = new ArrayList<>();
        List<NLayer> foreLayer = new ArrayList<>();
        List<NLayer> backLayer = new ArrayList<>();

        List<Template> bodyTemplate = new ArrayList<>();
        List<Template> foreTemplate = new ArrayList<>();
        List<Template> backTemplate = new ArrayList<>();

        for (NLayer layer : page.getXPage().getContent().getLayers()) {
            if (layer.getType() == null || layer.getType() == LayerType.Body) {
                bodyLayer.add(layer);
            } else if (layer.getType() == LayerType.Foreground) {
                foreLayer.add(layer);
            } else if (layer.getType() == LayerType.Background) {
                backLayer.add(layer);
            }
        }
        if (page.getXPage().getTemplates() != null) {
            for (NTemplate nTemplate : page.getXPage().getTemplates()) {
                Template template = page.getDocument().getTemplate(nTemplate.getTemplateId().getId());
                if (template == null)
                    continue;
                LayerType type = nTemplate.getZOrder();
                if (type == null || type == LayerType.Body) {
                    bodyTemplate.add(template);
                } else if (type == LayerType.Foreground) {
                    foreTemplate.add(template);
                } else if (type == LayerType.Background) {
                    backTemplate.add(template);
                }
            }
        }
        // 背景层模版
        for (Template template : backTemplate)
            renderPage(graphics, template, dpi);
        // 背景层
        for (NLayer layer : backLayer)
            renderContent(graphics, page.getDocument(), layer, dpi);
        // 正文层模版
        for (Template template : bodyTemplate)
            renderPage(graphics, template, dpi);
        // 正文层
        for (NLayer layer : bodyLayer)
            renderContent(graphics, page.getDocument(), layer, dpi);
        // 前景层模版
        for (Template template : foreTemplate)
            renderPage(graphics, template, dpi);
        // 前景层
        for (NLayer layer : foreLayer)
            renderContent(graphics, page.getDocument(), layer, dpi);

    }


    private void renderContent(Graphics2D graphics, Document document, CT_PageBlock pageBlock, int dpi) {
        new ContentWalker(pageBlock) {
            @Override
            public void onText(NTextObject nTextObject) {
                ST_Box boundary = nTextObject.getBoundary();
                Matrix m = MatrixUtils.base();
                // 初始化矩阵
                graphics.setTransform(MatrixUtils.createAffineTransform(m));

                m = m.mtimes(MatrixUtils.create(dpi, 0, 0, dpi, 0, 0));
                graphics.transform(MatrixUtils.createAffineTransform(m));
                // 绘制外框
                graphics.setClip(new Rectangle2D.Double(boundary.getX(), boundary.getY(), boundary.getW(),
                                boundary.getH()));
                renderBoundary(graphics, boundary);


                if (nTextObject.getStrokeColor() != null) {
                    CT_Color ct_color = nTextObject.getStrokeColor();
                    Color color = new Color(Float.valueOf(ct_color.getValue()[0]) / 255,
                                    Float.valueOf(ct_color.getValue()[1]) / 255,
                                    Float.valueOf(ct_color.getValue()[2]) / 255);
                    graphics.setColor(color);
                } else {
                    graphics.setColor(Color.BLACK);
                }

                log.debug("--------------------------------TextObject--------------------------------");

                // 准备字体
                boolean isEmbedded = false;
                boolean loadErr = false;
                CT_Font font = document.getFont(nTextObject.getFont().getId());
                TrueTypeFont typeFont = null;
                if (font.getFontFile() != null) {
                    isEmbedded = true;
                    OTFParser parser = new OTFParser(true);
                    try {
                        String loc = font.getFontFile().getFullLoc();
                        byte[] fontData = document.getOfd().getFileManager().readBytes(loc);
                        if (fontData != null) {
                            typeFont = parser.parse(new ByteArrayInputStream(fontData));
                        } else {
                            log.error("找不到字体：" + loc);
                        }
                    } catch (IOException e) {
                        log.error("加载字体出错：" + e.getMessage());
                    }
                } else {
                    typeFont = FontUtils.loadSystemFont(font.getFamilyName(), font.getFontName());
                    if (typeFont == null) {
                        log.warn("找不到系统字体：" + font.getFamilyName() + " " + font.getFontName());
                    }
                }
                if (typeFont == null) {
                    loadErr = true;
                    typeFont = getDefaultFont();
                }

                log.debug("字体是否内嵌：" + isEmbedded);
                if (isEmbedded) {
                    log.debug("字体是否内嵌：" + typeFont.getTables().stream().map(ttfTable -> {
                        return ttfTable.getTag() + " ";
                    }).collect(Collectors.joining()));
                }
                log.debug("字体是否加载失败：" + loadErr);
                if (loadErr) {
                    log.debug("原字体：" + loadErr);
                }
                try {
                    log.debug("字体名称：" + typeFont.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Double[] ctm = nTextObject.getCtm();
                BoundingBox fontBox = null;
                List<Number> fontMatrix = null;
                try {
                    fontBox = typeFont.getFontBBox();
                    fontMatrix = typeFont.getFontMatrix();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }


                int globalPoint = 0; // 字符计数，用来比较是否跟某个Transforms起始点重合
                int transPoint = -1; // 下一个Transforms，-1表示不存在
                // int codePosition=-1; //下一个Transforms的位置
                if (nTextObject.getTransforms() != null && nTextObject.getTransforms().size() >= 1) {
                    transPoint = 0;
                    // codePosition = nTextObject.getTransforms().get(transPoint).getCodePosition();
                    nTextObject.getTransforms().stream().forEach(transform -> {
                        if (transform.getCodePosition() == null) {
                            transform.setCodePosition(0);
                        }
                    });
                    nTextObject.getTransforms().sort((t1, t2) -> {
                        return t1.getCodePosition() - t2.getCodePosition();
                    });
                }
                for (int i = 0; i < nTextObject.getTextCodes().size(); i++) {
                    NTextCode textCode = nTextObject.getTextCodes().get(i);
                    int deltaOffset = -1;
                    log.debug("------------------------------------------");
                    log.debug("TextCode: " + textCode.getContent());
                    log.debug("DeltaX:" + textCode.getDeltaX());
                    log.debug("DeltaY:" + textCode.getDeltaY());
                    log.debug("DeltaY:" + textCode.getDeltaY());
                    List<Double> deltaX = FormatUtils.parseDelta(textCode.getDeltaX());
                    List<Double> deltaY = FormatUtils.parseDelta(textCode.getDeltaY());
                    Double x = textCode.getX();
                    Double y = textCode.getY();
                    for (int j = 0; j < textCode.getContent().length(); j++) {
                        // 没有Transforms或者还没到Transforms
                        if (transPoint == -1 || globalPoint < nTextObject.getTransforms().get(transPoint)
                                        .getCodePosition()) {
                            if (deltaOffset != -1) {
                                x += deltaX == null ? 0.0 : deltaX.get(deltaOffset);
                                y += deltaY == null ? 0.0 : deltaY.get(deltaOffset);
                            }

                            char c = textCode.getContent().charAt(j);
                            log.debug(String.format("编码索引 <%s> DeltaX:%s DeltaY:%s", c, x, y));
                            try {
                                int gid = typeFont.getUnicodeCmap().getGlyphId((int) c);
                                typeFont.getFontMatrix();
                                GlyphData glyphData = typeFont.getGlyph().getGlyph(gid);
                                Shape shape = glyphData.getPath();
                                log.debug(String.format("字形Shape %s", shape));
                                renderChar(graphics, shape, nTextObject, nTextObject.getCtm(),
                                                nTextObject.getBoundary(), x, y, nTextObject.getSize(), dpi,
                                                fontMatrix);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            globalPoint++;
                            deltaOffset++;
                        } else {
                            CT_CGTransform transform = nTextObject.getTransforms().get(transPoint);
                            log.debug("字形变换：" + transform);
                            for (Integer glyph : transform.getGlyphs()) {

                                if (deltaOffset != -1) {
                                    x += deltaX == null ? 0.0 : deltaX.get(deltaOffset);
                                    y += deltaY == null ? 0.0 : deltaY.get(deltaOffset);
                                }
                                log.debug(String.format("字形索引 <%s> DeltaX:%s DeltaY:%s", glyph, x, y));;

                                try {
                                    if (loadErr) {
                                        // 加载字体失败，字形索引无效
                                        continue;
                                    }
                                    typeFont.getGlyph().getGlyphs();
                                    GlyphData glyphData = typeFont.getGlyph().getGlyph(glyph);
                                    if (glyphData != null) {
                                        Shape shape = glyphData.getPath();
                                        renderChar(graphics, shape, nTextObject, nTextObject.getCtm(),
                                                        nTextObject.getBoundary(), x, y, nTextObject.getSize(), dpi,
                                                        fontMatrix);
                                        log.debug(String.format("字形Shape %s", shape));
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                deltaOffset++;
                            }
                            if (transPoint + 1 >= nTextObject.getTransforms().size()) {
                                transPoint = -1;
                            } else {
                                transPoint++;
                            }
                            globalPoint += (transform.getCodeCount() != null ? transform.getCodeCount()
                                            : transform.getGlyphs().length);
                            j += (transform.getCodeCount() != null ? transform.getCodeCount()
                                            : transform.getGlyphs().length);
                        }
                    }
                    globalPoint += textCode.getContent().length();
                }
            }

            @Override
            public void onImage(NImageObject nImageObject) {
                // graphics.setBackground(null);
                OFDFile ofdFile = document.getMultiMedia(nImageObject.getResourceId().getId());
                try {
                    ST_Box boundary = nImageObject.getBoundary();

                    Matrix m = MatrixUtils.base();
                    // 初始化矩阵
                    graphics.setTransform(MatrixUtils.createAffineTransform(m));

                    m = m.mtimes(MatrixUtils.create(dpi, 0, 0, dpi, 0, 0));
                    graphics.transform(MatrixUtils.createAffineTransform(m));
                    // 绘制外框
                    graphics.setClip(new Rectangle2D.Double(boundary.getX(), boundary.getY(), boundary.getW(),
                                    boundary.getH()));
                    renderBoundary(graphics, boundary);


                    BufferedImage targetImg = ImageUtils.getBufferedImage(ofdFile);

                    if (nImageObject.getImageMask() != null) {
                        OFDFile maskFile = document.getMultiMedia(nImageObject.getImageMask().getId());
                        BufferedImage mask = ImageUtils.getBufferedImage(maskFile);
                        targetImg = ImageUtils.renderMask(targetImg, mask);
                    }


                    Double[] ctm = nImageObject.getCtm();

                    graphics.setTransform(MatrixUtils.createAffineTransform(MatrixUtils.base()));
                    // 把图片还原成1*1
                    Matrix matrix = MatrixUtils.create(Double.valueOf(1.0 / targetImg.getWidth()).floatValue(), 0, 0,
                                    Double.valueOf(1.0 / targetImg.getHeight()).floatValue(), 0, 0);

                    // CTM
                    matrix = matrix.mtimes(
                                    MatrixUtils.create(ctm[0].floatValue(), ctm[1].floatValue(), ctm[2].floatValue(),
                                                    ctm[3].floatValue(), ctm[4].floatValue(), ctm[5].floatValue()));
                    Tuple2<Double, Double> tuple2 = MatrixUtils.leftTop(matrix);


                    // 转换到世界坐标系
                    matrix = matrix.mtimes(MatrixUtils.create(1, 0, 0, 1, boundary.getX().floatValue(),
                                    boundary.getY().floatValue()));

                    matrix = matrix.mtimes(MatrixUtils.create(dpi, 0, 0, dpi, 0, 0));

                    matrix = matrix.mtimes(MatrixUtils.create(1, 0, 0, 1, tuple2.getFirst().floatValue(),
                                    tuple2.getSecond().floatValue()));


                    // 单位毫米转换成像素
                    // matrix=MatrixUtils.scale(matrix,dpi, dpi);


                    graphics.drawImage(targetImg, MatrixUtils.createAffineTransform(matrix), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onPath(NPathObject nPathObject) {

                graphics.setBackground(null);
                ST_Box boundary = nPathObject.getBoundary();

                Matrix matrix = MatrixUtils.base();
                graphics.setTransform(MatrixUtils.createAffineTransform(matrix));

                matrix = matrix.mtimes(MatrixUtils.create(dpi, 0, 0, dpi, 0, 0));
                graphics.transform(MatrixUtils.createAffineTransform(matrix));

                // 设置剪裁区
                graphics.setClip(new Rectangle2D.Double(boundary.getX(), boundary.getY(), boundary.getW(),
                                boundary.getH()));

                // 绘制外框
                renderBoundary(graphics, boundary);


                Double[] ctm = nPathObject.getCtm();

                matrix = MatrixUtils.base();
                graphics.setTransform(MatrixUtils.createAffineTransform(matrix));

                if (ctm != null) {
                    matrix = matrix.mtimes(
                                    MatrixUtils.create(ctm[0].floatValue(), ctm[1].floatValue(), ctm[2].floatValue(),
                                                    ctm[3].floatValue(), ctm[4].floatValue(), ctm[5].floatValue()));
                }
                // 转换到世界坐标系
                matrix = matrix.mtimes(MatrixUtils.create(1, 0, 0, 1, boundary.getX().floatValue(),
                                boundary.getY().floatValue()));
                // 单位毫米转换成像素
                matrix = matrix.mtimes(MatrixUtils.create(dpi, 0, 0, dpi, 0, 0));

                graphics.transform(MatrixUtils.createAffineTransform(matrix));

                Path2D path = new Path2D.Double();
                path.moveTo(0, 0);
                String[] s = nPathObject.getAbbreviatedData().split("\\s+");
                int i = 0;
                while (i < s.length) {
                    String operator = s[i];
                    switch (operator) {
                        case "S":
                        case "M":
                            path.moveTo(Double.valueOf(s[i + 1]), Double.valueOf(s[i + 2]));
                            i += 3;
                            break;
                        case "L":
                            path.lineTo(Double.valueOf(s[i + 1]), Double.valueOf(s[i + 2]));
                            i += 3;
                            break;
                        case "Q":
                            path.quadTo(Double.valueOf(s[i + 1]), Double.valueOf(s[i + 2]), Double.valueOf(s[i + 3]),
                                            Double.valueOf(s[i + 4]));
                            i += 5;
                            break;
                        case "B":
                            path.curveTo(Double.valueOf(s[i + 1]), Double.valueOf(s[i + 2]), Double.valueOf(s[i + 3]),
                                            Double.valueOf(s[i + 4]), Double.valueOf(s[i + 5]),
                                            Double.valueOf(s[i + 6]));
                            i += 7;
                            break;
                        case "A":
                            // path.append(new
                            // Arc2D.Double(Double.valueOf(s[i+1]),Double.valueOf(s[i+2]),Double.valueOf(s[i+3]),Double.valueOf(s[i+4]),Double.valueOf(s[i+5]),Double.valueOf(s[i+3]),-1),true);
                            i += 7;
                            break;
                        case "C":
                            path.closePath();
                            i++;
                            break;
                    }
                }


                if (nPathObject.getStroke() == null || nPathObject.getStroke()) {
                    CT_Color ct_color = nPathObject.getStrokeColor();
                    Color color = null;
                    if (ct_color != null) {
                        color = new Color(Float.valueOf(ct_color.getValue()[0]) / 255,
                                        Float.valueOf(ct_color.getValue()[1]) / 255,
                                        Float.valueOf(ct_color.getValue()[2]) / 255);
                    } else {
                        color = Color.black;
                    }
                    graphics.setColor(color);
                    graphics.setStroke(new BasicStroke(nPathObject.getLineWidth().floatValue()));
                    graphics.draw(path);
                }


                if (nPathObject.getFill() != null && nPathObject.getFill()) {
                    CT_Color ct_color = nPathObject.getFillColor();
                    Color color = null;
                    if (ct_color != null) {
                        color = new Color(Float.valueOf(ct_color.getValue()[0]) / 255,
                                        Float.valueOf(ct_color.getValue()[1]) / 255,
                                        Float.valueOf(ct_color.getValue()[2]) / 255);
                    } else {
                        graphics.setBackground(null);
                    }
                    graphics.setColor(color);
                    graphics.fill(path);
                    graphics.setBackground(Color.white);
                }
            }
        }.walk();
    }


    /*
     * 渲染一个字符 字形图形 字体字形最大宽度 字体字形最大高度 ctm变换矩阵
     */
    private void renderChar(Graphics2D graphics, Shape shape, NTextObject nTextObject, Double[] ctm, ST_Box boundary,
                    Double deltaX, Double deltaY, Double fontSize, int dpi, List<Number> fontMatrix) {
        Matrix matrix = MatrixUtils.base();
        // matrix = MatrixUtils.move(matrix, -fontBox.getLowerLeftX(), -fontBox.getLowerLeftY());
        matrix = matrix.mtimes(MatrixUtils.create(fontMatrix.get(0).doubleValue(), fontMatrix.get(1).doubleValue(),
                        fontMatrix.get(2).doubleValue(), fontMatrix.get(3).doubleValue(),
                        fontMatrix.get(4).doubleValue(), fontMatrix.get(5).doubleValue()));
        // matrix = MatrixUtils.scale(matrix, 1.0 / fontBox.getWidth(), 1.0 / fontBox.getWidth());
        matrix = MatrixUtils.imageMatrix(matrix, 0, 1, 0);

        if (ctm != null) {
            matrix = matrix.mtimes(MatrixUtils.ctm(ctm));
        }

        matrix = MatrixUtils.scale(matrix, fontSize, fontSize);
        matrix = MatrixUtils.move(matrix, deltaX * (ctm == null ? 1 : ctm[0]), deltaY * (ctm == null ? 1 : ctm[3]));

        matrix = MatrixUtils.move(matrix, boundary.getX(), boundary.getY());
        matrix = MatrixUtils.scale(matrix, dpi, dpi);

        graphics.setClip(null);
        graphics.setTransform(MatrixUtils.createAffineTransform(matrix));
        graphics.setStroke(new BasicStroke(0.1f));
        graphics.setColor(Color.BLACK);
        graphics.setBackground(Color.white);
        // graphics.draw(shape);
        // graphics.fill(shape);

        /*
         * 文字默认填充 不勾边
         */

        if (nTextObject.getFill() == null || nTextObject.getFill() == true) {
            CT_Color ct_color = nTextObject.getFillColor();
            Color color = null;
            if (ct_color != null) {
                color = new Color(Float.valueOf(ct_color.getValue()[0]) / 255,
                                Float.valueOf(ct_color.getValue()[1]) / 255,
                                Float.valueOf(ct_color.getValue()[2]) / 255);
                graphics.setColor(color);
            } else {
                graphics.setColor(Color.BLACK);
            }
            graphics.fill(shape);
        }

        if (nTextObject.getStroke() != null && nTextObject.getStroke()) {
            CT_Color ct_color = nTextObject.getStrokeColor();
            if (ct_color == null) {
                graphics.setBackground(null);
                graphics.setColor(null);
            } else {
                Color color = new Color(Float.valueOf(ct_color.getValue()[0]) / 255,
                                Float.valueOf(ct_color.getValue()[1]) / 255,
                                Float.valueOf(ct_color.getValue()[2]) / 255);
                graphics.setColor(color);
            }
            graphics.draw(shape);
            graphics.setBackground(Color.white);
        }

    }

    private void renderBoundary(Graphics2D graphics, ST_Box st_box) {
        if (!this.config.drawBoundary) {
            return;
        }
        graphics.setClip(null);
        Color color = graphics.getColor();
        graphics.setColor(Color.RED);
        graphics.setStroke(new BasicStroke(0.2f));

        // 转int丢失精度，乘DPI后误差放大，这里上下取整，稍微画大一点
        graphics.drawRect(Double.valueOf(Math.floor(st_box.getX())).intValue(),
                        Double.valueOf(Math.floor(st_box.getY())).intValue(),
                        Double.valueOf(Math.ceil(st_box.getW())).intValue(),
                        Double.valueOf(Math.ceil(st_box.getH())).intValue());

        graphics.setColor(color);
    }


    private TrueTypeFont defaultFont = null;

    private TrueTypeFont getDefaultFont() {
        if (defaultFont == null) {
            synchronized (this) {
                if (defaultFont == null) {
                    defaultFont = FontUtils.loadDefaultFont();
                }
            }
        }
        return defaultFont;
    }

    @Data
    public static class Config {
        private boolean drawBoundary;
    }
}
