package com.ofdbox.core.xmlobj.object.text;

import com.ofdbox.core.xmlobj.base.page.object.NObject;
import com.ofdbox.core.xmlobj.object.PathOrText;
import com.ofdbox.core.xmlobj.st.ST_RefID;
import com.ofdbox.core.xmlobj.pagedesc.color.CT_Color;
import com.ofdbox.core.xmlobj.adapter.StRefIdAdapter;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement
public class CT_Text extends PathOrText implements NObject {

    /*
     * 引用资源文件中定义的字型的标识
     * */
    @XmlJavaTypeAdapter(value = StRefIdAdapter.class)
    @XmlAttribute(name = "Font")
    private ST_RefID font;

    /*
     * 字号
     * */
    @XmlAttribute(name = "Size")
    private Double size;

    /*
     * 是否勾边
     * */
    @XmlAttribute(name = "Stroke")
    private Boolean stroke;

    /*
     * 是否填充
     * */
    @XmlAttribute(name = "Fill")
    private Boolean fill;

    /*
     * 字型的水平方向的缩放比
     * */
    @XmlAttribute(name = "HScale")
    private Double hScale;

    /*
     * 阅读方向
     * */
    @XmlAttribute(name = "ReadDirection")
    private Integer readDirection;

    /*
     * 字符方向
     * */
    @XmlAttribute(name = "CharDirection")
    private Integer charDirection;

    /*
     * 文字对象的粗细值
     * */
    @XmlAttribute(name = "Weight")
    private Integer weight;

    /*
     * 是否是斜体样式
     * */
    @XmlAttribute(name = "Italic")
    private Boolean italic;

    /*
     * 填充颜色
     * */
    @XmlElement(name = "ofd:FillColor")
    private CT_Color fillColor;

    /*
     * 勾边颜色
     * */
    @XmlElement(name = "ofd:StrokeColor")
    private CT_Color strokeColor;

    /*
     * 指定字符编码到字符索引的变换关系
     * */
    @XmlElement(name = "ofd:CGTransform")
    private List<CT_CGTransform> transforms;

    /*
     * 文字内容
     * */
    @XmlElement(name = "ofd:TextCode", type = NTextCode.class)
    private List<NTextCode> textCodes;


    @AssertTrue(message = " CT_Text至少得有一个CT_CGTransform或TextCode")
    private boolean getValid() {
        return (transforms != null && transforms.size() > 0)
                ||
                (textCodes != null && textCodes.size() > 0);

    }
}
