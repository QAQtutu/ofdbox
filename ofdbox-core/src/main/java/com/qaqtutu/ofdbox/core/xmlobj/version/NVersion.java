package com.qaqtutu.ofdbox.core.xmlobj.version;

import com.qaqtutu.ofdbox.core.xmlobj.st.ST_ID;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
import com.qaqtutu.ofdbox.core.xmlobj.adapter.StIdAdapter;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NVersion {

    /*
    * 版本标识
    * */
    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StIdAdapter.class)
    @XmlAttribute(name = "ID")
    private ST_ID id;

    /*
    * 版本号
    * */
    @NotNull
    @XmlAttribute(name = "Index")
    private Integer index;

    /*
    * 是否是默认版本
    * */
    @XmlAttribute(name = "Current")
    private Boolean current;

    /*
    * 指向包内的版本描述文件
    * */
    @NotNull
    @Valid
    @XmlAttribute(name = "BaseLoc")
    private ST_Loc baseLoc;
}
