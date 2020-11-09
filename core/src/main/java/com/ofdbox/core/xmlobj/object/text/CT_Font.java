package com.ofdbox.core.xmlobj.object.text;

import com.ofdbox.core.xmlobj.adapter.StIdAdapter;
import com.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.ofdbox.core.xmlobj.st.ST_ID;
import com.ofdbox.core.xmlobj.st.ST_Loc;
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
public class CT_Font {


    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StIdAdapter.class)
    @XmlAttribute(name = "ID")
    private ST_ID id;

    /*
    * 字型名
    * */
    @NotNull
    @XmlAttribute(name = "FontName")
    private String fontName;

    /*
    * 字型族名
    * */
    @XmlAttribute(name = "FamilyName")
    private String familyName;

    /*
    * 字型适用的字符分类
    * */
    @XmlAttribute(name = "Charset")
    private Charset charset;

    /*
    * 是否是倾斜字型
    * */
    @XmlAttribute(name = "Italic")
    private Boolean italic;

    /*
    * 是否是粗体字型
    * */
    @XmlAttribute(name = "Bold")
    private Boolean bold;

    /*
    * 是否是带衬线字型
    * */
    @XmlAttribute(name = "Serif")
    private Boolean serif;

    /*
    * 是否是等宽字型
    * */
    @XmlAttribute(name = "FixedWidth")
    private Boolean fixedWidth;

    /*
    * 指向内嵌字型文件
    * */
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "ofd:FontFile")
    private ST_Loc fontFile;
}
