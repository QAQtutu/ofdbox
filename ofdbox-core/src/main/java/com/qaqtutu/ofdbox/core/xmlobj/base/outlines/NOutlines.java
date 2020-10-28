package com.qaqtutu.ofdbox.core.xmlobj.base.outlines;

import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class NOutlines {

    @Size(min = 1)
    @Valid
    @XmlElement(name = "ofd:OutlineElem")
    private List<CT_OutlineElem> list;
}
