package com.ofdbox.core.xmlobj.adapter;

import com.ofdbox.core.xmlobj.st.ST_ID;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class StIdAdapter  extends XmlAdapter< String, ST_ID> {
    @Override
    public ST_ID unmarshal(String v) throws Exception {
        ST_ID st_id=new ST_ID();
        st_id.setId(new Integer(v));
        return st_id;
    }

    @Override
    public String marshal(ST_ID v) throws Exception {
        return String.valueOf(v.getId());
    }
}
