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
            @XmlElement(name = "PageBlock",namespace = Const.NAMESPACE_URI,type = NPageBlock.class),
            @XmlElement(name = "PathObject",namespace = Const.NAMESPACE_URI,type = NPathObject.class),
            @XmlElement(name = "ImageObject",namespace = Const.NAMESPACE_URI,type = NImageObject.class),
            @XmlElement(name = "TextObject",namespace = Const.NAMESPACE_URI,type = NTextObject.class),
            @XmlElement(name = "CompositeObject",namespace = Const.NAMESPACE_URI,type = NCompositeObject.class)
    })
    @Valid
    private List<NObject> content;
}
