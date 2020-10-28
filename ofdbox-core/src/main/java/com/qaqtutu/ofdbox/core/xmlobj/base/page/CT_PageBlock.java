package com.qaqtutu.ofdbox.core.xmlobj.base.page;

import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.xmlobj.base.page.object.*;
import lombok.Data;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CT_PageBlock {

    @XmlElements({
            @XmlElement(name = "ofd:PageBlock",type = NPageBlock.class),
            @XmlElement(name = "ofd:PathObject",type = NPathObject.class),
            @XmlElement(name = "ofd:ImageObject",type = NImageObject.class),
            @XmlElement(name = "ofd:TextObject",type = NTextObject.class),
            @XmlElement(name = "ofd:CompositeObject",type = NCompositeObject.class)
    })
    @Valid
    private List<NObject> content;
}
