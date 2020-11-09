package com.ofdbox.core.xmlobj.object.composite;

import com.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import com.ofdbox.core.xmlobj.base.page.CT_PageBlock;
import com.ofdbox.core.xmlobj.st.ST_RefID;
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
    @XmlElement(name = "ofd:Thumbnail")
    private ST_RefID thumbnail;

    /*
    * 替换图像
    * */
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @XmlElement(name = "ofd:Substitution")
    private ST_RefID substitution;

    /*
    * 内容矢量描述
    * */
    @NotNull
    @XmlElement(name = "ofd:Content")
    private CT_PageBlock content;
}
