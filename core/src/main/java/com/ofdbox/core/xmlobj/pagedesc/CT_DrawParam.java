package com.ofdbox.core.xmlobj.pagedesc;

import com.ofdbox.core.xmlobj.adapter.DoubleArrayAdapter;
import com.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import com.ofdbox.core.xmlobj.enums.LineJoinStyle;
import com.ofdbox.core.xmlobj.pagedesc.color.CT_Color;
import com.ofdbox.core.xmlobj.enums.LineCapStyle;
import com.ofdbox.core.xmlobj.st.ST_RefID;
import com.ofdbox.core.utils.OfdXmlUtils;
import lombok.Data;

import javax.validation.Valid;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.ByteArrayInputStream;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement
public class CT_DrawParam {

    /*
    * 基础绘制参数，引用资源文件
    * */
    @Valid
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @XmlAttribute(name="Relative")
    private ST_RefID relative;

    /*
    * 线宽
    * */
    @XmlAttribute(name = "LineWidth")
    private Double lineWidth;

    /*
    * 线条连接样式
    * */
    @XmlAttribute(name = "Join")
    private LineJoinStyle join;

    /*
    * 线端点样式
    * */
    @XmlAttribute(name = "Cap")
    private LineCapStyle cap;

    /*
    * 线头虚线样式开始位置
    * */
    @XmlAttribute(name = "DashOffset")
    private Double dashOffset;

    /*
    * 线条虚线的重复样式
    * */
    @XmlJavaTypeAdapter(value = DoubleArrayAdapter.class)
    @XmlAttribute(name = "DashPattern")
    private Double[] dashPattern;

    /*
    * Join为Miter时小角度结合点长度的截断值 默认3.528
    * */
    @XmlAttribute(name = "MiterLimit")
    private Double miterLimit;

    /*
    * 填充色
    * */
    @XmlElement(name = "ofd:FillColor")
    private CT_Color fillColor;

    /*
    * 勾边颜色
    * */
    @XmlElement(name = "ofd:StrokeColor")
    private CT_Color strokeColor;

    public static void main(String[] args) {
        CT_DrawParam drawParam=new CT_DrawParam();
        drawParam.setCap(LineCapStyle.Butt);
        drawParam.setDashPattern(new Double[]{1.0,2.0,3.0});
        System.out.println(OfdXmlUtils.toXml(drawParam));

        String xml="<ctDrawParam Cap=\"Butt\"/>";
        System.out.println(OfdXmlUtils.toObject(new ByteArrayInputStream(xml.getBytes()),CT_DrawParam.class));
    }
}
