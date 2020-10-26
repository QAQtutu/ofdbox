package com.qaqtutu.ofdbox.core.xmlobj.action;

import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.xmlobj.enums.Event;
import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "CT_Action")
@XmlAccessorType(XmlAccessType.FIELD)
public class CT_Action {

    @XmlAttribute(name = "Event")
    private Event event;


    @XmlElements({
            @XmlElement(name = "Goto", namespace = Const.NAMESPACE_URI, type = Goto.class),
            @XmlElement(name = "Movie", namespace = Const.NAMESPACE_URI, type = Movie.class),
            @XmlElement(name = "Sound", namespace = Const.NAMESPACE_URI, type = Sound.class),
            @XmlElement(name = "GotoA", namespace = Const.NAMESPACE_URI, type = GotoA.class)
    })
    private ActionType action;
}
