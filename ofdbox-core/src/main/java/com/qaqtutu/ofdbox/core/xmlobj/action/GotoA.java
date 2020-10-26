package com.qaqtutu.ofdbox.core.xmlobj.action;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class GotoA extends ActionType{

    @NotBlank
    @XmlAttribute(name = "AttachID")
    private String attachId;


    @XmlAttribute(name = "NewWindow")
    private Boolean newWindow;

}
