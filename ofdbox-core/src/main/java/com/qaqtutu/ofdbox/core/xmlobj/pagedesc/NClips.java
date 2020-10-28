package com.qaqtutu.ofdbox.core.xmlobj.pagedesc;

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
public class NClips {

    @Valid
    @Size(min = 1)
    @XmlElement(name = "ofd:Clip")
    private List<CT_Clip> list;
}
