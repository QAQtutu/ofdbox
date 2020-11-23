package com.ofdbox.convertor.test.img;

import com.ofdbox.convertor.img.Ofd2Img;
import com.ofdbox.convertor.test.utils.PathUtils;
import com.ofdbox.convertor.utils.seal.SealUtil;
import com.ofdbox.core.model.OFD;
import com.ofdbox.core.OFDReader;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImgConverterTest {
    protected static final String basePath = PathUtils.getClassPath(ImgConverterTest.class);

    @Test
    public void toImg() throws IOException {

        OFDReader reader = new OFDReader();
        reader.getConfig().setValid(false);

        OFD ofd1 = reader.read(new File(basePath + "旋转测试.ofd"));

        Ofd2Img ofd2Img = new Ofd2Img();
        ofd2Img.getConfig().setDrawBoundary(false);

        BufferedImage image1 = ofd2Img.toImage(ofd1.getDocuments().get(0).getPages().get(0), 20);

        ImageIO.write(image1, "JPEG", new FileOutputStream(new File(basePath, "旋转测试.jpg")));

    }
}
