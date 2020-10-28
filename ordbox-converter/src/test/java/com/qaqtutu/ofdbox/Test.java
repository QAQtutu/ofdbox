package com.qaqtutu.ofdbox;

import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.OpenTypeFont;

import java.io.File;
import java.io.IOException;

/**
 * @description:
 * @author: 张家尧
 * @create: 2020/10/28 09:45
 */
public class Test {
    public static void main(String[] args) throws IOException {
        OTFParser parser=new OTFParser(true);
        OpenTypeFont openTypeFont= parser.parse(new File("C:\\Users\\123\\Desktop\\font_21.ttf"));
        System.out.println(openTypeFont);
    }
}