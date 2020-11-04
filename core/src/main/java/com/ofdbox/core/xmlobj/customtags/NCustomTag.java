package com.ofdbox.core.xmlobj.customtags;

import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NCustomTag {

    /*
    * 自定义标引内容节点适用的类型标识
    * */
    @XmlAttribute(name = "TypeID")
    private String typeId;

    /*
    * 指向自定义标引内容节点适用的Schema文件
    * */
    @XmlElement(name = "ofd:SchemaLoc")
    private ST_Loc schemaLoc;

    /*
    * 指向自定义标引文件
    * */
    @NotNull
    @Valid
    @XmlElement(name = "ofd:FileLoc")
    private ST_Loc fileLoc;
}
