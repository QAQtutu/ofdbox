//package com.ofdbox.convertor.pdf;
//
//import com.ofdbox.convertor.utils.ImageUtils;
//import com.ofdbox.core.*;
//import com.ofdbox.core.model.document.Document;
//import com.ofdbox.core.model.page.Page;
//import com.ofdbox.core.utils.MatrixUtils;
//import com.ofdbox.core.utils.Tuple2;
//import com.ofdbox.core.utils.Tuple3;
//import com.ofdbox.core.xmlobj.base.page.CT_PageBlock;
//import com.ofdbox.core.xmlobj.base.page.NLayer;
//import com.ofdbox.core.xmlobj.base.page.NTemplate;
//import com.ofdbox.core.xmlobj.base.page.object.NImageObject;
//import com.ofdbox.core.xmlobj.base.page.object.NPathObject;
//import com.ofdbox.core.xmlobj.base.page.object.NTextObject;
//import com.ofdbox.core.xmlobj.enums.LayerType;
//import com.ofdbox.core.xmlobj.object.text.NTextCode;
//import com.ofdbox.core.xmlobj.pagedesc.color.CT_Color;
//import com.ofdbox.core.xmlobj.st.ST_Box;
//import lombok.Data;
//import lombok.Getter;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.common.PDRectangle;
//import org.apache.pdfbox.pdmodel.font.PDFont;
//import org.apache.pdfbox.pdmodel.font.PDType0Font;
//import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
//import org.ujmp.core.Matrix;
//
//import java.awt.*;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @description:
// * @author: 张家尧
// * @create: 2020/10/01 14:14
// */
//@Getter
//@Slf4j
//public class Ofd2Pdf {
//
//    private Config config = new Config();
//
//    //1英寸=72pt
//    private static final int in2pt = 72;
//    //1英寸=25.4毫米
//    private static final float in2mm = 25.4f;
//    //1毫米=1/25.4英寸
//    private static final float mm2in = 1.0f / in2mm;
//    //1毫米=72/25.4英寸
//    private static final float mm2pt = in2pt / in2mm;
//
//    private PDFont font;
//
//
//    public void toPdf(Document doc, File file) {
//        try (OutputStream out = new FileOutputStream(file)) {
//            toPdf(doc, out);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void toPdf(Document doc, OutputStream out) {
//
//        PDDocument pdfDoc = null;
//        try {
//            pdfDoc = new PDDocument();
//            font = PDType0Font.load(pdfDoc, new File("C:\\Windows\\Fonts\\Dengl.ttf"));
//            for (Page page : doc.getPages()) {
//
//                ST_Box pageBox = page.getPhysicalBox();
//                System.out.println(pageBox);
//                PDPage pdfPage = new PDPage(new PDRectangle(pageBox.getX().floatValue() * mm2pt, pageBox.getY().floatValue() * mm2pt, pageBox.getW().floatValue() * mm2pt, pageBox.getH().floatValue() * mm2pt));
//
//
//                PDPageContentStream contStr = new PDPageContentStream(pdfDoc, pdfPage);
//
//                renderPage(pdfDoc, contStr, page, pageBox);
//
//                contStr.close();
//
//                pdfDoc.addPage(pdfPage);
//            }
//
//
//            pdfDoc.save(out);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//
//        }
//
//    }
//
//
//    private void renderPage(PDDocument document, PDPageContentStream contStr, Page page, ST_Box pageBox) {
//        List<NLayer> bodyLayer = new ArrayList<>();
//        List<NLayer> foreLayer = new ArrayList<>();
//        List<NLayer> backLayer = new ArrayList<>();
//
//        List<Template> bodyTemplate = new ArrayList<>();
//        List<Template> foreTemplate = new ArrayList<>();
//        List<Template> backTemplate = new ArrayList<>();
//
//        for (NLayer layer : page.getXPage().getContent().getLayers()) {
//            if (layer.getType() == null || layer.getType() == LayerType.Body) {
//                bodyLayer.add(layer);
//            } else if (layer.getType() == LayerType.Foreground) {
//                foreLayer.add(layer);
//            } else if (layer.getType() == LayerType.Background) {
//                backLayer.add(layer);
//            }
//        }
//        if (page.getXPage().getTemplates() != null) {
//            for (NTemplate nTemplate : page.getXPage().getTemplates()) {
//                Template template = page.getDocument().getTemplate(nTemplate.getTemplateId().getId());
//                if (template == null) continue;
//                LayerType type = nTemplate.getZOrder();
//                if (type == null || type == LayerType.Body) {
//                    bodyTemplate.add(template);
//                } else if (type == LayerType.Foreground) {
//                    foreTemplate.add(template);
//                } else if (type == LayerType.Background) {
//                    backTemplate.add(template);
//                }
//            }
//        }
//        //背景层模版
//        for (Template template : backTemplate)
//            renderPage(document, contStr, template, pageBox);
//        //背景层
//        for (NLayer layer : backLayer)
//            renderContent(document, contStr, page, layer, pageBox);
//        //正文层模版
//        for (Template template : bodyTemplate)
//            renderPage(document, contStr, template, pageBox);
//        //正文层
//        for (NLayer layer : bodyLayer)
//            renderContent(document, contStr, page, layer, pageBox);
//        //前景层模版
//        for (Template template : foreTemplate)
//            renderPage(document, contStr, template, pageBox);
//        //前景层
//        for (NLayer layer : foreLayer)
//            renderContent(document, contStr, page, layer, pageBox);
//
//    }
//
//    private void renderContent(PDDocument document, PDPageContentStream contStr, Page page, CT_PageBlock pageBlock, ST_Box pageBox) {
//
//        new ContentWalker(pageBlock) {
//            @Override
//            public void onText(NTextObject nTextObject) {
//                try {
//
//                    for (NTextCode nTextCode : nTextObject.getTextCodes()) {
//
//
//                        Double[] deltaX = formatDelta(nTextCode.getContent().length(), nTextCode.getDeltaX());
//                        Double[] deltaY = formatDelta(nTextCode.getContent().length(), nTextCode.getDeltaY());
//                        double dx = nTextCode.getX().floatValue();
//                        double dy = nTextCode.getY().floatValue();
//                        for (int i = 0; i < nTextCode.getContent().length(); i++) {
//                            //不是第一个字，就加上偏移量
//                            if (i != 0) {
//                                dx += deltaX[i - 1].floatValue();
//                                dy += deltaY[i - 1].floatValue();
//                            }
//
//
//                            String s = String.valueOf(nTextCode.getContent().charAt(i));
//
//                            Matrix matrix = MatrixUtils.base();
//                            matrix = MatrixUtils.imageMatrix(matrix, 0, 1, 0);
//                            if (nTextObject.getCtm() != null) {
//                                Matrix ctm = MatrixUtils.ctm(nTextObject.getCtm());
//                                matrix = matrix.mtimes(ctm);
//
//                            }
//                            ST_Box boundary = nTextObject.getBoundary();
//                            if (boundary == null) {
//                                boundary = pageBox;
//                            }
//                            matrix = matrix.mtimes(MatrixUtils.create(1, 0, 0, 1, dx, dy));
//                            matrix = matrix.mtimes(MatrixUtils.create(1, 0, 0, 1, boundary.getX(), boundary.getY()));
//                            matrix = MatrixUtils.imageMatrix(matrix, 0, 1, 0);
//                            matrix = matrix.mtimes(MatrixUtils.create(1, 0, 0, 1, 0, pageBox.getH()));
//                            matrix = matrix.mtimes(MatrixUtils.create(mm2pt, 0, 0, mm2pt, 0, 0));
//
////                            contStr.transform(toPFMatrix(matrix));
//
//
//                            contStr.setFont(font, Double.valueOf(nTextObject.getSize()).floatValue());
//                            contStr.beginText();
//                            /*
//                             * 文字默认填充 不勾边
//                             * */
//
//                            if (nTextObject.getFill() == null || nTextObject.getFill() == true) {
//                                if (nTextObject.getFillColor() != null) {
//                                    CT_Color ct_color = nTextObject.getFillColor();
//                                    Color color = new Color(Float.valueOf(ct_color.getValue()[0]) / 255, Float.valueOf(ct_color.getValue()[1]) / 255, Float.valueOf(ct_color.getValue()[2]) / 255);
//                                    contStr.setNonStrokingColor(color);
//                                } else {
//                                    contStr.setNonStrokingColor(Color.BLACK);
//                                }
//                            }
//
//                            if (nTextObject.getStroke() != null && nTextObject.getStroke() && nTextObject.getStrokeColor() != null) {
//                                CT_Color ct_color = nTextObject.getStrokeColor();
//                                Color color = new Color(Float.valueOf(ct_color.getValue()[0]) / 255, Float.valueOf(ct_color.getValue()[1]) / 255, Float.valueOf(ct_color.getValue()[2]) / 255);
//                                contStr.setStrokingColor(color);
//
//                            }
//
////
//                            contStr.setTextMatrix(toPFMatrix(matrix));
//                            contStr.drawString(s);
//                            contStr.endText();
//                        }
//
//
//                    }
//
////                    contStr.drawXObject(null,null);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onImage(NImageObject nImageObject) {
//                OFDFile ofdFile = page.getDocument().getMultiMedia(nImageObject.getResourceId().getId());
//                if (ofdFile == null) {
//                    log.warn("找不到图片：" + ofdFile.getLoc());
//                }
//                ;
//                try {
//
//                    Tuple3<byte[], Integer, Integer> image = ImageUtils.imageConvert(ofdFile);
//                    PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, image.getFirst(), ofdFile.getLoc());
//
//                    Matrix matrix = MatrixUtils.base();
//                    matrix = MatrixUtils.imageMatrix(matrix, 0, 1, 0);
//                    matrix = MatrixUtils.move(matrix, 0, 1);
//                    if (nImageObject.getCtm() != null) {
//                        Matrix ctm = MatrixUtils.base();
//                        ctm = ctm.mtimes(MatrixUtils.ctm(nImageObject.getCtm()));
//                        matrix = matrix.mtimes(ctm);
//                    }
//
//                    ST_Box boundary = nImageObject.getBoundary();
//                    if (boundary == null) {
//                        boundary = pageBox;
//                    }
//
//                    Tuple2<Double, Double> tuple2 = MatrixUtils.leftTop(matrix);
////                    matrix = matrix.mtimes(MatrixUtils.create(1, 0, 0, 1, tuple2.getFirst().floatValue(),tuple2.getSecond().floatValue()));
//
//                    matrix = matrix.mtimes(MatrixUtils.create(1, 0, 0, 1, boundary.getX(), boundary.getY()));
//
////                    matrix=matrix.mtimes(MatrixUtils.create(1,0,0,1,0,boundary.getH()));
//
//                    matrix = MatrixUtils.imageMatrix(matrix, 0, 1, 0);
//                    matrix = matrix.mtimes(MatrixUtils.create(1, 0, 0, 1, 0, pageBox.getH()));
//
//                    matrix = matrix.mtimes(MatrixUtils.create(mm2pt, 0, 0, mm2pt, 0, 0));
//
//
//                    contStr.drawImage(pdImage, toPFMatrix(matrix));
////                    contStr.drawImage(pdImage, 0, -50, 1, 1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onPath(NPathObject nPathObject) {
//
//            }
//        }.walk();
//    }
//
//    private static org.apache.pdfbox.util.Matrix toPFMatrix(Matrix source) {
//        org.apache.pdfbox.util.Matrix target = new org.apache.pdfbox.util.Matrix();
//        target.setValue(0, 0, source.getAsFloat(0, 0));
//        target.setValue(0, 1, source.getAsFloat(0, 1));
//        target.setValue(1, 0, source.getAsFloat(1, 0));
//        target.setValue(1, 1, source.getAsFloat(1, 1));
//        target.setValue(2, 0, source.getAsFloat(2, 0));
//        target.setValue(2, 1, source.getAsFloat(2, 1));
//        return target;
//    }
//
//    private Double[] formatDelta(int textLength, String deltaStr) {
//        Double[] arr = new Double[textLength - 1];
//        if (deltaStr == null) {
//            for (int i = 0; i < textLength - 1; i++)
//                arr[i] = 0D;
//            return arr;
//        }
//        ;
//
//        String[] s = deltaStr.split("\\s+");
//        int i = 0;
//        int counter = 0;
//        while (i < s.length && counter < textLength - 1) {
//            String current = s[i];
//            if ("g".equals(current)) {
//                Integer num = Integer.valueOf(s[i + 1]);
//                Double delta = Double.valueOf(s[i + 2]);
//                for (int j = 1; j <= num && counter < textLength - 1; j++) {
//                    arr[counter] = delta;
//                    counter++;
//                }
//
//                i += 3;
//            } else {
//                Double delta = Double.valueOf(current);
//                arr[counter] = delta;
//                counter++;
//                i++;
//            }
//
//        }
//        return arr;
//    }
//
//    @Data
//    public static class Config {
//        private boolean drawBoundary;
//    }
//}