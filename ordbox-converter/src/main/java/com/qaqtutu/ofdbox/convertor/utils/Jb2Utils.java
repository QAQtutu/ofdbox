package com.qaqtutu.ofdbox.convertor.utils;

import com.levigo.jbig2.JBIG2ImageReader;
import com.levigo.jbig2.JBIG2ImageReaderSpi;
import com.levigo.jbig2.io.DefaultInputStreamFactory;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Jb2Utils {

    public static BufferedImage toJpg(InputStream inputStream){

        int imageIndex = 0;
        JBIG2ImageReader imageReader = null;
        try {

            DefaultInputStreamFactory disf = new DefaultInputStreamFactory();
            ImageInputStream imageInputStream = disf.getInputStream(inputStream);

            imageReader = new JBIG2ImageReader(new JBIG2ImageReaderSpi());
            imageReader.setInput(imageInputStream);
            BufferedImage bufferedImage = imageReader.read(imageIndex, imageReader.getDefaultReadParam());

            return bufferedImage;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
