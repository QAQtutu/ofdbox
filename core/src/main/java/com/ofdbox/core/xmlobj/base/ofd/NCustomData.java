package com.ofdbox.core.xmlobj.base.ofd;


import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class NCustomData {


    @XmlValue
    private String value;

    @XmlAttribute(name = "Name")
    private String name;
}
