package com.qaqtutu.ofdbox.core.xmlobj.signature;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NProvider {

    @NotNull
    @XmlAttribute(name = "ProviderName")
    private String providerName;

    @NotNull
    @XmlAttribute(name = "Version")
    private String version;

    @NotNull
    @XmlAttribute(name = "Company")
    private String company;
}
