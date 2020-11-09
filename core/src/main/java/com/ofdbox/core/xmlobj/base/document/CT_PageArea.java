package com.ofdbox.core.xmlobj.base.document;

import com.ofdbox.core.xmlobj.adapter.StBoxAdapter;
import com.ofdbox.core.xmlobj.st.ST_Box;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CT_PageArea {

    @XmlJavaTypeAdapter(StBoxAdapter.class)
    @NotNull
    @XmlElement(name = "ofd:PhysicalBox")
    private ST_Box physicalBox;

    @XmlJavaTypeAdapter(StBoxAdapter.class)
    @XmlElement(name = "ofd:ApplicationBox")
    private ST_Box applicationBox;

    @XmlJavaTypeAdapter(StBoxAdapter.class)
    @XmlElement(name = "ofd:ContentBox")
    private ST_Box contentBox;

    @XmlJavaTypeAdapter(StBoxAdapter.class)
    @XmlElement(name = "ofd:BleedBox")
    private ST_Box bleedBox;

}
