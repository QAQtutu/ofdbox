package com.qaqtutu.ofdbox.core.xmlobj.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StringArrayAdapter extends XmlAdapter<String, String[]> {
    @Override
    public String[] unmarshal(String v) throws Exception {
        if(v==null)return null;
        return v.split("\\s+");
    }

    @Override
    public String marshal(String[] v) throws Exception {
        if(v==null)return null;
        return String.join(" ",v);
    }
}
