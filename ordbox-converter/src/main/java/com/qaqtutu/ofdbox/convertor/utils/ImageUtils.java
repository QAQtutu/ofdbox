package com.qaqtutu.ofdbox.convertor.utils;

import com.qaqtutu.ofdbox.core.OFDFile;
import com.qaqtutu.ofdbox.core.utils.Tuple3;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

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


    public static BufferedImage renderMask(BufferedImage image,BufferedImage mask){
        if((image.getWidth()!=mask.getWidth()||image.getHeight()!=mask.getHeight())){
            return image;
        }
        BufferedImage out = new BufferedImage(image.getWidth(), image.getHeight(), TYPE_INT_RGB);
        Graphics2D graphics = out.createGraphics();
        graphics.setColor(Color.WHITE);
        out = graphics.getDeviceConfiguration().createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = mask.getRGB(x, y);
                int r = 0xFF & rgb;
                int g = 0xFF00 & rgb;
                g >>= 8;
                int b = 0xFF0000 & rgb;
                b >>= 16;
                boolean sholdMask = (r + g + b) / 3 >244;
                if (sholdMask) {
                    out.setRGB(x,y,image.getRGB(x,y));
                }
            }
        }
        return out;
    }
}
