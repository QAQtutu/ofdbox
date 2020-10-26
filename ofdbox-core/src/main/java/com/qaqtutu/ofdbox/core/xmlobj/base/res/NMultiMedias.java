package com.qaqtutu.ofdbox.core.xmlobj.base.res;

import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NMultiMedias {

    @XmlElement(name = "MultiMedia",namespace = Const.NAMESPACE_URI)
    private List<NMultiMedia> list;
}
