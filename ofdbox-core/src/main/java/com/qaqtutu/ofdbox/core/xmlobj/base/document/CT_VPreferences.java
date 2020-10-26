package com.qaqtutu.ofdbox.core.xmlobj.base.document;

import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/*
 * 视图首选项
 * */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CT_VPreferences {

    /*
     * 窗口模式
     * */
    @XmlElement(name = "PageMode")
    private String pageMode;

    /*
     * 页面布局模式
     * */
    @XmlElement(name = "PageLayout", namespace = Const.NAMESPACE_URI)
    private String pageLayout;

    /*
     * 标题栏显示模式
     * */
    @XmlElement(name = "TabDisplay", namespace = Const.NAMESPACE_URI)
    private String tabDisplay;

    /*
     * 是否隐藏工具栏
     * */
    @XmlElement(name = "HideToolbar", namespace = Const.NAMESPACE_URI)
    private Boolean hideToolbar;

    /*
     * 是否隐藏菜单栏
     * */
    @XmlElement(name = "HideMenubar", namespace = Const.NAMESPACE_URI)
    private Boolean hideMenubar;

    /*
     * 是否隐藏主窗口之外的其他窗体组件
     * */
    @XmlElement(name = "HideWindowUI", namespace = Const.NAMESPACE_URI)
    private Boolean hideWindowUI;

    /*
     * 自动缩放模式
     * */
    @XmlElement(name = "ZoomMode", namespace = Const.NAMESPACE_URI)
    private String zoomMode;

    /*
     * 文档的缩放率
     * */
    @XmlElement(name = "Zoom", namespace = Const.NAMESPACE_URI)
    private Double zoom;

}
