package com.qaqtutu.ofdbox.convertor;

import com.qaqtutu.ofdbox.convertor.img.Ofd2Img;
import com.qaqtutu.ofdbox.convertor.pdf.Ofd2pdf;
import com.qaqtutu.ofdbox.core.OFD;
import com.qaqtutu.ofdbox.core.OFDReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        OFDReader reader = new OFDReader();
        OFD ofd = reader.read(new File("旋转测试.ofd"));

        Ofd2pdf ofd2pdf = new Ofd2pdf();
        ofd2pdf.toPdf(ofd.getDocuments().get(0), new File("旋转测试.pdf"));

//        System.out.println(ofd);
//
        Ofd2Img ofd2Img = new Ofd2Img();
        ofd2Img.getConfig().setDrawBoundary(true);
        BufferedImage image = ofd2Img.toImage(ofd.getDocuments().get(0).getPages().get(0), 20);
        ImageIO.write(image, "JPEG", new FileOutputStream("旋转测试.jpg"));
    }
}
