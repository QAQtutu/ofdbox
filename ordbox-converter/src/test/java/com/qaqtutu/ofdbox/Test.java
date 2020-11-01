package com.qaqtutu.ofdbox;

import com.qaqtutu.ofdbox.convertor.img.Ofd2Img;
import com.qaqtutu.ofdbox.core.OFD;
import com.qaqtutu.ofdbox.core.OFDReader;
import com.qaqtutu.ofdbox.core.Page;
import com.qaqtutu.ofdbox.core.utils.MatrixUtils;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.OpenTypeFont;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.OpenType;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * @description:
 * @author: 张家尧
 * @create: 2020/10/28 09:45
 */
public class Test {
    public static void main(String[] args) throws IOException {
        OFDReader reader = new OFDReader();
        reader.getConfig().setValid(false);
        OFD ofd = reader.read(new File("C:\\Users\\hututu\\Documents\\Tencent Files\\1178527103\\FileRecv\\多张.ofd"));

        Ofd2Img ofd2Img = new Ofd2Img();
        ofd2Img.getConfig().setDrawBoundary(false);


        int i=1;
        for(Page page:ofd.getDocuments().get(0).getPages()){
            BufferedImage image = ofd2Img.toImage(page, 30);
            ImageIO.write(image, "PNG", new FileOutputStream(new File("C:\\Users\\hututu\\Desktop\\test",i+".png")));
            i++;
        }

    }
}