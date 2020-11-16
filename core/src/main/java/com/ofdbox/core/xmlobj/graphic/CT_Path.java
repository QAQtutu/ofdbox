package com.ofdbox.core.xmlobj.graphic;

import com.ofdbox.core.xmlobj.adapter.BaseAdapter;
import com.ofdbox.core.xmlobj.base.page.object.NObject;
import com.ofdbox.core.xmlobj.object.PathOrText;
import com.ofdbox.core.xmlobj.pagedesc.color.CT_Color;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CT_Path extends PathOrText implements NObject {

    @XmlAttribute(name = "Stroke")
    private Boolean stroke;

    @XmlAttribute(name = "Fill")
    private Boolean fill;

    @XmlJavaTypeAdapter(value = RuleAdapter.class)
    @XmlAttribute(name = "Rule")
    private Rule rule;

    @XmlElement(name = "ofd:FillColor")
    private CT_Color fillColor;

    @XmlElement(name = "ofd:StrokeColor")
    private CT_Color strokeColor;

    @NotNull
    @XmlElement(name = "ofd:AbbreviatedData")
    private String abbreviatedData;

    public static enum Rule{
        NoneZero,
        Even_Odd
    }

    public static class RuleAdapter  extends BaseAdapter<Rule> {

        @Override
        public Rule unmarshal1(String v) throws Exception {
            switch (v){
                case "NoneZero":
                    return Rule.NoneZero;
                case "Even-Odd":
                    return Rule.Even_Odd;
            }
            return null;
        }

        @Override
        public String marshal1(Rule v) throws Exception {
            switch (v){
                case Even_Odd:
                    return "Even-Odd";
                case NoneZero:
                    return "NoneZero";
            }
            return null;
        }
    }
}
