package com.ofdbox.core.test.adapter;

import com.ofdbox.core.xmlobj.adapter.DoubleArrayAdapter;

public class DoubleArrayAdapterTest extends AdapterTest<DoubleArrayAdapter,Double[]>{

    @Override
    public DoubleArrayAdapter getAdapter() {
        return new DoubleArrayAdapter();
    }

    @Override
    public String getString() {
        return "10 23.1 11.9";
    }

    @Override
    public void testObject(Double[] doubles) {

    }
}
