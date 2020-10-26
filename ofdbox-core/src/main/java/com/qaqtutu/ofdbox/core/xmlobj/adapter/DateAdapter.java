package com.qaqtutu.ofdbox.core.xmlobj.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter extends BaseAdapter<Date> {

    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    @Override
    public Date unmarshal1(String v) throws Exception {
        return format.parse(v);
    }

    @Override
    public String marshal1(Date v) throws Exception {
        return format.format(v);
    }
}
