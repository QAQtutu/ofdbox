package com.qaqtutu.ofdbox.core.xmlobj.base.page.object;


import com.qaqtutu.ofdbox.core.xmlobj.object.image.CT_Image;
import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ImageObject",namespace = Const.NAMESPACE_URI)
public class NImageObject extends CT_Image implements NObject {

}
