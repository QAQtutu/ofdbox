package com.ofdbox.core.xmlobj.base.ofd;

import com.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.ofdbox.core.xmlobj.enums.DocUsage;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class NDocInfo {

    /*
     * 文件表示 UUID生产32位字符
     * */
    @XmlElement(name = "ofd:DocID")
    private String docId;

    /*
     * 文档标题
     * */
    @XmlElement(name = "ofd:Title")
    private String title;

    /*
     * 文档作者
     * */
    @XmlElement(name = "ofd:Author")
    private String author;

    /*
     * 文档主题
     * */
    @XmlElement(name = "ofd:Subject")
    private String subject;

    /*
     * 文档摘要与注释
     * */
    @XmlElement(name = "ofd:Abstract")
    private String Abstract;

    /*
     * 文档创建日期
     * */
    @XmlElement(name = "ofd:CreationDate")
    private String creationDate;

    /*
     * 文档最近修改日期
     * */
    @XmlElement(name = "ofd:ModDate")
    private String modDate;

    /*
     * 文档分类
     * */
    @XmlElement(name = "ofd:DocUsage")
    private DocUsage docUsage;

    /*
     * 文档封面
     * */
    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "ofd:Cover")
    private ST_Loc cover;

    /*
     * 关键词
     * */
    @Valid
    @XmlElement(name = "ofd:Keywords")
    private NKeywords keywords;

    /*
     * 创建文档的应用程序
     * */
    @XmlElement(name = "ofd:Creator")
    private String creator;

    /*
     * 创建文档的应用程序的版本信息
     * */
    @XmlElement(name = "ofd:CreatorVersion")
    private String creatorVersion;

    /*
     * 用户自定义元数据
     * */
    @Valid
    @XmlElement(name = "ofd:CustomDatas")
    private NCustomDatas customDatas;

}
