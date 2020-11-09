package com.ofdbox.core.xmlobj.object.image;

import com.ofdbox.core.xmlobj.adapter.DoubleArrayAdapter;
import com.ofdbox.core.xmlobj.pagedesc.color.CT_Color;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NImageBorder {

    /*
    * 边框线宽
    * */
    @XmlAttribute(name = "LineWidth")
    private Double lineWidth;

    /*
    * 边框水平角半径
    * */
    @XmlAttribute(name = "HorizonalCornerRadius")
    private Double horizonalCornerRadius;

    /*
    * 边框垂直角半径
    * */
    @XmlAttribute(name = "VerticalCornerRadius")
    private Double verticalCornerRadius;

    /*
    * 边框虚线重复样式开始的位置
    * */
    @XmlAttribute(name = "DashOffset")
    private Double dashOffset;

    /*
    * 边框虚线重复样式
    * */
    @XmlJavaTypeAdapter(value = DoubleArrayAdapter.class)
    @XmlAttribute(name = "DashPattern")
    private Double[] dashPattern;

    /*
    * 边框颜色
    * */
    @XmlElement(name = "ofd:BorderColor")
    private CT_Color borderColor;
}
