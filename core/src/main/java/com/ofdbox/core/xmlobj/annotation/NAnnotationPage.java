package com.ofdbox.core.xmlobj.annotation;

import com.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import com.ofdbox.core.xmlobj.st.ST_RefID;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NAnnotationPage {

    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @XmlAttribute(name = "PageID")
    private ST_RefID pageId;

    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "ofd:FileLoc")
    private ST_Loc fileLoc;
}
