package com.qaqtutu.ofdbox.core.xmlobj.object.text;

import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.xmlobj.adapter.StringArrayAdapter;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CT_CGTransform {

    /*
    * TextCode中字符编码的起始位置
    * */
    @NotNull
    @XmlAttribute(name = "CodePosition")
    private Integer codePosition;

    /*
    * 变换关系中字符的数量
    * */
    @XmlAttribute(name = "CodeCount")
    private Integer codeCount;

    /*
    * 变换关系中字型索引的个数
    * */
    @XmlAttribute(name = "GlyphCount")
    private Integer glyphCount;

    /*
    * 变换后的字型索引列表
    * */
    @NotNull
    @XmlJavaTypeAdapter(value = StringArrayAdapter.class)
    @XmlElement(name = "ofd:Glyphs")
    private String[] glyphs;
}
