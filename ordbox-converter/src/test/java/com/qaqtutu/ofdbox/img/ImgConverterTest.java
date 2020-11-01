package com.qaqtutu.ofdbox.img;

import com.qaqtutu.ofdbox.convertor.img.Ofd2Img;
import com.qaqtutu.ofdbox.convertor.pdf.Ofd2pdf;
import com.qaqtutu.ofdbox.core.OFD;
import com.qaqtutu.ofdbox.core.OFDReader;
import com.qaqtutu.ofdbox.font.FontTest;
import com.qaqtutu.ofdbox.pdf.PdfConverterTest;
import com.qaqtutu.ofdbox.utils.PathUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class ImgConverterTest {
    protected static final String basePath = PathUtils.getClassPath(ImgConverterTest.class);

    @Test
    public void toImg() throws IOException {

        OFDReader reader = new OFDReader();
        reader.getConfig().setValid(false);
        OFD ofd = reader.read(new File(basePath+"发票.ofd"));
        OFD ofd1 = reader.read(new File(basePath+"旋转测试.ofd"));
        OFD ofd2 = reader.read(new File(basePath+"font/0000000005.ofd"));

        Ofd2Img ofd2Img = new Ofd2Img();
        ofd2Img.getConfig().setDrawBoundary(false);

//        BufferedImage image = ofd2Img.toImage(ofd.getDocuments().get(0).getPages().get(0), 20);
//        BufferedImage image1 = ofd2Img.toImage(ofd1.getDocuments().get(0).getPages().get(0), 20);
        BufferedImage image2 = ofd2Img.toImage(ofd2.getDocuments().get(0).getPages().get(0), 20);
//        ImageIO.write(image, "JPEG", new FileOutputStream(new File(basePath,"发票.jpg")));
//        ImageIO.write(image1, "JPEG", new FileOutputStream(new File(basePath,"旋转测试.jpg")));
        ImageIO.write(image2, "JPEG", new FileOutputStream(new File(basePath,"0000000005.jpg")));

    }
}
