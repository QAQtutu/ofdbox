package com.ofdbox.core.xmlobj.adapter;

import com.ofdbox.core.utils.FormatUtils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DoubleArrayAdapter extends XmlAdapter<String, Double[]> {
    @Override
    public Double[] unmarshal(String v) throws Exception {
        if(v==null)return null;
        String[] s=v.split("\\s+");
        Double[] arr=new Double[s.length];
        for(int i=0;i<s.length;i++){
            arr[i]=(Double.valueOf(s[i]));
        }
        return arr;
    }

    @Override
    public String marshal(Double[] v) throws Exception {
        if(v==null)return null;
        String[] arr=new String[v.length];
        for(int i=0;i<v.length;i++){
            arr[i]=FormatUtils.doubleFormat(v[i]);
        }
        return String.join(" ",arr);
    }
}
