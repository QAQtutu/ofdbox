package com.qaqtutu.ofdbox.core.xmlobj.base.document;

import com.qaqtutu.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.xmlobj.base.outlines.NOutlines;
import com.qaqtutu.ofdbox.core.xmlobj.base.pages.NPages;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document", namespace = Const.NAMESPACE_URI)
public class XDocument {

    /*
     * 文档公共数据
     * */
    @NotNull
    @Valid
    @XmlElement(name = "CommonData", namespace = Const.NAMESPACE_URI)
    private NCommonData commonData;

    /*
     * 页树
     * */
    @NotNull
    @Valid
    @XmlElement(name = "Pages", namespace = Const.NAMESPACE_URI)
    private NPages pages;

    /*
    * 大纲
    * */
    @Valid
    @XmlElement(name = "Outlines", namespace = Const.NAMESPACE_URI)
    private NOutlines outlines;

    /*
    * 文档的权限声明
    * */
    @Valid
    @XmlElement(name = "Permissions", namespace = Const.NAMESPACE_URI)
    private CT_Permission permissions;

    /*
    * 文档的关联动作
    * */
    @Valid
    @XmlElement(name = "Actions", namespace = Const.NAMESPACE_URI)
    private NActions actions;

    @Valid
    @XmlElement(name = "VPreferences")
    private CT_VPreferences vPreferences;

    /*
    * 文档的书签集
    * */
    @Valid
    @XmlElement(name = "Bookmarks", namespace = Const.NAMESPACE_URI)
    private NBookmarks bookmarks;


    /*
     * 指向注释列表文件
     * */
    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "Annotations", namespace = Const.NAMESPACE_URI)
    private ST_Loc annotations;

    /*
     * 指向自定义标引列表文件
     * */
    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "CustomTags", namespace = Const.NAMESPACE_URI)
    private ST_Loc customTags;

    /*
     * 指向附件列表文件
     * */
    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "Attachments", namespace = Const.NAMESPACE_URI)
    private ST_Loc attachments;

    /*
    * 指向扩展列表文件
    * */
    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "Extensions", namespace = Const.NAMESPACE_URI)
    private ST_Loc extensions;
}
