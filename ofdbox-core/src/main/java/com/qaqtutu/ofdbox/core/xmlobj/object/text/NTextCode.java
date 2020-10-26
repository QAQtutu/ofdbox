package com.qaqtutu.ofdbox.core.xmlobj.object.text;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NTextCode {

    @XmlAttribute(name = "X")
    private Double x;

    @XmlAttribute(name = "Y")
    private Double y;

    @XmlAttribute(name = "DeltaX")
    private String deltaX;

    @XmlAttribute(name = "DeltaY")
    private String deltaY;

    @XmlValue
    private String content;
}
