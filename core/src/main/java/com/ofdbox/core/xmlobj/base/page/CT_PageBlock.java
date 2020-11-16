package com.ofdbox.core.xmlobj.base.page;

import com.ofdbox.core.xmlobj.base.page.object.*;
import com.ofdbox.core.xmlobj.graphic.CT_Path;
import com.ofdbox.core.xmlobj.object.composite.CT_Composite;
import com.ofdbox.core.xmlobj.object.image.CT_Image;
import com.ofdbox.core.xmlobj.object.text.CT_Text;
import com.ofdbox.core.xmlobj.pagedesc.CT_GraphicUnit;
import lombok.Data;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CT_PageBlock extends CT_GraphicUnit implements NObject {

    @XmlElements({
            @XmlElement(name = "ofd:PageBlock",type = CT_PageBlock.class),
            @XmlElement(name = "ofd:PathObject",type = CT_Path.class),
            @XmlElement(name = "ofd:ImageObject",type = CT_Image.class),
            @XmlElement(name = "ofd:TextObject",type = CT_Text.class),
            @XmlElement(name = "ofd:CompositeObject",type = CT_Composite.class)
    })
    @Valid
    private List<CT_GraphicUnit> content;
}
