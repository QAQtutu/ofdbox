package com.qaqtutu.ofdbox.core.contance;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
*@Description: 常量
*@Author: 张家尧
*@date: 2020/10/1 9:41
*/
public class Const {
    public static final String NAMESPACE_PREFIX="ofd";
    public static final String NAMESPACE_URI="http://www.ofdspec.org/2016";


    private static final String PROJECT_NAME="ofd parser";
    public static  String VERSION;

    static {
        String path = "/version.prop";
        InputStream stream = Const.class.getResourceAsStream(path);
        if (stream == null)
            VERSION= "UNKNOWN";
        Properties props = new Properties();
        try {
            props.load(stream);
            stream.close();
            VERSION= (String) props.get("version");
        } catch (IOException e) {
            VERSION = "UNKNOWN";
        }
    }
}
