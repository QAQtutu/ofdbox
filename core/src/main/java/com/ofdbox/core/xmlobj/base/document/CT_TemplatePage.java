package com.ofdbox.core.xmlobj.base.document;

import com.ofdbox.core.xmlobj.adapter.StIdAdapter;
import com.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.ofdbox.core.xmlobj.enums.LayerType;
import com.ofdbox.core.xmlobj.st.ST_ID;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CT_TemplatePage {

    /*
    * 模板页标识
    * */
    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StIdAdapter.class)
    @XmlAttribute(name = "ID")
    private ST_ID id;

    /*
    * 指向模板也内容描述文件
    * */
    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlAttribute(name = "BaseLoc")
    private ST_Loc baseLoc;

    /*
    * 模板页名称
    * */
    @XmlAttribute(name = "Name")
    private String name;

    /*
    * 模板页默认图层类型
    * */
    @XmlAttribute(name = "ZOrder")
    private LayerType zOrder;

}
