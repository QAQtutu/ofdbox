package com.qaqtutu.ofdbox.core.xmlobj.base.res;

import com.qaqtutu.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
import com.qaqtutu.ofdbox.core.contance.Const;
import lombok.Data;

import javax.validation.Valid;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "Res", namespace = Const.NAMESPACE_URI)
public class XRes {

    /*
    * 数据存储路径
    * */
    @XmlAttribute(name = "BaseLoc")
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    private ST_Loc baseLoc;

    /*
    * 颜色空间描述
    * */
    @Valid
    @XmlElement(name = "ColorSpaces", namespace = Const.NAMESPACE_URI)
    private List<NColorSpaces> colorSpaces;

    /*
    * 绘制参数
    * */
    @Valid
    @XmlElement(name = "DrawParams", namespace = Const.NAMESPACE_URI)
    private List<NDrawParams> drawParams;

    /*
    * 字型资源描述
    * */
    @Valid
    @XmlElement(name = "Fonts", namespace = Const.NAMESPACE_URI)
    private List<NFonts> fonts;

    /*
    * 多媒体资源描述
    * */
    @Valid
    @XmlElement(name = "MultiMedias", namespace = Const.NAMESPACE_URI)
    private List<NMultiMedias> multiMedias;

    /*
    * 矢量图像
    * */
    @Valid
    @XmlElement(name = "CompositeGraphicUnits", namespace = Const.NAMESPACE_URI)
    private List<NCompositeGraphicUnits> compositeGraphicUnits;

}
