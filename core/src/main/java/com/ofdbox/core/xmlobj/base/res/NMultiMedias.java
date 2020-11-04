package com.ofdbox.core.xmlobj.base.res;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NMultiMedias {

    @XmlElement(name = "ofd:MultiMedia")
    private List<NMultiMedia> list;
}
