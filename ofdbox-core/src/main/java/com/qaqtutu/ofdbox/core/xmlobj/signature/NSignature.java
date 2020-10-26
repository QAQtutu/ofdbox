package com.qaqtutu.ofdbox.core.xmlobj.signature;

import com.qaqtutu.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NSignature {

    @NotBlank
    @XmlAttribute(name = "ID")
    private String id;

    @XmlAttribute(name = "Type")
    private Type type;

    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlAttribute(name = "BaseLoc")
    private ST_Loc baseLoc;
}
