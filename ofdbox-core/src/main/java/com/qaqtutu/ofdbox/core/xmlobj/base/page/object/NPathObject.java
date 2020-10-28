package com.qaqtutu.ofdbox.core.xmlobj.base.page.object;


import com.qaqtutu.ofdbox.core.xmlobj.graphic.CT_Path;
import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ofd:PathObject")
public class NPathObject extends CT_Path implements NObject {
}
