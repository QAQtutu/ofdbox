package com.ofdbox.core.xmlobj.base.page;

import com.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import com.ofdbox.core.xmlobj.enums.LayerType;
import com.ofdbox.core.xmlobj.st.ST_RefID;
import lombok.Data;

import javax.validation.Valid;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class NLayer  extends CT_PageBlock {

    @XmlAttribute(name = "ID")
    private String id;


    /*
     * 模板页默认图层类型
     * */
    @XmlAttribute(name = "Type")
    private LayerType type;

    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @Valid
    @XmlAttribute(name = "DrawParam")
    private ST_RefID drawParam;
}
