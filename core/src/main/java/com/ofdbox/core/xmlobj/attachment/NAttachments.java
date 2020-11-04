package com.ofdbox.core.xmlobj.attachment;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NAttachments {


    @XmlElement(name = "ofd:Attachment")
    private List<CT_Attachment> list;

}
