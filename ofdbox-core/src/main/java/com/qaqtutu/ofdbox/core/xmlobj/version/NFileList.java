package com.qaqtutu.ofdbox.core.xmlobj.version;

import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NFileList {

    @Size(min = 1)
    @Valid
    @XmlElement(name = "ofd:File")
    private List<NFile> files;
}
