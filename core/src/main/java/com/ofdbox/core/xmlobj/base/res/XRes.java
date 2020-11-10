package com.ofdbox.core.xmlobj.base.res;

import com.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "ofd:Res")
public class XRes {

    /*
    * 数据存储路径
    * */
    @NotNull
    @Valid
    @XmlAttribute(name = "BaseLoc")
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    private ST_Loc baseLoc;

    /*
    * 颜色空间描述
    * */
    @Valid
    @XmlElement(name = "ofd:ColorSpaces")
    private List<NColorSpaces> colorSpaces;

    /*
    * 绘制参数
    * */
    @Valid
    @XmlElement(name = "ofd:DrawParams")
    private List<NDrawParams> drawParams;

    /*
    * 字型资源描述
    * */
    @Valid
    @XmlElement(name = "ofd:Fonts")
    private List<NFonts> fonts;

    /*
    * 多媒体资源描述
    * */
    @Valid
    @XmlElement(name = "ofd:MultiMedias")
    private List<NMultiMedias> multiMedias;

    /*
    * 矢量图像
    * */
    @Valid
    @XmlElement(name = "ofd:CompositeGraphicUnits")
    private List<NCompositeGraphicUnits> compositeGraphicUnits;

}
