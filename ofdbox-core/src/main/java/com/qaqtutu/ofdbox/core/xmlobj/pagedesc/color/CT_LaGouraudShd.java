package com.qaqtutu.ofdbox.core.xmlobj.pagedesc.color;

import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CT_LaGouraudShd extends ComplexColor {

    /*
    * 渐变区域内每行的网格树
    * */
    @NotNull
    @XmlAttribute(name = "VerticesPerRow")
    private Integer verticesPerRow;

    /*
    * 在渐变控制点所确定范围之外的部分是否填充
    * */
    @XmlAttribute(name = "Extend")
    private Integer extend;

    /*
    * 渐变控制点
    * */
    @Valid
    @Size(min = 4)
    @XmlElement(name = "Point",namespace = Const.NAMESPACE_URI,type = NLaPoint.class)
    private List<NLaPoint> points;

    /*
    * 渐变范围外的填充颜色
    * */
    @XmlElement(name = "BackColor",namespace = Const.NAMESPACE_URI)
    private CT_Color backColor;


    @Data
    @XmlAccessorType(value = XmlAccessType.FIELD)
    public static class NLaPoint{

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
         * 控制点对应的颜色
         * */
        @NotNull
        @XmlElement(name = "Color",namespace = Const.NAMESPACE_URI)
        private CT_Color color;
    }
}
