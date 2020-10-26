package com.qaqtutu.ofdbox.core.xmlobj.signature;

import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NSignedInfo {

    @NotNull
    @Valid
    @XmlElement(name = "Provider",namespace = Const.NAMESPACE_URI)
    private NProvider provider;

    @NotNull
    @Valid
    @XmlElement(name = "SignatureMethod",namespace = Const.NAMESPACE_URI)
    private String signatureMethod;

    @NotNull
    @Valid
    @XmlElement(name = "SignatureDateTime",namespace = Const.NAMESPACE_URI)
    private String signatureDateTime;

    @NotNull
    @Valid
    @XmlElement(name = "References",namespace = Const.NAMESPACE_URI)
    private NReferences references;

    @XmlElement(name = "StampAnnot",namespace = Const.NAMESPACE_URI)
    private List<NStampAnnot> stampAnnots;

    @XmlElement(name = "Seal",namespace = Const.NAMESPACE_URI)
    private NSeal seal;
}
