package com.ofdbox.core.xmlobj.action;

import com.ofdbox.core.xmlobj.enums.Event;
import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "ofd:CT_Action")
@XmlAccessorType(XmlAccessType.FIELD)
public class CT_Action {

    @XmlAttribute(name = "Event")
    private Event event;


    @XmlElements({
            @XmlElement(name = "ofd:Goto", type = Goto.class),
            @XmlElement(name = "ofd:Movie", type = Movie.class),
            @XmlElement(name = "ofd:Sound", type = Sound.class),
            @XmlElement(name = "ofd:GotoA", type = GotoA.class)
    })
    private ActionType action;
}
