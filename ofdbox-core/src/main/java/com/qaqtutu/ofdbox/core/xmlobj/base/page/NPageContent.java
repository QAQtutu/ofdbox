package com.qaqtutu.ofdbox.core.xmlobj.base.page;

import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Content",namespace = Const.NAMESPACE_URI)
public class NPageContent {

    @Valid
    @Size(min = 1)
    @XmlElement(name = "Layer",namespace = Const.NAMESPACE_URI)
    private List<NLayer> layers;
}
