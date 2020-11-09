package com.ofdbox.core.xmlobj.annotation;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.xml.bind.annotation.*;
import java.util.List;


@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NParameters {

    @Valid
    @XmlElement(name = "ofd:Parameter")
    private List<NParameter> list;

    @Data
    @XmlAccessorType(value = XmlAccessType.FIELD)
    public static class NParameter{

        /*
         * 参数名
         * */
        @NotBlank
        @XmlAttribute(name = "Name")
        private String name;

        /*
         * 参数值
         * */
        @XmlValue
        @NotBlank
        private String value;
    }
}
