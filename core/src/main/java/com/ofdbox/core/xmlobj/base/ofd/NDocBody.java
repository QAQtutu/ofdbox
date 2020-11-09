package com.ofdbox.core.xmlobj.base.ofd;

import com.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class NDocBody {

    @NotNull
    @Valid
    @XmlElement(name = "ofd:DocInfo")
    private NDocInfo docInfo;

    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "ofd:DocRoot")
    private ST_Loc docRoot;

    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "ofd:Signatures")
    private ST_Loc signatures;
}
