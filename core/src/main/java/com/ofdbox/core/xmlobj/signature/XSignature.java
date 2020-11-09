package com.ofdbox.core.xmlobj.signature;

import com.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "ofd:Signature")
public class XSignature {

    /*
    * 签名信息
    * */
    @NotNull
    @Valid
    @XmlElement(name = "ofd:SignedInfo")
    private NSignedInfo signedInfo;

    /*
    * 签名值文件
    * */
    @Valid
    @NotNull
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "ofd:SignedValue")
    private ST_Loc signedValue;
}
