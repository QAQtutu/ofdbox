package com.ofdbox.core.xmlobj.base.document;

import com.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.ofdbox.core.xmlobj.base.outlines.NOutlines;
import com.ofdbox.core.xmlobj.base.pages.NPages;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ofd:Document")
public class XDocument {

    /*
     * 文档公共数据
     * */
    @NotNull
    @Valid
    @XmlElement(name = "ofd:CommonData")
    private NCommonData commonData;

    /*
     * 页树
     * */
    @NotNull
    @Valid
    @XmlElement(name = "ofd:Pages")
    private NPages pages;

    /*
    * 大纲
    * */
    @Valid
    @XmlElement(name = "ofd:Outlines")
    private NOutlines outlines;

    /*
    * 文档的权限声明
    * */
    @Valid
    @XmlElement(name = "ofd:Permissions")
    private CT_Permission permissions;

    /*
    * 文档的关联动作
    * */
    @Valid
    @XmlElement(name = "ofd:Actions")
    private NActions actions;

    @Valid
    @XmlElement(name = "ofd:VPreferences")
    private CT_VPreferences vPreferences;

    /*
    * 文档的书签集
    * */
    @Valid
    @XmlElement(name = "ofd:Bookmarks")
    private NBookmarks bookmarks;


    /*
     * 指向注释列表文件
     * */
    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "ofd:Annotations")
    private ST_Loc annotations;

    /*
     * 指向自定义标引列表文件
     * */
    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "ofd:CustomTags")
    private ST_Loc customTags;

    /*
     * 指向附件列表文件
     * */
    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "ofd:Attachments")
    private ST_Loc attachments;

    /*
    * 指向扩展列表文件
    * */
    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "ofd:Extensions")
    private ST_Loc extensions;
}
