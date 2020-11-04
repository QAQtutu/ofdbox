package com.ofdbox.core.xmlobj.graphic;

import com.ofdbox.core.xmlobj.st.ST_Pos;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class QuadraticBezier extends PathOperator {

    /*
     * 二次贝塞尔曲线的控制点
     * */
    @NotNull
    @XmlAttribute(name = "Point1")
    private ST_Pos point1;

    /*
     * 二次贝塞尔曲线的结束点
     * */
    @NotNull
    @XmlAttribute(name = "Point2")
    private ST_Pos point2;
}
