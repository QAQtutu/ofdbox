package com.qaqtutu.ofdbox.core.xmlobj.version;

import com.qaqtutu.ofdbox.core.xmlobj.st.ST_ID;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
import com.qaqtutu.ofdbox.core.xmlobj.adapter.StIdAdapter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class NFile extends ST_Loc {

    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StIdAdapter.class)
    @XmlAttribute(name = "ID")
    private ST_ID id;
}
