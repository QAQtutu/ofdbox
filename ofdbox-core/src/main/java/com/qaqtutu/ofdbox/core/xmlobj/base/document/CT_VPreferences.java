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
    @XmlElement(name = "ofd:PageMode")
    private String pageMode;

    /*
     * 页面布局模式
     * */
    @XmlElement(name = "ofd:PageLayout")
    private String pageLayout;

    /*
     * 标题栏显示模式
     * */
    @XmlElement(name = "ofd:TabDisplay")
    private String tabDisplay;

    /*
     * 是否隐藏工具栏
     * */
    @XmlElement(name = "ofd:HideToolbar")
    private Boolean hideToolbar;

    /*
     * 是否隐藏菜单栏
     * */
    @XmlElement(name = "ofd:HideMenubar")
    private Boolean hideMenubar;

    /*
     * 是否隐藏主窗口之外的其他窗体组件
     * */
    @XmlElement(name = "ofd:HideWindowUI")
    private Boolean hideWindowUI;

    /*
     * 自动缩放模式
     * */
    @XmlElement(name = "ofd:ZoomMode")
    private String zoomMode;

    /*
     * 文档的缩放率
     * */
    @XmlElement(name = "ofd:Zoom")
    private Double zoom;

}
