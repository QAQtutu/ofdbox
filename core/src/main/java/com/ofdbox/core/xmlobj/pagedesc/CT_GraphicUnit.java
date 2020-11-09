package com.ofdbox.core.xmlobj.pagedesc;

import com.ofdbox.core.xmlobj.adapter.DoubleArrayAdapter;
import com.ofdbox.core.xmlobj.adapter.StBoxAdapter;
import com.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import com.ofdbox.core.xmlobj.enums.LineJoinStyle;
import com.ofdbox.core.xmlobj.base.document.NActions;
import com.ofdbox.core.xmlobj.enums.LineCapStyle;
import com.ofdbox.core.xmlobj.st.ST_RefID;
import com.ofdbox.core.xmlobj.st.ST_Box;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CT_GraphicUnit {

    @NotNull
    @Valid
    @XmlJavaTypeAdapter(value = StBoxAdapter.class)
    @XmlAttribute(name = "Boundary")
    private ST_Box boundary;

    @XmlAttribute(name = "Name")
    private String name;

    @XmlAttribute(name = "Visible")
    private Boolean visible;

    @XmlJavaTypeAdapter(value = DoubleArrayAdapter.class)
    @XmlAttribute(name = "CTM")
    private Double[] ctm;

    @Valid
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @XmlAttribute(name = "DrawParam")
    private ST_RefID drawParam;

    @XmlAttribute(name = "LineWidth")
    private Double lineWidth;

    @XmlAttribute(name = "Cap")
    private LineCapStyle cap;

    @XmlAttribute(name = "Join")
    private LineJoinStyle join;

    @XmlAttribute(name = "MiterLimit")
    private Double miterLimit;

    @XmlAttribute(name = "DashOffset")
    private Double dashOffset;

    @XmlJavaTypeAdapter(value = DoubleArrayAdapter.class)
    @XmlAttribute(name = "DashPattern")
    private Double[] dashPattern;

    @XmlAttribute(name = "Alpha")
    private Integer alpha;

    @Valid
    @XmlElement(name = "ofd:Actions")
    private NActions actions;

    @Valid
    @XmlElement(name = "ofd:Clips")
    private NClips clips;
}
