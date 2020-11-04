package com.ofdbox.core.xmlobj.action;

import com.ofdbox.core.xmlobj.enums.MovieOperator;
import com.ofdbox.core.xmlobj.st.ST_RefID;
import com.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Movie extends ActionType{

    /*
     * 引用资源文件中定义的视频资源标识
     * */
    @NotNull
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @XmlAttribute(name = "ResourceID")
    private ST_RefID resourceId;

    @XmlAttribute(name = "Operator")
    private MovieOperator operator;
}
