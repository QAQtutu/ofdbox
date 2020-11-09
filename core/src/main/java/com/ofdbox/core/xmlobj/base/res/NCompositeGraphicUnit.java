package com.ofdbox.core.xmlobj.base.res;

import com.ofdbox.core.xmlobj.adapter.StIdAdapter;
import com.ofdbox.core.xmlobj.object.composite.CT_VectorG;
import com.ofdbox.core.xmlobj.st.ST_ID;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
public class NCompositeGraphicUnit extends CT_VectorG {

    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StIdAdapter.class)
    @XmlAttribute(name = "ID")
    private ST_ID id;

}
