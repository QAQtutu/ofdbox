package com.qaqtutu.ofdbox.core.xmlobj.base.document;

import com.qaqtutu.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
import com.qaqtutu.ofdbox.core.contance.Const;
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
    @XmlElement(name = "PageArea", namespace = Const.NAMESPACE_URI)
    private CT_PageArea pageArea;

    @NotBlank
    @XmlElement(name = "MaxUnitID", namespace = Const.NAMESPACE_URI)
    private String maxUnitId;

    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "PublicRes", namespace = Const.NAMESPACE_URI)
    private  List<ST_Loc> publicRes;

    @Valid
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "DocumentRes", namespace = Const.NAMESPACE_URI)
    private List<ST_Loc> documentRes;

    @Valid
    @XmlElement(name = "TemplatePage", namespace = Const.NAMESPACE_URI)
    private List<CT_TemplatePage> templatePages;

    @XmlAttribute(name = "DefaultCS", namespace = Const.NAMESPACE_URI)
    private Integer defaultCS;

}
