package com.ofdbox.convertor.img;

import com.ofdbox.convertor.utils.FontUtils;
import com.ofdbox.convertor.utils.ImageUtils;
import com.ofdbox.core.ContentWalker;
import com.ofdbox.core.OFDFile;
import com.ofdbox.core.OFDReader;
import com.ofdbox.core.Template;
import com.ofdbox.core.model.Annotations;
import com.ofdbox.core.model.OFD;
import com.ofdbox.core.model.Signatures;
import com.ofdbox.core.model.document.Document;
import com.ofdbox.core.model.page.Page;
import com.ofdbox.core.utils.FormatUtils;
import com.ofdbox.core.utils.MatrixUtils;
import com.ofdbox.core.utils.Tuple2;
import com.ofdbox.core.xmlobj.annotation.NAnnot;
import com.ofdbox.core.xmlobj.annotation.XPageAnnot;
import com.ofdbox.core.xmlobj.base.page.CT_PageBlock;
import com.ofdbox.core.xmlobj.base.page.NLayer;
import com.ofdbox.core.xmlobj.base.page.NTemplate;
import com.ofdbox.core.xmlobj.enums.LayerType;
import com.ofdbox.core.xmlobj.graphic.CT_Path;
import com.ofdbox.core.xmlobj.object.image.CT_Image;
import com.ofdbox.core.xmlobj.object.text.CT_CGTransform;
import com.ofdbox.core.xmlobj.object.text.CT_Font;
import com.ofdbox.core.xmlobj.object.text.CT_Text;
import com.ofdbox.core.xmlobj.object.text.NTextCode;
import com.ofdbox.core.xmlobj.pagedesc.color.CT_Color;
import com.ofdbox.core.xmlobj.signature.NStampAnnot;
import com.ofdbox.core.xmlobj.st.ST_Box;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import com.ofdbox.signature.gm.Gm;
import com.ofdbox.signature.gm.v4.SES_Signature;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.fontbox.ttf.GlyphData;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.fontbox.util.BoundingBox;
import org.ujmp.core.Matrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return toImage(page, false, dpi);
    }

    private BufferedImage toImage(Page page, boolean transparentBackground, int dpi) {
        ST_Box box = page.getPhysicalBox();
        BufferedImage image = new BufferedImage(box.getW().intValue() * dpi, box.getH().intValue() * dpi,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
        if (transparentBackground) {
            graphics.setColor(Color.WHITE);
            image = graphics.getDeviceConfiguration().createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);
            graphics = (Graphics2D) image.getGraphics();
        } else {
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        }

        renderPage(graphics, page, dpi);
        renderAnnotations(graphics, page, dpi);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, config.stampOpacity));
        renderSignatures(graphics, page, dpi);

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

    private void renderAnnotations(Graphics2D graphics, Page page, int dpi) {

        Annotations annotations = page.getDocument().getAnnotations();
        if (annotations == null) return;
        List<NAnnot> nAnnots = new ArrayList<>();
        for (Annotations.AnnotationPage annotationPage : annotations.getAnnotationPages()) {
            if (annotationPage.getNAnnotationPage().getPageId().getId().equals(page.getNPage().getId().getId())) {
                XPageAnnot xPageAnnot = annotationPage.getXPageAnnot();
                nAnnots.addAll(xPageAnnot.getAnnots());
            }
        }
        for (NAnnot nAnnot : nAnnots) {
            renderContent(graphics, page.getDocument(), nAnnot.getAppearance(), dpi);
        }
    }

    private void renderSignatures(Graphics2D graphics, Page page, int dpi) {
        Signatures signatures = page.getDocument().getSignatures();
        if (signatures == null) return;
        for (Signatures.Signature signature : signatures.getSignatureList()) {
            if (signature.getXSignature().getSignedValue() == null) continue;
            if (!signature.getXSignature().getSignedValue().getLoc().endsWith(".dat")) continue;
            List<NStampAnnot> nStampAnnots = new ArrayList<>();
            for (NStampAnnot nStampAnnot : signature.getXSignature().getSignedInfo().getStampAnnots()) {
                if (nStampAnnot.getPageRef().getId().equals(page.getNPage().getId().getId())) {
                    nStampAnnots.add(nStampAnnot);
                }
            }
            if (nStampAnnots.size() <= 0) continue;
            ST_Loc datLoc = signature.getXSignature().getSignedValue();

            InputStream in = page.getDocument().getOfd().getFileManager().read(datLoc.getFullLoc());
            String picType = "";
            byte[] picData = null;
            try {
                SES_Signature ses_signature = Gm.readV4(in);
                picType = ses_signature.getToSign().getEseal().getESealInfo().getPicture().getType().getString();
                picData = ses_signature.getToSign().getEseal().getESealInfo().getPicture().getData().getOctets();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    com.ofdbox.signature.gm.v1.SES_Signature ses_signature = Gm.readV1(in);
                    picType = ses_signature.getToSign().getEseal().getESealInfo().getPicture().getType().getString();
                    picData = ses_signature.getToSign().getEseal().getESealInfo().getPicture().getData().getOctets();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            if (picData == null) continue;
            BufferedImage image = null;
            switch (picType) {
                case "png":
                case "jpg":
                case "gif":
                case "bmp":
//                case "svg":
                    try {
                        image = ImageIO.read(new ByteArrayInputStream(picData));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                case "ofd":
                    try {
                        File file = File.createTempFile("ofdbox-stamp-", ".ofd");
                        log.debug("ofd格式签章释放路径：" + file.getAbsoluteFile());
                        OutputStream out = new FileOutputStream(file);
                        IOUtils.copy(new ByteArrayInputStream(picData), out);
                        out.flush();
                        out.close();
                        OFDReader ofdReader = new OFDReader();
                        ofdReader.getConfig().setValid(false);
                        OFD ofd = ofdReader.read(file);
                        image = toImage(ofd.getDocuments().get(0).getPages().get(0), true, 30);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            if (image == null) continue;
            for (NStampAnnot nStampAnnot : nStampAnnots) {
                renderImage(graphics, image, nStampAnnot.getBoundary(), new Double[]{nStampAnnot.getBoundary().getW() + 0.0, 0.0, 0.0, nStampAnnot.getBoundary().getH() + 0.0, 0.0, 0.0}, dpi);
            }

        }
    }

    private void renderContent(Graphics2D graphics, Document document, CT_PageBlock pageBlock, int dpi) {
        new ContentWalker(pageBlock) {
            @Override
            public void onText(CT_Text ctText, ST_Box baseBoundary, Matrix baseMatrix) {
                ST_Box boundary = ctText.getBoundary();
                Matrix m = MatrixUtils.base();
                // 初始化矩阵
                graphics.setTransform(MatrixUtils.createAffineTransform(m));

                m = m.mtimes(MatrixUtils.create(dpi, 0, 0, dpi, 0, 0));
                graphics.transform(MatrixUtils.createAffineTransform(m));
                // 绘制外框
                graphics.setClip(new Rectangle2D.Double(boundary.getX(), boundary.getY(), boundary.getW(),
                        boundary.getH()));
                renderBoundary(graphics, boundary);


                if (ctText.getStrokeColor() != null) {
                    CT_Color ct_color = ctText.getStrokeColor();
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
                CT_Font font = document.getFont(ctText.getFont().getId());
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

                Double[] ctm = ctText.getCtm();
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
                if (ctText.getTransforms() != null && ctText.getTransforms().size() >= 1) {
                    transPoint = 0;
                    // codePosition = ctText.getTransforms().get(transPoint).getCodePosition();
                    ctText.getTransforms().stream().forEach(transform -> {
                        if (transform.getCodePosition() == null) {
                            transform.setCodePosition(0);
                        }
                    });
                    ctText.getTransforms().sort((t1, t2) -> {
                        return t1.getCodePosition() - t2.getCodePosition();
                    });
                }
                for (int i = 0; i < ctText.getTextCodes().size(); i++) {
                    NTextCode textCode = ctText.getTextCodes().get(i);
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
                        if (transPoint == -1 || globalPoint < ctText.getTransforms().get(transPoint)
                                .getCodePosition()) {
                            if (deltaOffset != -1) {
                                x += (deltaX == null || deltaX.size() < 0) ? 0.0 : (deltaOffset < deltaX.size() ? deltaX.get(deltaOffset) : deltaX.get(deltaX.size() - 1));
                                y += (deltaY == null || deltaY.size() < 0) ? 0.0 : (deltaOffset < deltaY.size() ? deltaY.get(deltaOffset) : deltaY.get(deltaY.size() - 1));
                            }

                            char c = textCode.getContent().charAt(j);
                            log.debug(String.format("编码索引 <%s> DeltaX:%s DeltaY:%s", c, x, y));
                            try {
                                int gid = typeFont.getUnicodeCmap().getGlyphId((int) c);
                                typeFont.getFontMatrix();
                                GlyphData glyphData = typeFont.getGlyph().getGlyph(gid);
                                Shape shape = glyphData.getPath();
                                log.debug(String.format("字形Shape %s", shape));
                                renderChar(graphics, shape, ctText, ctText.getCtm(),
                                        ctText.getBoundary(), x, y, ctText.getSize(), dpi,
                                        fontMatrix);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            globalPoint++;
                            deltaOffset++;
                        } else {
                            CT_CGTransform transform = ctText.getTransforms().get(transPoint);
                            log.debug("字形变换：" + transform);
                            for (Integer glyph : transform.getGlyphs()) {

                                if (deltaOffset != -1) {
                                    x += (deltaX == null || deltaX.size() < 0) ? 0.0 : (deltaOffset < deltaX.size() ? deltaX.get(deltaOffset) : deltaX.get(deltaX.size() - 1));
                                    y += (deltaY == null || deltaY.size() < 0) ? 0.0 : (deltaOffset < deltaY.size() ? deltaY.get(deltaOffset) : deltaY.get(deltaY.size() - 1));
                                }
                                log.debug(String.format("字形索引 <%s> DeltaX:%s DeltaY:%s", glyph, x, y));

                                try {
                                    if (loadErr) {
                                        // 加载字体失败，字形索引无效
                                        continue;
                                    }
                                    typeFont.getGlyph().getGlyphs();
                                    GlyphData glyphData = typeFont.getGlyph().getGlyph(glyph);
                                    if (glyphData != null) {
                                        Shape shape = glyphData.getPath();
                                        renderChar(graphics, shape, ctText, ctText.getCtm(),
                                                ctText.getBoundary(), x, y, ctText.getSize(), dpi,
                                                fontMatrix);
                                        log.debug(String.format("字形Shape %s", shape));
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                deltaOffset++;
                            }
                            if (transPoint + 1 >= ctText.getTransforms().size()) {
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
            public void onImage(CT_Image ctImage, ST_Box baseBoundary, Matrix baseMatrix) {
                // graphics.setBackground(null);
                OFDFile ofdFile = document.getMultiMedia(ctImage.getResourceId().getId());
                BufferedImage targetImg = null;
                try {
                    targetImg = ImageUtils.getBufferedImage(ofdFile);
                    if (ctImage.getImageMask() != null) {
                        OFDFile maskFile = document.getMultiMedia(ctImage.getImageMask().getId());
                        BufferedImage mask = ImageUtils.getBufferedImage(maskFile);
                        targetImg = ImageUtils.renderMask(targetImg, mask);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                ST_Box boundary = ctImage.getBoundary();
                Double[] ctm = ctImage.getCtm();

                renderImage(graphics, targetImg, boundary, ctm, dpi);

            }

            @Override
            public void onPath(CT_Path ctPath, ST_Box baseBoundary, Matrix baseMatrix) {

                graphics.setBackground(null);
                ST_Box boundary = ctPath.getBoundary();

                Matrix matrix = MatrixUtils.base();
                graphics.setTransform(MatrixUtils.createAffineTransform(matrix));

                matrix = matrix.mtimes(MatrixUtils.create(dpi, 0, 0, dpi, 0, 0));
                graphics.transform(MatrixUtils.createAffineTransform(matrix));

                // 设置剪裁区
//                graphics.setClip(new Rectangle2D.Double(boundary.getX(), boundary.getY(), boundary.getW(),
//                        boundary.getH()));

                // 绘制外框
                renderBoundary(graphics, boundary);


                Double[] ctm = ctPath.getCtm();

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

                matrix = matrix.mtimes(baseMatrix);

                // 单位毫米转换成像素
                matrix = matrix.mtimes(MatrixUtils.create(dpi, 0, 0, dpi, 0, 0));


                graphics.transform(MatrixUtils.createAffineTransform(matrix));

                Path2D path = new Path2D.Double();
                path.moveTo(0, 0);
                String[] s = ctPath.getAbbreviatedData().split("\\s+");
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
                        default:
                            i++;
                    }
                }


                if (ctPath.getStroke() == null || ctPath.getStroke()) {
                    CT_Color ct_color = ctPath.getStrokeColor();
                    Color color = null;
                    if (ct_color != null && ct_color.getValue() != null) {
                        color = new Color(Float.valueOf(ct_color.getValue()[0]) / 255,
                                Float.valueOf(ct_color.getValue()[1]) / 255,
                                Float.valueOf(ct_color.getValue()[2]) / 255);
                    } else {
                        color = Color.black;
                    }
                    graphics.setColor(color);
                    Double lineWidth = ctPath.getLineWidth();
                    if (lineWidth == null) {
                        log.error("LineWidth为空");
                        lineWidth = 0.4;
                    }
                    graphics.setStroke(new BasicStroke(lineWidth.floatValue()));
                    graphics.draw(path);
                }


                if (ctPath.getFill() != null && ctPath.getFill()) {
                    CT_Color ct_color = ctPath.getFillColor();
                    Color color = null;
                    if (ct_color != null && ct_color.getValue() != null) {
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
    private void renderChar(Graphics2D graphics, Shape shape, CT_Text ctText, Double[] ctm, ST_Box boundary,
                            Double deltaX, Double deltaY, Double fontSize, int dpi, List<Number> fontMatrix) {
        Matrix matrix = MatrixUtils.base();
        matrix = MatrixUtils.imageMatrix(matrix, 0, 1, 0);
        matrix = matrix.mtimes(MatrixUtils.create(fontMatrix.get(0).doubleValue(), fontMatrix.get(1).doubleValue(),
                fontMatrix.get(2).doubleValue(), fontMatrix.get(3).doubleValue(),
                fontMatrix.get(4).doubleValue(), fontMatrix.get(5).doubleValue()));
        matrix = MatrixUtils.scale(matrix, fontSize, fontSize);
        matrix = MatrixUtils.move(matrix, deltaX, deltaY);
        if (ctm != null) {
            matrix = matrix.mtimes(MatrixUtils.ctm(ctm));
        }
//

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

        if (ctText.getFill() == null || ctText.getFill() == true) {
            CT_Color ct_color = ctText.getFillColor();
            Color color = null;
            if (ct_color != null && ct_color.getValue() != null && ct_color.getValue().length >= 3) {
                color = new Color(Float.valueOf(ct_color.getValue()[0]) / 255,
                        Float.valueOf(ct_color.getValue()[1]) / 255,
                        Float.valueOf(ct_color.getValue()[2]) / 255);
                graphics.setColor(color);
            } else {
                graphics.setColor(Color.BLACK);
            }
            graphics.fill(shape);
        }

        if (ctText.getStroke() != null && ctText.getStroke()) {
            CT_Color ct_color = ctText.getStrokeColor();
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

    private void renderImage(Graphics2D graphics, BufferedImage targetImg, ST_Box boundary, Double[] ctm, int dpi) {

        Matrix m = MatrixUtils.base();
        // 初始化矩阵
        graphics.setTransform(MatrixUtils.createAffineTransform(m));

        m = m.mtimes(MatrixUtils.create(dpi, 0, 0, dpi, 0, 0));
        graphics.transform(MatrixUtils.createAffineTransform(m));
        // 绘制外框
        graphics.setClip(new Rectangle2D.Double(boundary.getX(), boundary.getY(), boundary.getW(),
                boundary.getH()));
        renderBoundary(graphics, boundary);

        graphics.setTransform(MatrixUtils.createAffineTransform(MatrixUtils.base()));
        // 把图片还原成1*1
        Matrix matrix = MatrixUtils.create(Double.valueOf(1.0 / targetImg.getWidth()).floatValue(), 0, 0,
                Double.valueOf(1.0 / targetImg.getHeight()).floatValue(), 0, 0);

        if (ctm != null) {
            // CTM
            matrix = matrix.mtimes(
                    MatrixUtils.create(ctm[0].floatValue(), ctm[1].floatValue(), ctm[2].floatValue(),
                            ctm[3].floatValue(), ctm[4].floatValue(), ctm[5].floatValue()));
        }
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
    }

    private void renderBoundary(Graphics2D graphics, ST_Box st_box) {
        if (!this.config.drawBoundary) {
            return;
        }
        graphics.setClip(null);
        Color color = graphics.getColor();
        graphics.setColor(Color.RED);
        graphics.setStroke(new BasicStroke(0.10f));

        // 转int丢失精度，乘DPI后误差放大，这里上下取整，稍微画大一点
        graphics.drawRect(Double.valueOf(Math.floor(st_box.getX())).intValue(),
                Double.valueOf(Math.floor(st_box.getY())).intValue(),
                Double.valueOf(Math.ceil(st_box.getW())).intValue(),
                Double.valueOf(Math.ceil(st_box.getH())).intValue());

        graphics.setColor(color);
    }

    private void setClipAndrenderBoundary(ST_Box baseBoundary, ST_Box boundary, Matrix baseMatrix) {

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
        /*
         * 印章透明度
         * */
        private float stampOpacity = 1;
        //        private boolean stampInTop=true;
        private boolean drawBoundary;
    }
}
