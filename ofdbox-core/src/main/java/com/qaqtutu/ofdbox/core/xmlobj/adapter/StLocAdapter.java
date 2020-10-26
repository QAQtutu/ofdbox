package com.qaqtutu.ofdbox.core.xmlobj.adapter;

import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @description: ST_Loc适配器
 * @author: 张家尧
 * @create: 2020/10/01 13:17
 */
public class StLocAdapter extends XmlAdapter< String, ST_Loc> {
    @Override
    public ST_Loc unmarshal(String v) throws Exception {
        ST_Loc loc=new ST_Loc();
        loc.setLoc(v);
        return loc;
    }

    @Override
    public String marshal(ST_Loc v) throws Exception {
        return v.getLoc();
    }
}