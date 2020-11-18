package com.ofdbox.core.xmlobj.pagedesc.color;

import com.ofdbox.core.xmlobj.adapter.StIdAdapter;
import com.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.ofdbox.core.xmlobj.adapter.StringArrayAdapter;
import com.ofdbox.core.xmlobj.enums.ColorSpaceType;
import com.ofdbox.core.xmlobj.st.ST_ID;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CT_ColorSpace {


    @NotNull
    @XmlJavaTypeAdapter(value = StIdAdapter.class)
    @XmlAttribute(name = "ID")
    private ST_ID id;

    @NotNull
    @XmlAttribute(name = "Type")
    private ColorSpaceType type;

    @XmlAttribute(name = "BitsPerComponent")
    private Integer bitsPerComponent;

    @Valid
    @XmlAttribute(name = "Profile")
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    private ST_Loc profile;

    @Valid
    @XmlElement(name = "ofd:Palette")
    private Palette palette;

    @Data
    @XmlAccessorType(value = XmlAccessType.FIELD)
    public static class Palette {

        @Size(min = 1)
        @XmlJavaTypeAdapter(value = StringArrayAdapter.class)
        @XmlAttribute(name = "CV")
        private List<String[]> cvs;
    }
}
