package com.qaqtutu.ofdbox.core.xmlobj.pagedesc.color;

import com.qaqtutu.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.qaqtutu.ofdbox.core.xmlobj.enums.ColorSpaceType;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class CT_ColorSpace {
    @XmlAttribute(name = "Type")
    private ColorSpaceType type;

    @XmlAttribute(name = "BitsPerComponent")
    private Integer bitsPerComponent;

    @XmlAttribute(name = "Profile")
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    private ST_Loc profile;
}
