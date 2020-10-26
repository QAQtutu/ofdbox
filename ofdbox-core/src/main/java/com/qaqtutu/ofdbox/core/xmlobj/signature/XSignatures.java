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
@XmlRootElement(name = "Signatures",namespace = Const.NAMESPACE_URI)
public class XSignatures {

    @XmlElement(name = "MaxSignId",namespace = Const.NAMESPACE_URI)
    private String id;

    @XmlElement(name = "Signature",namespace = Const.NAMESPACE_URI)
    private List<NSignature> signatures;
}
