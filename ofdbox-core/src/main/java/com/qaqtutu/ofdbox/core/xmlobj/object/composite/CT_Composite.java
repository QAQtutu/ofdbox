package com.qaqtutu.ofdbox.core.xmlobj.object.composite;

import com.qaqtutu.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import com.qaqtutu.ofdbox.core.xmlobj.pagedesc.CT_GraphicUnit;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_RefID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class CT_Composite extends CT_GraphicUnit {

    @NotNull
    @Valid
    @XmlAttribute(name = "ResourceID")
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    private ST_RefID resourceId;
}
