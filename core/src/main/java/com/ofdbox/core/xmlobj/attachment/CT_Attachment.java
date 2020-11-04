package com.ofdbox.core.xmlobj.attachment;

import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.Date;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CT_Attachment {

    /*
    * 附件标识
    * */
    @NotBlank
    @XmlAttribute(name = "ID")
    private String id;

    /*
    * 附件名称
    * */
    @NotBlank
    @XmlAttribute(name = "Name")
    private String name;

    /*
    * 附件格式
    * */
    @XmlAttribute(name = "Format")
    private String format;

    /*
    * 创建时间
    * */
    @XmlAttribute(name = "CreationDate")
    private Date creationDate;

    /*
    * 修改时间
    * */
    @XmlAttribute(name = "ModDate")
    private Date modDate;

    /*
    * 附件大小
    * */
    @XmlAttribute(name = "Size")
    private Double size;

    /*
    * 附件是否可见
    * */
    @XmlAttribute(name = "Visible")
    private Boolean visible;

    /*
    * 附件用途
    * */
    @XmlAttribute(name = "Usage")
    private String usage;

    /*
    * 附件内容在包内的路径
    * */
    @XmlAttribute(name = "FileLoc")
    private ST_Loc fileLoc;
}
