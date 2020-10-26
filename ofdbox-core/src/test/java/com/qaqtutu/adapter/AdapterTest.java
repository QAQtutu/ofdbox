package com.qaqtutu.adapter;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public abstract  class  AdapterTest<T extends XmlAdapter<String,S>,S> {

    @Test
    public void test() throws Exception {
        String string=getString();
        T t=getAdapter();
        S s=t.unmarshal(string);
        String format=t.marshal(s);
        Assert.assertEquals(string,format);

        testObject(s);
    }

    public abstract T getAdapter();
    public abstract String getString();
    public abstract void testObject(S s);
}
