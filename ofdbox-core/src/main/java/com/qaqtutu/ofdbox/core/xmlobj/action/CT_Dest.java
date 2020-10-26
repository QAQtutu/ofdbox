package com.qaqtutu.ofdbox.core.xmlobj.action;

import com.qaqtutu.ofdbox.core.xmlobj.enums.DestType;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/*
 * 目标区域结构 p75
 * */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CT_Dest {

    /*
     * 声明目标区域的描述方法
     * */
    @NotBlank
    @XmlAttribute(name = "Type")
    private DestType type;

    /*
     * 引用跳转目标页面的标识
     * */
    @NotBlank
    @XmlAttribute(name = "PageID")
    private String pageId;

    /*
     * 目标区域左上角x坐标
     * */
    @NotNull
    @XmlAttribute(name = "Left")
    private Double left;

    /*
     * 目标区域右下角x坐标
     * */
    @NotNull
    @XmlAttribute(name = "Right")
    private Double right;

    /*
     * 目标区域左上角y坐标
     * */
    @NotNull
    @XmlAttribute(name = "Top")
    private Double top;

    /*
     * 目标区域右下角y坐标
     * */
    @NotNull
    @XmlAttribute(name = "Bottom")
    private Double bottom;

    /*
     * 目标区域页面缩放比例
     * */
    @NotNull
    @XmlAttribute(name = "Zoom")
    private Double zoom;
}
