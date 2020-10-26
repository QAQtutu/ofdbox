package com.qaqtutu.ofdbox.core.xmlobj.version;

import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_ID;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
import com.qaqtutu.ofdbox.core.xmlobj.adapter.StIdAdapter;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class XDocVersion {

    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StIdAdapter.class)
    @XmlAttribute(name = "ID")
    private ST_ID id;

    @XmlAttribute(name = "Version")
    private String version;

    @XmlAttribute(name = "Name")
    private String name;

    @XmlAttribute(name = "CreationDate")
    private String creationDate;

    @NotNull
    @Valid
    @XmlElement(name = "FileList",namespace = Const.NAMESPACE_URI)
    private NFileList fileList;

    @XmlElement(name = "DocRoot",namespace = Const.NAMESPACE_URI)
    private ST_Loc docRoot;
}
