package com.qaqtutu.ofdbox.core.xmlobj.customtags;

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
@XmlRootElement(name = "CustomTags",namespace = Const.NAMESPACE_URI)
public class XCustomTags {

    @Valid
    @XmlElement(name = "CustomTag",namespace = Const.NAMESPACE_URI)
    private List<NCustomTag> tags;
}
