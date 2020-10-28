package com.qaqtutu.ofdbox.core.xmlobj.graphic;

import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.utils.OfdXmlUtils;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Pos;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement
public class CT_Region {

    @NotNull
    @Valid
    @XmlElement(name = "ofd:Area",type = NRegionArea.class)
    private List<NRegionArea> areas;

    public static void main(String[] args) {
        CT_Region region=new CT_Region();
        region.setAreas(new ArrayList<>());

        NRegionArea nRegionArea=new NRegionArea();
        nRegionArea.setStart(new ST_Pos());
        nRegionArea.setOperators(new ArrayList<>());
        nRegionArea.getStart().setX(300.0);
        nRegionArea.getStart().setY(500.0);
        nRegionArea.getOperators().add(new Move());
        nRegionArea.getOperators().add(new Close());

        region.getAreas().add(nRegionArea);
        region.getAreas().add(nRegionArea);

        System.out.println(OfdXmlUtils.toXml(region));
    }
}
