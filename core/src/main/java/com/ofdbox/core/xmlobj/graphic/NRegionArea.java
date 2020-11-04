package com.ofdbox.core.xmlobj.graphic;

import com.ofdbox.core.xmlobj.adapter.StPosAdapter;
import com.ofdbox.core.xmlobj.st.ST_Pos;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NRegionArea {

    /*
    * 子图形起始点坐标
    * */
    @XmlJavaTypeAdapter(value = StPosAdapter.class)
    @NotNull
    @XmlAttribute(name = "Start")
    private ST_Pos start;

    @Size(min = 1)
    @XmlElements({
            @XmlElement(name = "ofd:Move",type = Move.class),
            @XmlElement(name = "ofd:Line",type = Line.class),
            @XmlElement(name = "ofd:QuadraticBezier",type = QuadraticBezier.class),
            @XmlElement(name = "ofd:CubicBezier",type = CubicBezier.class),
            @XmlElement(name = "ofd:Arc",type = Arc.class),
            @XmlElement(name = "ofd:Close",type = Close.class)
    })
    private List<PathOperator> operators;
}
