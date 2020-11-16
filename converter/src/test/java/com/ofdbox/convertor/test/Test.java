package com.ofdbox.convertor.test;

import com.ofdbox.convertor.img.Ofd2Img;
import com.ofdbox.core.ContentWalker;
import com.ofdbox.core.model.OFD;
import com.ofdbox.core.OFDReader;
import com.ofdbox.core.model.page.Page;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @description:
 * @author: 张家尧
 * @create: 2020/10/28 09:45
 */
public class Test {
    public static void main(String[] args) throws IOException {
        OFDReader reader = new OFDReader();
        reader.getConfig().setValid(false);

        OFD ofd = reader.read(new File("C:\\Users\\hututu\\Documents\\Tencent Files\\1178527103\\FileRecv\\aaaaaaaaa(1)(1).ofd"));
//        OFD ofd = reader.read(new File("C:\\Users\\hututu\\Documents\\Tencent Files\\1178527103\\FileRecv\\999.ofd"));
//        OFD ofd = reader.read(new File("C:\\Users\\hututu\\Desktop\\ofdbox-stamp-5153252851729488752.ofd"));

        Ofd2Img ofd2Img = new Ofd2Img();
        ofd2Img.getConfig().setDrawBoundary(false);

        int i = 1;
        for (Page page : ofd.getDocuments().get(0).getPages()) {
            BufferedImage image = ofd2Img.toImage(page, 30);
            ImageIO.write(image, "JPEG", new FileOutputStream(new File("C:\\Users\\hututu\\Desktop\\test", i + ".jpg")));
            i++;
        }
    }
}