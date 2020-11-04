package com.ofdbox.convertor.test.utils;

import java.net.URLDecoder;

/**
 * @description:
 * @author: 张家尧
 * @create: 2020/10/28 13:44
 */
public class PathUtils {
    public static String getClassPath(Class c){
        return URLDecoder.decode(c.getClassLoader().getResource("").getFile());
    }

}