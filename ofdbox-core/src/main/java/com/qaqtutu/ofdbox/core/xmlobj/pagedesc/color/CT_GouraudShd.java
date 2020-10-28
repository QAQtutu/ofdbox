package com.qaqtutu.ofdbox.core.xmlobj.pagedesc.color;

import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import java.util.List;

/*
* 高洛德渐变
* */
@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CT_GouraudShd extends ComplexColor {

    /*
    * 在渐变控制点所确定范围之外的部分是否填充
    * */
    @XmlAttribute(name = "Extend")
    private Integer extend;

    /*
    * 渐变控制点
    * */
    @Size(min = 3)
    @XmlElement(name = "ofd:Point",type = NPoint.class)
    private List<NPoint> points;

    /*
    * 渐变范围外的填充颜色
    * */
    @XmlElement(name = "ofd:BackColor")
    private CT_Color backColor;


    @Data
    @XmlAccessorType(value = XmlAccessType.FIELD)
    @XmlRootElement(name = "ofd:NPoint")
    public static class NPoint{

        /*
        * 控制点水平位置
        * */
        @NotNull
        @XmlAttribute(name = "X")
        private Double x;

        /*
        * 控制点垂直位置
        * */
        @NotNull
        @XmlAttribute(name = "Y")
        private Double y;

        /*
        * 三角单元切换的方向标志
        * */
        @XmlAttribute(name = "EdgeFlag")
        private Integer edgeFlag;

        /*
        * 控制点对应的颜色
        * */
        @NotNull
        @XmlElement(name = "ofd:Color")
        private CT_Color color;
    }
}
