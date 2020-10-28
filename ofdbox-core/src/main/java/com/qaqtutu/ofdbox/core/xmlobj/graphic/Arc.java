package com.qaqtutu.ofdbox.core.xmlobj.graphic;

import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Pos;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Arc extends PathOperator {

    @XmlAttribute(name = "EndPoint")
    private ST_Pos endPoint;

    @XmlElement(name = "ofd:EllipscSize")
    private Double[] ellipscSize;

    @XmlElement(name = "ofd:RotationAngle")
    private Double rotationAngle;

    @XmlElement(name = "ofd:LargeArc")
    private Boolean largeArc;

    @XmlElement(name = "ofd:SweepDirection")
    private Boolean sweepDirection;
}
