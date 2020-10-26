package com.qaqtutu.ofdbox.core.xmlobj.base.document;

import com.qaqtutu.ofdbox.core.xmlobj.action.CT_Dest;
import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CT_Bookmark {

    /*
    * 书签名称
    * */
    @NotBlank
    @XmlAttribute(name = "Name")
    private String name;

    /*
    * 书签对应的文档位置
    * */
    @Valid
    @NotNull
    @XmlElement(name = "Dest", namespace = Const.NAMESPACE_URI)
    private CT_Dest dest;
}
