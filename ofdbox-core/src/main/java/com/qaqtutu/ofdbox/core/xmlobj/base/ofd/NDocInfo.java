package com.qaqtutu.ofdbox.core.xmlobj.base.ofd;

import com.qaqtutu.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.xmlobj.enums.DocUsage;
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
    @XmlElement(name = "DocID", namespace = Const.NAMESPACE_URI)
    private String docId;

    /*
     * 文档标题
     * */
    @XmlElement(name = "Title", namespace = Const.NAMESPACE_URI)
    private String title;

    /*
     * 文档作者
     * */
    @XmlElement(name = "Author", namespace = Const.NAMESPACE_URI)
    private String author;

    /*
     * 文档主题
     * */
    @XmlElement(name = "Subject", namespace = Const.NAMESPACE_URI)
    private String subject;

    /*
     * 文档摘要与注释
     * */
    @XmlElement(name = "Abstract", namespace = Const.NAMESPACE_URI)
    private String Abstract;

    /*
     * 文档创建日期
     * */
    @XmlElement(name = "CreationDate", namespace = Const.NAMESPACE_URI)
    private String creationDate;

    /*
     * 文档最近修改日期
     * */
    @XmlElement(name = "ModDate", namespace = Const.NAMESPACE_URI)
    private String modDate;

    /*
     * 文档分类
     * */
    @XmlElement(name = "DocUsage", namespace = Const.NAMESPACE_URI)
    private DocUsage docUsage;

    /*
     * 文档封面
     * */
    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "Cover", namespace = Const.NAMESPACE_URI)
    private ST_Loc cover;

    /*
     * 关键词
     * */
    @Valid
    @XmlElement(name = "Keywords", namespace = Const.NAMESPACE_URI)
    private NKeywords keywords;

    /*
     * 创建文档的应用程序
     * */
    @XmlElement(name = "Creator", namespace = Const.NAMESPACE_URI)
    private String creator;

    /*
     * 创建文档的应用程序的版本信息
     * */
    @XmlElement(name = "CreatorVersion", namespace = Const.NAMESPACE_URI)
    private String creatorVersion;

    /*
     * 用户自定义元数据
     * */
    @Valid
    @XmlElement(name = "CustomDatas", namespace = Const.NAMESPACE_URI)
    private NCustomDatas customDatas;

}
