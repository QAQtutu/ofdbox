package com.ofdbox.core.xmlobj.base.page.object;


import com.ofdbox.core.xmlobj.object.image.CT_Image;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ofd:ImageObject")
public class NImageObject extends CT_Image implements NObject {

}
