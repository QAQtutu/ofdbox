package com.qaqtutu.ofdbox.core.xmlobj.base.ofd;

import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/*
* 关键词集合
* */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class NKeywords {

    @Size(min = 1)
    @XmlElement(name = "Keyword", namespace = Const.NAMESPACE_URI)
    private List<String> list;
}
