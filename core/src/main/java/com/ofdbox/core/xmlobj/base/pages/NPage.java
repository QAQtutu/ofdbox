package com.ofdbox.core.xmlobj.base.pages;

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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @description: 页节点
 * @author: 张家尧
 * @create: 2020/09/30 17:22
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class NPage {

    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StIdAdapter.class)
    @XmlAttribute(name = "ID")
    private ST_ID id;

    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlAttribute(name = "BaseLoc")
    private ST_Loc baseLoc;
}