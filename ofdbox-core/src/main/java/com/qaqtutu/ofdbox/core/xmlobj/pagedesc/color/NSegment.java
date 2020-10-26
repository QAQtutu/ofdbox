package com.qaqtutu.ofdbox.core.xmlobj.pagedesc.color;

import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NSegment{
    @XmlAttribute(name = "Position")
    private Double position;

    @NotNull
    @XmlElement(name = "Color",namespace = Const.NAMESPACE_URI)
    private CT_Color color;
}
