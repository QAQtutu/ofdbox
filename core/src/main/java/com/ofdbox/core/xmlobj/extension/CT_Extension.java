package com.ofdbox.core.xmlobj.extension;

import com.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import com.ofdbox.core.xmlobj.st.ST_RefID;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CT_Extension {

    /*
    * 生成扩展的程序名称
    * */
    @NotNull
    @XmlAttribute(name = "AppName")
    private String appName;

    /*
    * 生成扩展的软件厂商标识
    * */
    @XmlAttribute(name = "Company")
    private String company;

    /*
    * 生成扩展的软件版本
    * */
    @XmlAttribute(name = "AppVersion")
    private String appVersion;

    /*
    * 生成扩展的时间
    * */
    @XmlAttribute(name = "Date")
    private String date;

    /*
    * 引用此扩展项针对的文档项目的标识
    * */
    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @XmlAttribute(name = "RefId")
    private ST_RefID refId;

    /*
    *
    * */
    @Valid
    @Size(min = 1)
    @XmlElements({
            @XmlElement(name = "ofd:Property",type =Property.class ),
            @XmlElement(name = "ofd:Data",type =Data.class ),
            @XmlElement(name = "ofd:ExtendData",type =ExtendData.class )
    })
    private List<ExtensionObj> content;

    public static interface ExtensionObj{}

    @lombok.Data
    @AllArgsConstructor
    @XmlAccessorType(value = XmlAccessType.FIELD)
    public static class Property implements ExtensionObj{
        @NotNull
        @XmlAttribute(name = "Name")
        private String name;

        @XmlAttribute(name = "Type")
        private String type;

        @NotNull
        @XmlValue
        private String value;
    }

    @lombok.Data
    @XmlAccessorType(value = XmlAccessType.FIELD)
    public static class Data implements ExtensionObj{
        @XmlValue
        private String content;
    }

    @lombok.Data
    @XmlAccessorType(value = XmlAccessType.FIELD)
    public static class ExtendData implements ExtensionObj{
        @NotNull
        @Valid
        @XmlValue
        @XmlJavaTypeAdapter(value = StLocAdapter.class)
        private ST_Loc loc;
    }
}
