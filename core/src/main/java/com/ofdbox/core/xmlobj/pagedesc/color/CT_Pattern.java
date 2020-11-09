package com.ofdbox.core.xmlobj.pagedesc.color;

import com.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import com.ofdbox.core.xmlobj.enums.RefleetMethod;
import com.ofdbox.core.xmlobj.enums.RelativeTo;
import com.ofdbox.core.xmlobj.base.page.CT_PageBlock;
import com.ofdbox.core.xmlobj.st.ST_RefID;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/*
* 底纹填充
* */
@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CT_Pattern extends ComplexColor {

    /*
    * 底纹单元的宽度
    * */
    @NotNull
    @XmlAttribute(name = "Width")
    private Double width;

    /*
    * 底纹单元的高度
    * */
    @NotNull
    @XmlAttribute(name = "height")
    private Double Height;

    /*
    * X方向底纹单元间距
    * */
    @XmlAttribute(name = "XStep")
    private Double xStep;

    /*
    * Y方向底纹单元宽度
    * */
    @XmlAttribute(name = "YStep")
    private Double yStep;

    /*
    * 描述底纹单元的映像反转方式
    * */
    @XmlAttribute(name = "RefleetMethod")
    private RefleetMethod refleetMethod;

    /*
    * 底纹单元起始绘制位置
    * */
    @XmlAttribute(name = "RelativeTo")
    private RelativeTo relativeTo;

    /*
    * 底纹单元变换矩阵
    * */
    @XmlAttribute(name = "CTM")
    private Double[] ctm;

    /*
    * 底纹单元
    * */
    @NotNull
    @Valid
    @XmlElement(name = "ofd:CellContent")
    private NCellContent cellContent;


    @Data
    @XmlAccessorType(value = XmlAccessType.FIELD)
    public static class NCellContent extends CT_PageBlock {

        /*
        * 引用资源文件中缩略图图像的标识
        * */
        @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
        @XmlAttribute(name = "Thumbnail")
        private ST_RefID thumbnail;
    }
}
