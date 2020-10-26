package com.qaqtutu.ofdbox.core.xmlobj.base.document;

import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Box;
import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.xmlobj.adapter.StBoxAdapter;
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
    @XmlElement(name = "PhysicalBox", namespace = Const.NAMESPACE_URI)
    private ST_Box physicalBox;

    @XmlJavaTypeAdapter(StBoxAdapter.class)
    @XmlElement(name = "ApplicationBox", namespace = Const.NAMESPACE_URI)
    private ST_Box applicationBox;

    @XmlJavaTypeAdapter(StBoxAdapter.class)
    @XmlElement(name = "ContentBox", namespace = Const.NAMESPACE_URI)
    private ST_Box contentBox;

    @XmlJavaTypeAdapter(StBoxAdapter.class)
    @XmlElement(name = "BleedBox", namespace = Const.NAMESPACE_URI)
    private ST_Box bleedBox;

}
