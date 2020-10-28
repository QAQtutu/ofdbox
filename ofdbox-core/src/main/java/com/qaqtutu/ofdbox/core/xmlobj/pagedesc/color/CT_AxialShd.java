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

/*
* 轴向渐变
* */
@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CT_AxialShd extends ComplexColor {

    /*
    * 渐变绘制方式
    * */
    @XmlAttribute(name = "MapType")
    private MapType mapType;

    /*
    * 渐变区间长度
    * */
    @XmlAttribute(name = "MapUnit")
    private Double mapUnit;

    /*
    * 轴线延长线方向是否继续绘制
    * */
    @XmlAttribute(name = "Extend")
    private Integer extend;

    /*
    * 轴线起始点
    * */
    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StPosAdapter.class)
    @XmlAttribute(name = "StartPoint")
    private ST_Pos startPoint;

    /*
    * 轴线结束点
    * */
    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StPosAdapter.class)
    @XmlAttribute(name = "EndPoint")
    private ST_Pos endPoint;

    /*
    * 颜色段
    * */
    @Valid
    @Size(min = 2)
    @XmlElement(name = "ofd:Segment")
    private List<NSegment> segment;


}
