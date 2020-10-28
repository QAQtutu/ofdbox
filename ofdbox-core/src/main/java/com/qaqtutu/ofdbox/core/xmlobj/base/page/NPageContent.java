package com.qaqtutu.ofdbox.core.xmlobj.base.page;

import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ofd:Content")
public class NPageContent {

    @Valid
    @Size(min = 1)
    @XmlElement(name = "ofd:Layer")
    private List<NLayer> layers;
}
