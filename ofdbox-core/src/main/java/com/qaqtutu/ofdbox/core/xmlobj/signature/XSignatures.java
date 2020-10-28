package com.qaqtutu.ofdbox.core.xmlobj.signature;

import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "ofd:Signatures")
public class XSignatures {

    @XmlElement(name = "ofd:MaxSignId")
    private String id;

    @XmlElement(name = "ofd:Signature")
    private List<NSignature> signatures;
}
