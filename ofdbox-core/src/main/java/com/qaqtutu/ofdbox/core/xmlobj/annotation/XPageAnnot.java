package com.qaqtutu.ofdbox.core.xmlobj.annotation;

import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "ofd:PageAnnot")
public class XPageAnnot {

    @Size(min = 1)
    @Valid
    @XmlElement(name = "ofd:Annot")
    private List<NAnnot> annots;
}
