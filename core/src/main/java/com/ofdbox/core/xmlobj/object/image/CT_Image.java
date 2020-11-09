package com.ofdbox.core.xmlobj.object.image;

import com.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import com.ofdbox.core.xmlobj.st.ST_RefID;
import com.ofdbox.core.xmlobj.pagedesc.CT_GraphicUnit;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CT_Image extends CT_GraphicUnit {

    /*
    * 引用资源文件中定义的多媒体标识
    * */
    @NotNull
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @XmlAttribute(name = "ResourceID")
    private ST_RefID resourceId;

    /*
    * 可替换图像
    * */
    @Valid
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @XmlAttribute(name = "Substitution")
    private ST_RefID substitution;

    /*
    * 图像蒙版
    * */
    @Valid
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @XmlAttribute(name = "ImageMask")
    private ST_RefID imageMask;

    /*
    * 图像边框设置
    * */
    @Valid
    @XmlElement(name = "ofd:Border")
    private NImageBorder border;
}
