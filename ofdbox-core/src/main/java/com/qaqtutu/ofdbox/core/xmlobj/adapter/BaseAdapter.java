package com.qaqtutu.ofdbox.core.xmlobj.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public abstract class BaseAdapter<T> extends XmlAdapter<String,T> {

    @Override
    public T unmarshal(String v) throws Exception {
        if(v==null)return null;
        return unmarshal1(v);
    }

    @Override
    public String marshal(T v) throws Exception {
        if(v==null)return null;
        return marshal1(v);
    }

    public abstract T unmarshal1(String v) throws Exception;

    public abstract String marshal1(T v) throws Exception ;
}
