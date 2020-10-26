package com.qaqtutu.ofdbox.core.xmlobj.graphic;

import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Pos;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Arc extends PathOperator {

    @XmlAttribute(name = "EndPoint")
    private ST_Pos endPoint;

    @XmlElement(name = "EllipscSize")
    private Double[] ellipscSize;

    @XmlElement(name = "RotationAngle")
    private Double rotationAngle;

    @XmlElement(name = "LargeArc")
    private Boolean largeArc;

    @XmlElement(name = "SweepDirection")
    private Boolean sweepDirection;
}
