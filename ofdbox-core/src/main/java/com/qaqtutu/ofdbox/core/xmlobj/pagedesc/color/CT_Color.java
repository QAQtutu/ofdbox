package com.qaqtutu.ofdbox.core.xmlobj.pagedesc.color;

import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_RefID;
import com.qaqtutu.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import com.qaqtutu.ofdbox.core.xmlobj.adapter.StringArrayAdapter;
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
            @XmlElement(name = "Pattern",namespace = Const.NAMESPACE_URI,type = CT_Pattern.class),
            @XmlElement(name = "AxialShd",namespace = Const.NAMESPACE_URI,type = CT_AxialShd.class),
            @XmlElement(name = "RadialShd",namespace = Const.NAMESPACE_URI,type = CT_RadialShd.class),
            @XmlElement(name = "GouraudShd",namespace = Const.NAMESPACE_URI,type = CT_GouraudShd.class),
            @XmlElement(name = "LaGouraudShd",namespace = Const.NAMESPACE_URI,type = CT_LaGouraudShd.class)
    })
    private ComplexColor complexColor;
}
