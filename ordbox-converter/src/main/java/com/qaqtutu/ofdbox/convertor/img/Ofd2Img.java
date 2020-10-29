package com.qaqtutu.ofdbox.convertor.img;

import com.qaqtutu.ofdbox.convertor.utils.ImageUtils;
import com.qaqtutu.ofdbox.core.utils.MatrixUtils;
import com.qaqtutu.ofdbox.core.utils.Tuple2;
import com.qaqtutu.ofdbox.core.*;
import com.qaqtutu.ofdbox.core.xmlobj.base.page.CT_PageBlock;
import com.qaqtutu.ofdbox.core.xmlobj.base.page.NLayer;
import com.qaqtutu.ofdbox.core.xmlobj.base.page.NTemplate;
import com.qaqtutu.ofdbox.core.xmlobj.base.page.object.NImageObject;
import com.qaqtutu.ofdbox.core.xmlobj.base.page.object.NPathObject;
import com.qaqtutu.ofdbox.core.xmlobj.base.page.object.NTextObject;
import com.qaqtutu.ofdbox.core.xmlobj.enums.LayerType;
import com.qaqtutu.ofdbox.core.xmlobj.object.text.CT_CGTransform;
import com.qaqtutu.ofdbox.core.xmlobj.pagedesc.color.CT_Color;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Box;
import lombok.Data;
import lombok.Getter;
import org.apache.fontbox.ttf.GlyphData;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.apache.fontbox.util.BoundingBox;
import org.ujmp.core.Matrix;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: 张家尧
 * @create: 2020/10/01 14:14
 */
@Getter
public class Ofd2Img {

    private Config config = new Config();


