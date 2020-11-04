package com.ofdbox.core.xmlobj.adapter;

import com.ofdbox.core.xmlobj.st.ST_RefID;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @description: ST_Box适配器
 * @author: 张家尧
 * @create: 2020/10/01 11:27
 */
public class StRefIdAdapter extends XmlAdapter< String, ST_RefID> {
    @Override
    public ST_RefID unmarshal(String v) throws Exception {
        if(v==null)return null;
        ST_RefID st_refID=new ST_RefID();
        st_refID.setId(Integer.valueOf(v));
        return st_refID;
    }

    @Override
    public String marshal(ST_RefID v) throws Exception {
        if(v==null)return null;
        return v.getId().toString();
    }
}