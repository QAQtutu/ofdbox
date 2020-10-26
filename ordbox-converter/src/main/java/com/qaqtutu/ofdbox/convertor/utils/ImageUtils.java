package com.qaqtutu.ofdbox.convertor.utils;

import com.qaqtutu.ofdbox.core.OFDFile;
import com.qaqtutu.ofdbox.core.utils.Tuple3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtils {

    public static byte[] toBytes(BufferedImage bufferedImage, String type) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        boolean flag = ImageIO.write(bufferedImage, type, out);
        byte[] bytes = out.toByteArray();
        return bytes;
    }

    public static Tuple3<byte[],Integer,Integer> imageConvert(OFDFile ofdFile) throws IOException {
        String suffix=ofdFile.getLoc().substring(ofdFile.getLoc().indexOf(".")+1);
        switch (suffix){
            case "jb2":
                BufferedImage bufferedImage=Jb2Utils.toJpg(new ByteArrayInputStream(ofdFile.getBytes()));
                Tuple3<byte[],Integer,Integer> tuple=new Tuple3<>(toBytes(bufferedImage,"jpg"),bufferedImage.getWidth(),bufferedImage.getHeight());
                return tuple;

        }
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(ofdFile.getBytes()));
        Tuple3<byte[],Integer,Integer> tuple=new Tuple3<>(ofdFile.getBytes(),bufferedImage.getWidth(),bufferedImage.getHeight());
        return tuple;
    }

    public static BufferedImage getBufferedImage(OFDFile ofdFile) throws IOException {
        String suffix=ofdFile.getLoc().substring(ofdFile.getLoc().indexOf(".")+1);
        switch (suffix){
            case "jb2":
                BufferedImage bufferedImage=Jb2Utils.toJpg(new ByteArrayInputStream(ofdFile.getBytes()));
                return bufferedImage;

        }
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(ofdFile.getBytes()));
        return bufferedImage;
    }

}
