package com.ofdbox.core.xmlobj.base.document;

import com.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class NCommonData {

    @Valid
    @NotNull
    @XmlElement(name = "ofd:PageArea")
    private CT_PageArea pageArea;

    @NotBlank
    @XmlElement(name = "ofd:MaxUnitID")
    private String maxUnitId;

    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "ofd:PublicRes")
    private  List<ST_Loc> publicRes;

    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "ofd:DocumentRes")
    private List<ST_Loc> documentRes;

    @Valid
    @XmlElement(name = "ofd:TemplatePage")
    private List<CT_TemplatePage> templatePages;

    @XmlAttribute(name = "DefaultCS")
    private Integer defaultCS;

}
