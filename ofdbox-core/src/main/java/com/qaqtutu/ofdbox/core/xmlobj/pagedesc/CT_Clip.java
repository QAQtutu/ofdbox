package com.qaqtutu.ofdbox.core.xmlobj.pagedesc;

import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.xmlobj.object.PathOrText;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_RefID;
import com.qaqtutu.ofdbox.core.xmlobj.adapter.DoubleArrayAdapter;
import com.qaqtutu.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CT_Clip {

    @Valid
    @Size(min = 1)
    @XmlElement(name = "ofd:Area")
    private List<NArea> areas;

    @Data
    @XmlAccessorType(value = XmlAccessType.FIELD)
    public static class NArea{

        @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
        @XmlAttribute(name = "DrawParam")
        private ST_RefID drawParam;

        @XmlJavaTypeAdapter(value = DoubleArrayAdapter.class)
        @XmlAttribute(name = "CTM")
        private Double[] ctm;

        @NotNull
        @XmlElements({
                @XmlElement(name = "ofd:Path"),
                @XmlElement(name = "ofd:Text")
        })
        private PathOrText content;
    }
}
