package com.qaqtutu.ofdbox.core.xmlobj.signature;

import com.qaqtutu.ofdbox.core.xmlobj.adapter.StBoxAdapter;
import com.qaqtutu.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Box;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_RefID;
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
public class NStampAnnot {

    @NotBlank
    @XmlAttribute(name = "ID")
    private String id;

    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @XmlAttribute(name = "PageRef")
    private ST_RefID pageRef;

    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StBoxAdapter.class)
    @XmlAttribute(name = "Boundary")
    private ST_Box boundary;

    @Valid
    @XmlJavaTypeAdapter(value = StBoxAdapter.class)
    @XmlAttribute(name = "Clip")
    private ST_Box clip;
}
