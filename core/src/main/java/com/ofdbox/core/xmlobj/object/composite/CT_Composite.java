package com.ofdbox.core.xmlobj.object.composite;

import com.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import com.ofdbox.core.xmlobj.st.ST_RefID;
import com.ofdbox.core.xmlobj.pagedesc.CT_GraphicUnit;

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
