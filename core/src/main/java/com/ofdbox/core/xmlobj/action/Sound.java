package com.ofdbox.core.xmlobj.action;

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
public class Sound extends ActionType{

    /*
    * 引用资源文件中定义的音频资源标识
    * */
    @NotNull
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @XmlAttribute(name = "ResourceID")
    private ST_RefID resourceId;

    /*
    * 音量
    * */
    @XmlAttribute(name = "Volume")
    private Integer volume;

    /*
    * 是否需要循环播放
    * */
    @XmlAttribute(name = "Repeat")
    private Boolean repeat;

    /*
    * 是否同步播放
    * */
    @XmlAttribute(name = "Synchronous")
    private Boolean synchronous;
}
