package com.qaqtutu.ofdbox.core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: 张家尧
 * @create: 2020/10/01 13:39
 */
public class FormatUtils {
    public static String doubleFormat(double value){
        if(value -(int)value >0){
            return String.valueOf(value);
        }else{
            return String.valueOf((int)value);
        }
    }

    public static List<Double> parseDelta(String deltaStr) {
        if(deltaStr==null||deltaStr.length()<=0)return null;
        List<Double> arr=new ArrayList<>();

        String[] s = deltaStr.split("\\s+");
        int i = 0;
        int counter = 0;
        while (i < s.length) {
            String current = s[i];
            if ("g".equals(current)) {
                Integer num = Integer.valueOf(s[i + 1]);
                Double delta = Double.valueOf(s[i + 2]);
                for (int j = 1; j <= num; j++) {
                    arr.add(delta)  ;
                    counter++;
                }
                i += 3;
            } else {
                Double delta = Double.valueOf(current);
                arr.add(delta)  ;
                counter++;
                i++;
            }

        }
        return arr;
    }

}