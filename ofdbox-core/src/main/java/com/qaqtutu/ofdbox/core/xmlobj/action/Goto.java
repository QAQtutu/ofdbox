package com.qaqtutu.ofdbox.core.xmlobj.action;


import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Goto extends ActionType{

    @XmlElement(name = "ofd:Dest")
    private CT_Dest dest;

    @Valid
    @XmlElement(name = "ofd:Bookmark")
    private Bookmark bookmark;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Bookmark {

        @NotBlank
        @XmlAttribute(name = "Name")
        private String name;
    }
}
