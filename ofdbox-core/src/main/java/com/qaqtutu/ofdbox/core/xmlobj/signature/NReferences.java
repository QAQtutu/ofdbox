package com.qaqtutu.ofdbox.core.xmlobj.signature;

import lombok.Data;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NReferences {

    @XmlAttribute(name = "CheckMethod")
    private String checkMethod;

    @Size(min = 1)
    @XmlElement(name = "ofd:Reference")
    private List<NReference> list;
}
