package com.ofdbox.core.xmlobj.signature;

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
    @XmlElement(name = "ofd:Provider")
    private NProvider provider;

    @NotNull
    @Valid
    @XmlElement(name = "ofd:SignatureMethod")
    private String signatureMethod;

    @NotNull
    @Valid
    @XmlElement(name = "ofd:SignatureDateTime")
    private String signatureDateTime;

    @NotNull
    @Valid
    @XmlElement(name = "ofd:References")
    private NReferences references;

    @XmlElement(name = "ofd:StampAnnot")
    private List<NStampAnnot> stampAnnots;

    @XmlElement(name = "ofd:Seal")
    private NSeal seal;
}
