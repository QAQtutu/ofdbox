package com.ofdbox.core.xmlobj.base.pages;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class NPages {

    @Valid
    @Size(min = 1)
    @XmlElement(name = "ofd:Page")
    List<NPage> list;
}
