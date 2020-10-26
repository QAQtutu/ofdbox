package com.qaqtutu.ofdbox.core.xmlobj.object.composite;

import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.xmlobj.base.page.CT_PageBlock;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_RefID;
import com.qaqtutu.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CT_VectorG {

    /*
    * 矢量图的宽度
    * */
    @NotNull
    @XmlAttribute(name = "Width")
    private Double width;

    /*
    * 矢量图的高度
    * */
    @NotNull
    @XmlAttribute(name = "Height")
    private Double height;

    /*
    * 缩略图
    * */
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @XmlElement(name = "Thumbnail",namespace = Const.NAMESPACE_URI)
    private ST_RefID thumbnail;

    /*
    * 替换图像
    * */
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @XmlElement(name = "Substitution",namespace = Const.NAMESPACE_URI)
    private ST_RefID substitution;

    /*
    * 内容矢量描述
    * */
    @NotNull
    @XmlElement(name = "Content",namespace = Const.NAMESPACE_URI)
    private CT_PageBlock content;
}
