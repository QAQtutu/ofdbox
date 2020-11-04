package com.ofdbox.core.test.adapter;

import com.ofdbox.core.xmlobj.adapter.DateAdapter;

import java.util.Date;

public class DateAdapterTest extends AdapterTest<DateAdapter,Date>{

    @Override
    public DateAdapter getAdapter() {
        return new DateAdapter();
    }

    @Override
    public String getString() {
        return "2020-10-06T16:05:33";
    }

    @Override
    public void testObject(Date date) {

    }
}
