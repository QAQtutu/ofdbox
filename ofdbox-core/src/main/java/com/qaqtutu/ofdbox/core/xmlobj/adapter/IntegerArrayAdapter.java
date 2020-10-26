package com.qaqtutu.ofdbox.core.xmlobj.adapter;

public class IntegerArrayAdapter extends BaseAdapter<Integer[]> {


    @Override
    public Integer[] unmarshal1(String v) throws Exception {
        String[] s=v.split("\\s+");
        Integer[] arr=new Integer[s.length];
        for(int i=0;i<s.length;i++){
            arr[i]=Integer.valueOf(s[i]);
        }
        return arr;
    }

    @Override
    public String marshal1(Integer[] v) throws Exception {
        String[] arr=new String[v.length];
        for(int i=0;i<v.length;i++){
            arr[i]=String.valueOf(v[i]);
        }
        return null;
    }
}
