package com.ofdbox.core.xmlobj.pagedesc.color;

import com.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import com.ofdbox.core.xmlobj.adapter.StringArrayAdapter;
import com.ofdbox.core.xmlobj.st.ST_RefID;
import lombok.Data;

import javax.validation.Valid;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement
public class CT_Color {

    @XmlJavaTypeAdapter(value = StringArrayAdapter.class)
    @XmlAttribute(name = "Value")
    private String[] value;

    @XmlAttribute(name = "Index")
    private Integer index;

    @Valid
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @XmlAttribute(name = "ColorSpace")
    private ST_RefID colorSpace;

    @XmlAttribute(name = "Alpha")
    private Integer alpha;

    /*
    * 填充类型
    * */
    @XmlElements({
            @XmlElement(name = "ofd:Pattern",type = CT_Pattern.class),
            @XmlElement(name = "ofd:AxialShd",type = CT_AxialShd.class),
            @XmlElement(name = "ofd:RadialShd",type = CT_RadialShd.class),
            @XmlElement(name = "ofd:GouraudShd",type = CT_GouraudShd.class),
            @XmlElement(name = "ofd:LaGouraudShd",type = CT_LaGouraudShd.class)
    })
    private ComplexColor complexColor;
}
