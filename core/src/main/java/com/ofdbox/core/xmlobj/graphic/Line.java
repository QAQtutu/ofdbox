package com.ofdbox.core.xmlobj.graphic;

import com.ofdbox.core.xmlobj.st.ST_Pos;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Line extends PathOperator {

    /*
    * 线段结束点
    * */
    @NotNull
    @XmlAttribute(name = "Point1")
    private ST_Pos point1;
}
