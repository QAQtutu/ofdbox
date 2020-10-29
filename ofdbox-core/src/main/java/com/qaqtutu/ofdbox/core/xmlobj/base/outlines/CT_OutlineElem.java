package com.qaqtutu.ofdbox.core.xmlobj.base.outlines;

import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.xmlobj.base.document.NActions;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CT_OutlineElem {

    @NotNull
    @XmlAttribute(name = "Title")
    private String title;

    @XmlAttribute(name = "Count")
    private Integer count;

    @XmlAttribute(name = "Expanded")
    private Boolean expanded;

    @Valid
    @XmlElement(name = "ofd:Actions")
    private NActions actions;

    @XmlElement(name="ofd:OutlineElem")
    private List<CT_OutlineElem> outlineElems;
}
