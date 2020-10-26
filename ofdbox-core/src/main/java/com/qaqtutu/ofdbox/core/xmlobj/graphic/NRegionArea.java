package com.qaqtutu.ofdbox.core.xmlobj.graphic;

import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Pos;
import com.qaqtutu.ofdbox.core.xmlobj.adapter.StPosAdapter;
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
            @XmlElement(name = "Move",namespace = Const.NAMESPACE_URI,type = Move.class),
            @XmlElement(name = "Line",namespace = Const.NAMESPACE_URI,type = Line.class),
            @XmlElement(name = "QuadraticBezier",namespace = Const.NAMESPACE_URI,type = QuadraticBezier.class),
            @XmlElement(name = "CubicBezier",namespace = Const.NAMESPACE_URI,type = CubicBezier.class),
            @XmlElement(name = "Arc",namespace = Const.NAMESPACE_URI,type = Arc.class),
            @XmlElement(name = "Close",namespace = Const.NAMESPACE_URI,type = Close.class)
    })
    private List<PathOperator> operators;
}
