package com.ofdbox.core.xmlobj.base.document;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CT_Permission {

    @XmlElement(name = "ofd:Edit")
    private Boolean edit;

    @XmlElement(name = "ofd:Annot")
    private Boolean annot;

    @XmlElement(name = "ofd:Export")
    private Boolean export;

    @XmlElement(name = "ofd:Signature")
    private Boolean signature;

    @XmlElement(name = "ofd:Watermark")
    private Boolean watermark;

    @XmlElement(name = "ofd:PrintScreen")
    private Boolean printScreen;

    @XmlElement(name = "ofd:Print")
    private Print print;

    @XmlElement(name = "ofd:ValidPeriod")
    private ValidPeriod validPeriod;


    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Print {
        @XmlAttribute(name = "Printable")
        private Boolean printable;

        @XmlAttribute(name = "Copies")
        private int copies;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ValidPeriod {
        @XmlAttribute(name = "StartDate")
        private Boolean startDate;

        @XmlAttribute(name = "EndDate")
        private Boolean endDate;
    }
}
