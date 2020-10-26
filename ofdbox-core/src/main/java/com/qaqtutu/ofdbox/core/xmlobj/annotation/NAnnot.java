package com.qaqtutu.ofdbox.core.xmlobj.annotation;

import com.qaqtutu.ofdbox.core.xmlobj.base.page.CT_PageBlock;
import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_ID;
import com.qaqtutu.ofdbox.core.xmlobj.adapter.StIdAdapter;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NAnnot {

    /*
    * 注释的标识
    * */
    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StIdAdapter.class)
    @XmlAttribute(name = "ID")
    private ST_ID id;

    /*
    * 注释的类型
    * */
    @NotNull
    @XmlAttribute(name = "Type")
    private AnnotationType type;

    /*
    * 注释的创建者
    * */
    @NotBlank
    @XmlAttribute(name = "Creator")
    private String creator;

    /*
    * 最近一次修改的时间
    * */
    @NotBlank
    @XmlAttribute(name = "LastModDate")
    private String lastModDate;

    /*
    * 注释对象是否显示
    * */
    @XmlAttribute(name = "Visible")
    private Boolean visible;

    /*
    * 注释的子类型
    * */
    @XmlAttribute(name = "Subtype")
    private String subtype;

    /*
    * 对象的Remark信息是否随页面一起打印
    * */
    @XmlAttribute(name = "Print")
    private Boolean print;

    /*
    * 对象的Remark信息是否不随页面缩放
    * */
    @XmlAttribute(name = "NoZoom")
    private Boolean noZoom;

    /*
    * 对象的Remark信息是否不随页面旋转
    * */
    @XmlAttribute(name = "NoRotate")
    private Boolean noRotate;

    /*
    * 对象的Remark信息是否不能被用户更改
    * */
    @XmlAttribute(name = "ReadOnly")
    private Boolean readOnly;

    /*
    * 注释说明内容
    * */
    @XmlElement(name = "Remark",namespace = Const.NAMESPACE_URI)
    private String remark;

    /*
    * 一组注释参数
    * */
    @XmlElement( name= "Parameters",namespace = Const.NAMESPACE_URI)
    private NParameters parameters;

    /*
    * 注释的静态呈现效果
    * */
    @XmlElement(name = "Appearance",namespace = Const.NAMESPACE_URI)
    private CT_PageBlock appearance;
}
