package com.ofdbox.core.xmlobj.annotation;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "ofd:Annotations")
public class XAnnotations {

    @NotBlank
    @XmlElement(name = "ofd:MaxSignID")
    private String maxSignID;

    @Valid
    @XmlElement(name = "ofd:Page")
    private List<NAnnotationPage> pages;
}
