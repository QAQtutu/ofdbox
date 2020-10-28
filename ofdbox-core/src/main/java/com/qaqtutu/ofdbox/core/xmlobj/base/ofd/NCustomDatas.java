package com.qaqtutu.ofdbox.core.xmlobj.base.ofd;


import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class NCustomDatas {

    @NotNull
    @XmlElement(name = "ofd:CustomData")
    private List<NCustomData> customDatas;
}
