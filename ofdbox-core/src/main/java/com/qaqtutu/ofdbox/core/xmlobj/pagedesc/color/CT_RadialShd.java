package com.qaqtutu.ofdbox.core.xmlobj.pagedesc.color;

import com.qaqtutu.ofdbox.core.xmlobj.adapter.StPosAdapter;
import com.qaqtutu.ofdbox.core.xmlobj.enums.MapType;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Pos;
import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CT_RadialShd extends ComplexColor {

    /*
    * 渐变绘制的方式
    * */
    @XmlAttribute(name = "MapType")
    private MapType mapType;

    /*
    * 中心点连线上一个区间所绘制的长度
    * */
    @XmlAttribute(name = "MapUnit")
    private Double mapUnit;

    /*
    * 两个椭圆的离心率
    * */
    @XmlAttribute(name = "Eccentricity")
    private Double eccentricity;

    /*
    * 两个椭圆的倾斜角度
    * */
    @XmlAttribute(name = "Angle")
    private Double angle;

    /*
    * 起始椭圆的中心点
    * */
    @NotNull
    @XmlJavaTypeAdapter(value = StPosAdapter.class)
    @XmlAttribute(name = "StartPoint")
    private ST_Pos startPoint;

    /*
    * 起始椭圆的半长轴
    * */
    @XmlAttribute(name = "StartRadius")
    private Double startRadius;

    /*
    * 结束椭圆的中心点
    * */
    @XmlJavaTypeAdapter(value = StPosAdapter.class)
    @XmlAttribute(name = "EndPoint")
    private ST_Pos endPoint;

    /*
    * 结束椭圆的半长轴
    * */
    @NotNull
    @XmlAttribute(name = "EndRadius")
    private Double endRaduis;

    /*
    * 径向延长线方向是否继续绘制
    * */
    @XmlAttribute(name = "Extend")
    private Integer extend;

    /*
     * 颜色段
     * */
    @Valid
    @Size(min = 2)
    @XmlElement(name = "Segment",namespace = Const.NAMESPACE_URI)
    private List<NSegment> segment;
}
