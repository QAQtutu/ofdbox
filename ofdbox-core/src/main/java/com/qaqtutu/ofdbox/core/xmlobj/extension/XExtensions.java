package com.qaqtutu.ofdbox.core.xmlobj.extension;

import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "Extensions",namespace = Const.NAMESPACE_URI)
public class XExtensions {

    @Valid
    @XmlElement(name = "Extension",namespace = Const.NAMESPACE_URI)
    private List<CT_Extension> extensions;
}
