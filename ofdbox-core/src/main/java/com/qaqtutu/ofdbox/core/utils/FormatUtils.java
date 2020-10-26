package com.qaqtutu.ofdbox.core.utils;

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

    private Double[] parseDelta(int textLength, String deltaStr) {

        Double[] arr = new Double[textLength - 1];
        if (deltaStr == null) {
            for (int i = 0; i < textLength - 1; i++)
                arr[i] = 0D;
            return arr;
        }
        ;

        String[] s = deltaStr.split("\\s+");
        int i = 0;
        int counter = 0;
        while (i < s.length) {
            String current = s[i];
            if ("g".equals(current)) {
                Integer num = Integer.valueOf(s[i + 1]);
                Double delta = Double.valueOf(s[i + 2]);
                for (int j = 1; j <= num; j++) {
                    arr[counter] = delta;
                    counter++;
                }

                i += 3;
            } else {
                Double delta = Double.valueOf(current);
                arr[counter] = delta;
                counter++;
                i++;
            }

        }
        return arr;
    }

}