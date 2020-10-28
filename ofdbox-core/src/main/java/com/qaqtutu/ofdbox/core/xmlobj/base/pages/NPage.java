package com.qaqtutu.ofdbox.core.xmlobj.base.pages;

import com.qaqtutu.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;

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

    @XmlAttribute(name = "ID")
    private String id;

    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlAttribute(name = "BaseLoc")
    private ST_Loc baseLoc;
}