    public BufferedImage toImage(Page page, int dpi) {

        ST_Box box = page.getPhysicalBox();
        BufferedImage image = new BufferedImage(box.getW().intValue() * dpi, box.getH().intValue() * dpi, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

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
                if (template == null) continue;
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
        //背景层模版
        for (Template template : backTemplate)
            renderPage(graphics, template, dpi);
        //背景层
        for (NLayer layer : backLayer)
            renderContent(graphics, page.getDocument(), layer, dpi);
        //正文层模版
        for (Template template : bodyTemplate)
            renderPage(graphics, template, dpi);
        //正文层
        for (NLayer layer : bodyLayer)
            renderContent(graphics, page.getDocument(), layer, dpi);
        //前景层模版
        for (Template template : foreTemplate)
            renderPage(graphics, template, dpi);
        //前景层
        for (NLayer layer : foreLayer)
            renderContent(graphics, page.getDocument(), layer, dpi);

    }

    private void renderContent(Graphics2D graphics, Document document, CT_PageBlock pageBlock, int dpi) {
        new ContentWalker(pageBlock) {
            @Override
            public void onText(NTextObject nTextObject) {
                ST_Box boundary = nTextObject.getBoundary();
                Matrix m = MatrixUtils.base();
                //初始化矩阵
                graphics.setTransform(MatrixUtils.createAffineTransform(m));

                m = m.mtimes(MatrixUtils.create(dpi, 0, 0, dpi, 0, 0));
                graphics.transform(MatrixUtils.createAffineTransform(m));
                //绘制外框
                graphics.setClip(new Rectangle2D.Double(boundary.getX(), boundary.getY(), boundary.getW(), boundary.getH()));
                renderBoundary(graphics, boundary);


                if (nTextObject.getStrokeColor() != null) {
                    CT_Color ct_color = nTextObject.getStrokeColor();
                    Color color = new Color(Float.valueOf(ct_color.getValue()[0]) / 255, Float.valueOf(ct_color.getValue()[1]) / 255, Float.valueOf(ct_color.getValue()[2]) / 255);
                    graphics.setColor(color);
                } else {
                    graphics.setColor(Color.BLACK);
                }

                Double[] ctm = nTextObject.getCtm();

                OFDFile fontFile=document.getFont(nTextObject.getFont().getId());

                BigInteger j=BigInteger.valueOf(0);
                nTextObject.getTextCodes().forEach(nTextCode -> {
                    j.add(BigInteger.valueOf(1));

                    if(j.intValue()>1)return;
                    Double[] deltaX = formatDelta(nTextCode.getContent().length(), nTextCode.getDeltaX());
                    Double[] deltaY = formatDelta(nTextCode.getContent().length(), nTextCode.getDeltaY());

                    double x = nTextCode.getX().floatValue();
                    double y = nTextCode.getY().floatValue();

                    int z=0;
                    for (int i = 0; i < nTextCode.getContent().length(); i++) {
                        if(z>1)return;
                        z++;

                        //不是第一个字，就加上偏移量
                        if (i != 0) {
                            x += deltaX[i - 1].floatValue();
                            y += deltaY[i - 1].floatValue();
                        }


                        Matrix matrix = MatrixUtils.base();
                        graphics.setTransform(MatrixUtils.createAffineTransform(matrix));

                        matrix = matrix.mtimes(MatrixUtils.create(1, 0, 0, 1, Double.valueOf(x).floatValue(), Double.valueOf(y).floatValue()));
                        if (ctm != null) {
                            matrix = matrix.mtimes(MatrixUtils.create(ctm[0].floatValue(), ctm[1].floatValue(), ctm[2].floatValue(), ctm[3].floatValue(), ctm[4].floatValue(), ctm[5].floatValue()));
                        }
                        matrix = matrix.mtimes(MatrixUtils.create(1, 0, 0, 1, nTextObject.getBoundary().getX().floatValue(), nTextObject.getBoundary().getY().floatValue()));
                        matrix = matrix.mtimes(MatrixUtils.create(dpi, 0, 0, dpi, 0, 0));

                        graphics.setTransform(MatrixUtils.createAffineTransform(matrix));

                        Font font = new Font("楷体", Font.BOLD, nTextObject.getSize().intValue());
                        graphics.setFont(font);

                        String charStr = String.valueOf(nTextCode.getContent().charAt(i));

                        GlyphVector v = font.createGlyphVector(graphics.getFontRenderContext(), charStr);
                        Shape shape = v.getOutline();


                        if(fontFile!=null){
                            OTFParser parser = new OTFParser(true);
                            OpenTypeFont openTypeFont =null;
                            try {
                                openTypeFont = parser.parse(new ByteArrayInputStream(fontFile.getBytes()));

                                System.out.println(openTypeFont);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if(nTextObject.getTransforms()!=null){
                                for(CT_CGTransform transform:nTextObject.getTransforms()){
                                    System.out.println(transform);

                                    int k=-1;
                                    for(Integer glyph:transform.getGlyphs()){
                                        k++;

                                        try {
                                            long last=0;
                                            for(long l:openTypeFont.getIndexToLocation().getOffsets()){
                                                if(l!=last){
                                                    last=l;
                                                    System.out.println(last);
                                                }
                                            }


                                            graphics.setClip(null);
                                            openTypeFont.getGlyph().getGlyphs();
                                            GlyphData glyphData=openTypeFont.getGlyph().getGlyph(glyph);
                                            System.out.println(glyphData.getBoundingBox());
                                            BoundingBox box=glyphData.getBoundingBox();
                                            Matrix m1=MatrixUtils.base();
                                            m1=MatrixUtils.imageMatrix(m1,0,1,0);
                                            m1=MatrixUtils.move(m1,-box.getUpperRightY(),-box.getLowerLeftX());
                                            m1=MatrixUtils.scale(m1,0.5,0.5);
                                            m1=MatrixUtils.scale(m1,1/box.getWidth(),1/box.getWidth());
//                                            m1=MatrixUtils.scale(m1,1/2,1/2);
                                            m1=MatrixUtils.move(m1,15*k,0);
                                            m1=  matrix.mtimes(m1);
                                            graphics.setTransform(MatrixUtils.createAffineTransform(m1.mtimes(matrix)));
                                            if(glyphData==null)continue;
                                            graphics.setStroke(new BasicStroke(0.1f));
                                            graphics.setColor(Color.BLACK);
                                            graphics.setBackground(Color.white);
                                            graphics.draw(glyphData.getPath());
                                            graphics.fill(glyphData.getPath());
                                            System.out.println(glyphData.getPath());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                        /*
                         * 文字默认填充 不勾边
                         * */

                        if (nTextObject.getFill() == null || nTextObject.getFill() == true) {
                            CT_Color ct_color = nTextObject.getFillColor();
                            Color color = null;
                            if (ct_color != null) {
                                color = new Color(Float.valueOf(ct_color.getValue()[0]) / 255, Float.valueOf(ct_color.getValue()[1]) / 255, Float.valueOf(ct_color.getValue()[2]) / 255);
                                graphics.setColor(color);
                            } else {
                                graphics.setColor(Color.BLACK);
                            }
                            graphics.fill(shape);
                        }

                        if ( nTextObject.getStroke()!=null && nTextObject.getStroke()) {
                            CT_Color ct_color = nTextObject.getStrokeColor();
                            if(ct_color==null){
                                graphics.setBackground(null);
                                graphics.setColor(null);
                            }else{
                                Color color = new Color(Float.valueOf(ct_color.getValue()[0]) / 255, Float.valueOf(ct_color.getValue()[1]) / 255, Float.valueOf(ct_color.getValue()[2]) / 255);
                                graphics.setColor(color);
                            }
                            graphics.draw(shape);
                            graphics.setBackground(Color.white);
                        }
                    }
                });
            }

            @Override
            public void onImage(NImageObject nImageObject) {

                OFDFile ofdFile = document.getMultiMedia(nImageObject.getResourceId().getId());
                try {
                    ST_Box boundary = nImageObject.getBoundary();

                    Matrix m = MatrixUtils.base();
                    //初始化矩阵
                    graphics.setTransform(MatrixUtils.createAffineTransform(m));

                    m = m.mtimes(MatrixUtils.create(dpi, 0, 0, dpi, 0, 0));
                    graphics.transform(MatrixUtils.createAffineTransform(m));
                    //绘制外框
                    graphics.setClip(new Rectangle2D.Double(boundary.getX(), boundary.getY(), boundary.getW(), boundary.getH()));
                    renderBoundary(graphics, boundary);


                    BufferedImage targetImg = ImageUtils.getBufferedImage(ofdFile);
                    Double[] ctm = nImageObject.getCtm();

                    graphics.setTransform(MatrixUtils.createAffineTransform(MatrixUtils.base()));
                    //把图片还原成1*1
                    Matrix matrix = MatrixUtils.create(Double.valueOf(1.0 / targetImg.getWidth()).floatValue(), 0, 0, Double.valueOf(1.0 / targetImg.getHeight()).floatValue(), 0, 0);

                    //CTM
                    matrix = matrix.mtimes(MatrixUtils.create(ctm[0].floatValue(), ctm[1].floatValue(), ctm[2].floatValue(), ctm[3].floatValue(), ctm[4].floatValue(), ctm[5].floatValue()));
                    Tuple2<Double, Double> tuple2 = MatrixUtils.leftTop(matrix);


                    //转换到世界坐标系
                    matrix = matrix.mtimes(MatrixUtils.create(1, 0, 0, 1, boundary.getX().floatValue(), boundary.getY().floatValue()));

                    matrix = matrix.mtimes(MatrixUtils.create(dpi, 0, 0, dpi, 0, 0));

                    matrix = matrix.mtimes(MatrixUtils.create(1, 0, 0, 1, tuple2.getFirst().floatValue(), tuple2.getSecond().floatValue()));


                    //单位毫米转换成像素
//                    matrix=MatrixUtils.scale(matrix,dpi, dpi);


                    graphics.drawImage(targetImg, MatrixUtils.createAffineTransform(matrix), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onPath(NPathObject nPathObject) {
                ST_Box boundary = nPathObject.getBoundary();

                Matrix matrix = MatrixUtils.base();
                graphics.setTransform(MatrixUtils.createAffineTransform(matrix));

                matrix = matrix.mtimes(MatrixUtils.create(dpi, 0, 0, dpi, 0, 0));
                graphics.transform(MatrixUtils.createAffineTransform(matrix));

                //设置剪裁区
                graphics.setClip(new Rectangle2D.Double(boundary.getX(), boundary.getY(), boundary.getW(), boundary.getH()));

                //绘制外框
                renderBoundary(graphics, boundary);


                Double[] ctm = nPathObject.getCtm();

                matrix = MatrixUtils.base();
                graphics.setTransform(MatrixUtils.createAffineTransform(matrix));

                if (ctm != null) {
                    matrix = matrix.mtimes(MatrixUtils.create(ctm[0].floatValue(), ctm[1].floatValue(), ctm[2].floatValue(), ctm[3].floatValue(), ctm[4].floatValue(), ctm[5].floatValue()));
                }
                //转换到世界坐标系
                matrix = matrix.mtimes(MatrixUtils.create(1, 0, 0, 1, boundary.getX().floatValue(), boundary.getY().floatValue()));
                //单位毫米转换成像素
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
                            path.quadTo(Double.valueOf(s[i + 1]), Double.valueOf(s[i + 2]), Double.valueOf(s[i + 3]), Double.valueOf(s[i + 4]));
                            i += 5;
                            break;
                        case "B":
                            path.curveTo(Double.valueOf(s[i + 1]), Double.valueOf(s[i + 2]), Double.valueOf(s[i + 3]), Double.valueOf(s[i + 4]), Double.valueOf(s[i + 5]), Double.valueOf(s[i + 6]));
                            i += 7;
                            break;
                        case "A":
//                            path.append(new Arc2D.Double(Double.valueOf(s[i+1]),Double.valueOf(s[i+2]),Double.valueOf(s[i+3]),Double.valueOf(s[i+4]),Double.valueOf(s[i+5]),Double.valueOf(s[i+3]),-1),true);
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
                        color = new Color(Float.valueOf(ct_color.getValue()[0]) / 255, Float.valueOf(ct_color.getValue()[1]) / 255, Float.valueOf(ct_color.getValue()[2]) / 255);
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
                        color = new Color(Float.valueOf(ct_color.getValue()[0]) / 255, Float.valueOf(ct_color.getValue()[1]) / 255, Float.valueOf(ct_color.getValue()[2]) / 255);
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

    private void renderBoundary(Graphics2D graphics, ST_Box st_box) {
        if (!this.config.drawBoundary) {
            return;
        }
        graphics.setClip(null);
        Color color = graphics.getColor();
        graphics.setColor(Color.RED);
        graphics.setStroke(new BasicStroke(0.2f));

        //转int丢失精度，乘DPI后误差放大，这里上下取整，稍微画大一点
        graphics.drawRect(Double.valueOf(Math.floor(st_box.getX())).intValue(), Double.valueOf(Math.floor(st_box.getY())).intValue(), Double.valueOf(Math.ceil(st_box.getW())).intValue(), Double.valueOf(Math.ceil(st_box.getH())).intValue());

        graphics.setColor(color);
    }

    private Double[] formatDelta(int textLength, String deltaStr) {

        Double[] arr = new Double[textLength - 1];
        if (deltaStr == null) {
            for (int i = 0; i < textLength - 1; i++)
                arr[i] = 0D;
            return arr;
        }
        ;

        String[] s = deltaStr.split("\\s+");
        int i = 0;
        int counter = 0;
        while (i < s.length) {
            String current = s[i];
            if ("g".equals(current)) {
                Integer num = Integer.valueOf(s[i + 1]);
                Double delta = Double.valueOf(s[i + 2]);
                for (int j = 1; j <= num; j++) {
                    arr[counter] = delta;
                    counter++;
                }

                i += 3;
            } else {
                Double delta = Double.valueOf(current);
                arr[counter] = delta;
                counter++;
                i++;
            }

        }
        return arr;
    }


//
//    public  int getWordWidth(Font font, String content) {
//        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
//        int width = 0;
//        for (int i = 0; i < content.length(); i++) {
//            width += metrics.charWidth(content.charAt(i));
//        }
//        return width;
//    }

    @Data
    public static class Config {
        private boolean drawBoundary;
    }
}