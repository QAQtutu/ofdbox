package com.ofdbox.core.xmlobj.base.res;

import com.ofdbox.core.xmlobj.pagedesc.CT_DrawParam;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NDrawParams {

    @Valid
    @Size(min = 1)
    @XmlElement(name = "ofd:DrawParam")
    private List<CT_DrawParam> list;
}
