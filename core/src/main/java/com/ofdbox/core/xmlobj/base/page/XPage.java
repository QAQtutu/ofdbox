package com.ofdbox.core.xmlobj.base.page;


import com.ofdbox.core.xmlobj.st.ST_Loc;
import com.ofdbox.core.xmlobj.base.document.CT_PageArea;
import com.ofdbox.core.xmlobj.base.document.NActions;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ofd:Page")
public class XPage {

    /*
    * 模板页
    * */
    @Valid
    @XmlElement(name = "ofd:Template")
    private List<NTemplate> templates;

    /*
    * 该页面区域大小和位置 该页有效
    * */
    @Valid
    @XmlElement(name = "ofd:Area")
    private CT_PageArea area;

    /*
    * 页资源，指向该页使用资源文件
    * */
    @Valid
    @XmlElement(name = "ofd:PageRes")
    private List<ST_Loc> pageReses;

    /*
    * 页面内容描述
    * */
    @NotNull
    @Valid
    @XmlElement(name = "ofd:Content")
    private NPageContent content;

    /*
    * 与页面关联的动作序列
    * */
    @Valid
    @XmlElement(name = "ofd:Actions")
    private NActions actions;
}
