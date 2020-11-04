package com.ofdbox.core.xmlobj.extension;

import lombok.Data;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "ofd:Extensions")
public class XExtensions {

    @Valid
    @XmlElement(name = "ofd:Extension")
    private List<CT_Extension> extensions;
}
