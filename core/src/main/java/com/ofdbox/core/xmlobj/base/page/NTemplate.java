package com.ofdbox.core.xmlobj.base.page;

import com.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import com.ofdbox.core.xmlobj.enums.LayerType;
import com.ofdbox.core.xmlobj.st.ST_RefID;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class NTemplate {

    /*
     * 引用文档公用数据（CommonData）中定义的模板页标识
     * */
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @XmlAttribute(name = "TemplateID")
    private ST_RefID templateId;

    /*
     * 呈现顺序
     * */
    @XmlAttribute(name = "ZOrder")
    private LayerType zOrder;
}
