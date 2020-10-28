package com.qaqtutu.ofdbox.core.xmlobj.base.res;

import com.qaqtutu.ofdbox.core.xmlobj.pagedesc.color.CT_ColorSpace;
import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NColorSpaces {

    @Size(min = 1)
    @Valid
    @XmlElement(name = "ofd:ColorSpace")
    private List<CT_ColorSpace> list;

}
