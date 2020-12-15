package com.ofdbox.convertor.img;

import com.ofdbox.convertor.utils.FontUtils;
import com.ofdbox.convertor.utils.ImageUtils;
import com.ofdbox.core.*;
import com.ofdbox.core.model.Annotations;
import com.ofdbox.core.model.OFD;
import com.ofdbox.core.model.Signatures;
import com.ofdbox.core.model.document.Document;
import com.ofdbox.core.model.page.Page;
import com.ofdbox.core.utils.FormatUtils;
import com.ofdbox.core.utils.MatrixUtils;
import com.ofdbox.core.utils.Stack;
import com.ofdbox.core.utils.Tuple2;
import com.ofdbox.core.xmlobj.annotation.NAnnot;
import com.ofdbox.core.xmlobj.annotation.XPageAnnot;
import com.ofdbox.core.xmlobj.base.page.CT_PageBlock;
import com.ofdbox.core.xmlobj.base.page.NLayer;
import com.ofdbox.core.xmlobj.base.page.NTemplate;
import com.ofdbox.core.xmlobj.enums.ColorSpaceType;
import com.ofdbox.core.xmlobj.enums.LayerType;
import com.ofdbox.core.xmlobj.graphic.CT_Path;
import com.ofdbox.core.xmlobj.object.composite.CT_Composite;
import com.ofdbox.core.xmlobj.object.image.CT_Image;
import com.ofdbox.core.xmlobj.object.text.CT_CGTransform;
import com.ofdbox.core.xmlobj.object.text.CT_Font;
import com.ofdbox.core.xmlobj.object.text.CT_Text;
import com.ofdbox.core.xmlobj.object.text.NTextCode;
import com.ofdbox.core.xmlobj.pagedesc.CT_DrawParam;
import com.ofdbox.core.xmlobj.pagedesc.CT_GraphicUnit;
import com.ofdbox.core.xmlobj.pagedesc.color.CT_Color;
import com.ofdbox.core.xmlobj.pagedesc.color.CT_ColorSpace;
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
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
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
    private Map<String, TrueTypeFont> embeddedCache = new HashMap<>();

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
//                e.printStackTrace();
                log.error("以V4标准加载签章失败 " + e.getMessage());
                try {
                    in.reset();
                    com.ofdbox.signature.gm.v1.SES_Signature ses_signature = Gm.readV1(in);
                    picType = ses_signature.getToSign().getEseal().getESealInfo().getPicture().getType().getString();
                    picData = ses_signature.getToSign().getEseal().getESealInfo().getPicture().getData().getOctets();
                } catch (Exception e1) {
                    log.error("以V1标准加载签章失败 " + e1.getMessage());
                }
            }
            if (picData == null) continue;
            BufferedImage image = null;
            switch (picType.toLowerCase()) {
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
                    break;
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
                ST_Box stBox = nStampAnnot.getBoundary();
                Matrix m = MatrixUtils.base();
                graphics.setTransform(MatrixUtils.createAffineTransform(m));
                m = MatrixUtils.scale(m, stBox.getW() / image.getWidth(), stBox.getH() / image.getHeight());
                m = MatrixUtils.move(m, stBox.getX(), stBox.getY());
                m = MatrixUtils.scale(m, dpi, dpi);
                graphics.setClip(null);
                graphics.drawImage(image, MatrixUtils.createAffineTransform(m), null);
            }

        }
    }

    private void renderContent(Graphics2D graphics, Document document, CT_PageBlock pageBlock, int dpi) {
        new ContentWalker(pageBlock) {
            @Override
            public void onImage(CT_Image ctImage, Stack<CT_PageBlock> stack) {
                Matrix baseMatrix = renderBoundaryAndSetClip(graphics, ctImage, stack, dpi);
                Matrix m = MatrixUtils.base();

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


                // 把图片还原成1*1
                m = MatrixUtils.scale(m, Double.valueOf(1.0 / targetImg.getWidth()).floatValue(), Double.valueOf(1.0 / targetImg.getHeight()).floatValue());

                if (ctImage.getCtm() != null) {
                    m = m.mtimes(MatrixUtils.ctm(ctImage.getCtm()));
                }
                if (ctImage.getBoundary() != null) {
                    m = MatrixUtils.move(m, ctImage.getBoundary().getX(), ctImage.getBoundary().getY());
                }
                m = m.mtimes(baseMatrix);
                graphics.setTransform(MatrixUtils.createAffineTransform(MatrixUtils.base()));

                graphics.drawImage(targetImg, MatrixUtils.createAffineTransform(m), null);
            }

            @Override
            public void onText(CT_Text ctText, Stack<CT_PageBlock> stack) {
                Matrix baseMatrix = renderBoundaryAndSetClip(graphics, ctText, stack, dpi);

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
                        typeFont = embeddedCache.get(loc);
                        if (typeFont == null) {
                            byte[] fontData = document.getOfd().getFileManager().readBytes(loc);
                            if (fontData != null) {
                                typeFont = parser.parse(new ByteArrayInputStream(fontData));
                                embeddedCache.put(loc, typeFont);
                            } else {
                                log.error("找不到字体：" + loc);
                            }
                        }else{
                            log.debug("缓存命中："+loc);
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
                    log.debug("原字体：" + font.getFontFile().getFullLoc());
                }
                try {
                    log.debug("字体名称：" + typeFont.getPostScript());
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
                                if (glyphData == null) {
                                    log.debug(String.format("找不到字形 %s", c));
                                } else {
                                    Shape shape = glyphData.getPath();
                                    log.debug(String.format("字形Shape %s", shape));
                                    Matrix matrix = chatMatrix(ctText, x, y, ctText.getSize(), fontMatrix, baseMatrix);
                                    renderChar(graphics, shape, document, ctText, matrix, stack);
//                                renderChar(graphics, shape, ctText, ctText.getCtm(),
//                                        ctText.getBoundary(), x, y, ctText.getSize(), dpi,
//                                        fontMatrix);
                                }

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
//                                        renderChar(graphics, shape, ctText, ctText.getCtm(),
//                                                ctText.getBoundary(), x, y, ctText.getSize(), dpi,
//                                                fontMatrix);
                                        Matrix matrix = chatMatrix(ctText, x, y, ctText.getSize(), fontMatrix, baseMatrix);
                                        renderChar(graphics, shape, document, ctText, matrix, stack);
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
            public void onPath(CT_Path ctPath, Stack<CT_PageBlock> stack) {
                Matrix baseMatrix = renderBoundaryAndSetClip(graphics, ctPath, stack, dpi);


                Matrix m = MatrixUtils.base();
                if (ctPath.getCtm() != null) {
                    m = m.mtimes(MatrixUtils.ctm(ctPath.getCtm()));
                }
                if (ctPath.getBoundary() != null) {
                    m = MatrixUtils.move(m, ctPath.getBoundary().getX(), ctPath.getBoundary().getY());
                }
                m = m.mtimes(baseMatrix);
                graphics.transform(MatrixUtils.createAffineTransform(m));

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
                    if (ct_color == null) {
                        ct_color = getStrokeColor(document, stack);
                    }
                    if (ct_color != null && ct_color.getValue() != null) {
                        color = getColor(document, ct_color);

                    }
                    if (color == null) {
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
                    if (ct_color == null) {
                        ct_color = getFillColor(document, stack);
                    }
                    if (ct_color != null && ct_color.getValue() != null) {
                        color = getColor(document, ct_color);
                        if (color == null) return;
                    } else {
                        graphics.setBackground(null);
                        return;
                    }
                    graphics.setColor(color);
                    graphics.fill(path);
                    graphics.setBackground(Color.white);
                }
            }

            //TODO
            @Override
            public void onComposite(CT_Composite ctComposite, Stack<CT_PageBlock> stack) {
                Matrix baseMatrix = renderBoundaryAndSetClip(graphics, ctComposite, stack, dpi);

            }
        }.walk();
    }

    private Matrix renderBoundaryAndSetClip(Graphics2D graphics, CT_GraphicUnit graphicUnit, Stack<CT_PageBlock> stack, int dpi) {
        graphics.setColor(Color.RED);
        graphics.setStroke(new BasicStroke(0.1f * dpi));
        Matrix m = getBaseMatrix(stack);


        graphics.setTransform(MatrixUtils.createAffineTransform(m));

        ST_Box st_box = graphicUnit.getBoundary();

        if (st_box != null) {
            int x = Double.valueOf(Math.floor(st_box.getX().doubleValue() * dpi)).intValue();
            int y = Double.valueOf(Math.floor(st_box.getY().doubleValue() * dpi)).intValue();
            int w = Double.valueOf(Math.floor(st_box.getW().doubleValue() * dpi)).intValue();
            int h = Double.valueOf(Math.floor(st_box.getH().doubleValue() * dpi)).intValue();


            Polygon shape = new Polygon();

            Tuple2<Double, Double> p00 = MatrixUtils.pointTransform(m, st_box.getX(), st_box.getY());
            Tuple2<Double, Double> p01 = MatrixUtils.pointTransform(m, st_box.getX() + st_box.getW(), st_box.getY());
            Tuple2<Double, Double> p10 = MatrixUtils.pointTransform(m, st_box.getX(), st_box.getY() + st_box.getH());
            Tuple2<Double, Double> p11 = MatrixUtils.pointTransform(m, st_box.getX() + st_box.getW(), st_box.getY() + st_box.getH());

            shape.addPoint(Double.valueOf(p00.getFirst() * dpi).intValue(), Double.valueOf(p00.getSecond() * dpi).intValue());
            shape.addPoint(Double.valueOf(p01.getFirst() * dpi).intValue(), Double.valueOf(p01.getSecond() * dpi).intValue());
            shape.addPoint(Double.valueOf(p11.getFirst() * dpi).intValue(), Double.valueOf(p11.getSecond() * dpi).intValue());
            shape.addPoint(Double.valueOf(p10.getFirst() * dpi).intValue(), Double.valueOf(p10.getSecond() * dpi).intValue());

            if (getConfig().drawBoundary) {
                graphics.setClip(null);
                graphics.drawPolygon(shape);
            }
            graphics.setClip(shape);
        }

        m = MatrixUtils.scale(m, dpi, dpi);
        return m;
    }

    private Matrix getBaseMatrix(Stack<CT_PageBlock> stack) {
        Matrix m = MatrixUtils.base();
        Iterator<CT_PageBlock> iterator = stack.getIteratorBeginTop();
        while (iterator.hasNext()) {
            CT_PageBlock pageBlock = iterator.next();
            if (pageBlock.getCtm() != null)
                m = m.mtimes(MatrixUtils.ctm(pageBlock.getCtm()));
            if (pageBlock.getBoundary() != null)
                m = MatrixUtils.move(m, pageBlock.getBoundary().getX(), pageBlock.getBoundary().getY());
        }
        return m;
    }

    private Matrix chatMatrix(CT_Text ctText, Double deltaX, Double deltaY, Double fontSize, List<Number> fontMatrix, Matrix baseMatrix) {
        Matrix m = MatrixUtils.base();
        m = MatrixUtils.imageMatrix(m, 0, 1, 0);
        if (ctText.getHScale() != null) {
            m = MatrixUtils.scale(m, ctText.getHScale(), 1);
        }
        m = m.mtimes(MatrixUtils.create(fontMatrix.get(0).doubleValue(), fontMatrix.get(1).doubleValue(),
                fontMatrix.get(2).doubleValue(), fontMatrix.get(3).doubleValue(),
                fontMatrix.get(4).doubleValue(), fontMatrix.get(5).doubleValue()));
        m = MatrixUtils.scale(m, fontSize, fontSize);
        m = MatrixUtils.move(m, deltaX, deltaY);
        if (ctText.getCtm() != null) {
            m = m.mtimes(MatrixUtils.ctm(ctText.getCtm()));
        }
        if (ctText.getBoundary() != null) {
            m = MatrixUtils.move(m, ctText.getBoundary().getX(), ctText.getBoundary().getY());
        }
        m = m.mtimes(baseMatrix);
        return m;
    }

    /*
     * 渲染一个字符 字形图形 字体字形最大宽度 字体字形最大高度 ctm变换矩阵
     */

    private void renderChar(Graphics2D graphics, Shape shape, Document document, CT_Text ctText, Matrix m, Stack<CT_PageBlock> pageBlocks) {

        graphics.setClip(null);
        graphics.setTransform(MatrixUtils.createAffineTransform(m));
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
            if (ct_color == null) {
                ct_color = getFillColor(document, pageBlocks);
            }
            if (ct_color != null && ct_color.getValue() != null && ct_color.getValue().length >= 3) {
                color = getColor(document, ct_color);
            }
            if (color == null) {
                graphics.setColor(Color.BLACK);
            } else {
                graphics.setColor(color);
            }

            graphics.fill(shape);
        }

        if (ctText.getStroke() != null && ctText.getStroke()) {
            CT_Color ct_color = ctText.getStrokeColor();
            if (ct_color == null) {
                ct_color = getStrokeColor(document, pageBlocks);
            }
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

    public CT_Color getFillColor(Document document, Stack<CT_PageBlock> pageBlocks) {
        Iterator<CT_PageBlock> iterator = pageBlocks.getIteratorBeginBottom();
        if (iterator.hasNext()) {
            CT_PageBlock pageBlock = iterator.next();
            if (!(pageBlock instanceof NLayer)) return null;
            NLayer layer = (NLayer) pageBlock;
            if (layer.getDrawParam() != null) {
                CT_DrawParam drawParam = document.getDrawParam(layer.getDrawParam().getId());
                if (drawParam != null && drawParam.getFillColor() != null)
                    return drawParam.getFillColor();
            }
        }
        return null;
    }

    public CT_Color getStrokeColor(Document document, Stack<CT_PageBlock> pageBlocks) {
        Iterator<CT_PageBlock> iterator = pageBlocks.getIteratorBeginBottom();
        if (iterator.hasNext()) {
            CT_PageBlock pageBlock = iterator.next();
            if (!(pageBlock instanceof NLayer)) return null;
            NLayer layer = (NLayer) pageBlock;
            if (layer.getDrawParam() != null) {
                CT_DrawParam drawParam = document.getDrawParam(layer.getDrawParam().getId());
                if (drawParam != null && drawParam.getStrokeColor() != null)
                    return drawParam.getStrokeColor();
            }
        }
        return null;
    }

    public Color getColor(Document document, CT_Color ctColor) {
        String[] color = ctColor.getValue();

        ColorSpaceType type = ColorSpaceType.RGB;
        if (ctColor.getColorSpace() != null) {
            CT_ColorSpace ctColorSpace = document.getColorSpace(ctColor.getColorSpace().getId());
            if (ctColorSpace != null) {
                if (ctColorSpace.getType() != null) {
                    type = ctColorSpace.getType();
                }
                if (color == null && ctColor.getIndex() != null
                        && ctColorSpace.getPalette() != null
                        && ctColor.getIndex() < ctColorSpace.getPalette().getCvs().size()) {
                    color = ctColorSpace.getPalette().getCvs().get(ctColor.getIndex());
                }
            }
        }
        if (color == null) return null;
        int[] color_ = new int[color.length];
        for (int i = 0; i < color.length; i++) {
            String s = color[i];
            if (s.startsWith("#")) {
                color_[i] = Integer.parseInt(s.replaceAll("#", ""), 16);
            } else {
                color_[i] = Integer.valueOf(s);
            }
        }
        switch (type) {
            case GRAY:
                return new Color(color_[0], color_[0], color_[0]);
            case CMYK:
                int r = 255 * (100 - color_[0]) * (100 - color_[3]) / 10000;
                int g = 255 * (100 - color_[1]) * (100 - color_[3]) / 10000;
                int b = 255 * (100 - color_[2]) * (100 - color_[3]) / 10000;
                return new Color(r, g, b);
            case RGB:
            default:
                return new Color(color_[0], color_[1], color_[2]);
        }
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
        private float stampOpacity = 0.75f;
        //        private boolean stampInTop=true;
        private boolean drawBoundary;
    }
}
