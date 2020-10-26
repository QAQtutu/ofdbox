package com.qaqtutu.ofdbox.core.xmlobj.base.document;

import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CT_Permission {

    @XmlElement(name = "Edit", namespace = Const.NAMESPACE_URI)
    private Boolean edit;

    @XmlElement(name = "Annot", namespace = Const.NAMESPACE_URI)
    private Boolean annot;

    @XmlElement(name = "Export", namespace = Const.NAMESPACE_URI)
    private Boolean export;

    @XmlElement(name = "Signature", namespace = Const.NAMESPACE_URI)
    private Boolean signature;

    @XmlElement(name = "Watermark", namespace = Const.NAMESPACE_URI)
    private Boolean watermark;

    @XmlElement(name = "PrintScreen", namespace = Const.NAMESPACE_URI)
    private Boolean printScreen;

    @XmlElement(name = "Print", namespace = Const.NAMESPACE_URI)
    private Print print;

    @XmlElement(name = "ValidPeriod", namespace = Const.NAMESPACE_URI)
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
