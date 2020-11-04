package com.ofdbox.core.xmlobj.object.text;

public enum Charset {
    symbol,
    prc,
    big5,
    unicode;

    public static Charset defaultValue=Charset.unicode;
}
