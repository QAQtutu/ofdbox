package com.qaqtutu.ofdbox.core.xmlobj.base.ofd;

import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ofd:OFD")
public class XOFD {

    /*
    * 文件格式子集类型
    * */
    @NotBlank
    @XmlAttribute(name = "DocType")
    private String docType;

    /*
    * 文件格式版本号 取值为“1.0”
    * */
    @NotBlank
    @XmlAttribute(name = "Version")
    private String version;

    /*
    * 文件对象入口
    * */
    @Size(min = 1)
    @Valid
    @XmlElement(name = "ofd:DocBody")
    private List<NDocBody> docBodys;

    /*
    * 指向该文档中签名和签章结构
    * */
    @Valid
    @XmlElement(name = "ofd:Signatures")
    private ST_Loc signatures;
}
