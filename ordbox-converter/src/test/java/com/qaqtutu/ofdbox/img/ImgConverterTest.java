package com.qaqtutu.ofdbox.img;

import com.qaqtutu.ofdbox.convertor.img.Ofd2Img;
import com.qaqtutu.ofdbox.convertor.pdf.Ofd2pdf;
import com.qaqtutu.ofdbox.core.OFD;
import com.qaqtutu.ofdbox.core.OFDReader;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImgConverterTest {
    private static File base=new File("target/test/img");

    @Test
    public void init() throws IOException {
        if(!base.exists()){
            FileUtils.forceMkdir(base);
        }
    }

    @Test
    public void toPdf() throws IOException {

        OFDReader reader = new OFDReader();
        OFD ofd = reader.read(new File("src/test/resources/发票.ofd"));
        OFD ofd1 = reader.read(new File("src/test/resources/旋转测试.ofd"));

        Ofd2Img ofd2Img = new Ofd2Img();
        ofd2Img.getConfig().setDrawBoundary(true);

        BufferedImage image = ofd2Img.toImage(ofd.getDocuments().get(0).getPages().get(0), 20);
        BufferedImage image1 = ofd2Img.toImage(ofd1.getDocuments().get(0).getPages().get(0), 20);
        ImageIO.write(image, "JPEG", new FileOutputStream(new File(base,"发票.jpg")));
        ImageIO.write(image1, "JPEG", new FileOutputStream(new File(base,"旋转测试.jpg")));

    }
}
