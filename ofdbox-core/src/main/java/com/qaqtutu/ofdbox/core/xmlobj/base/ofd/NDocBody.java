package com.qaqtutu.ofdbox.core.xmlobj.base.ofd;

import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.xmlobj.adapter.StLocAdapter;
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
    @XmlElement(name = "DocInfo",namespace = Const.NAMESPACE_URI)
    private NDocInfo docInfo;

    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "DocRoot",namespace = Const.NAMESPACE_URI)
    private ST_Loc docRoot;
}
