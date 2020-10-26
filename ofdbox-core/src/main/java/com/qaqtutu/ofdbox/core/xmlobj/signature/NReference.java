package com.qaqtutu.ofdbox.core.xmlobj.signature;

import com.qaqtutu.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
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
public class NReference {

    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlAttribute(name = "FileRef")
    private ST_Loc fileRef;

    @NotNull
    @Valid
    @XmlElement(name = "CheckValue")
    private String checkValue;
}
