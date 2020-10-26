package com.qaqtutu.ofdbox.core.xmlobj.base.page;


import com.qaqtutu.ofdbox.core.xmlobj.base.document.CT_PageArea;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.xmlobj.base.document.NActions;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Page",namespace = Const.NAMESPACE_URI)
public class XPage {

    /*
    * 模板页
    * */
    @Valid
    @XmlElement(name = "Template",namespace = Const.NAMESPACE_URI)
    private List<NTemplate> templates;

    /*
    * 该页面区域大小和位置 该页有效
    * */
    @Valid
    @XmlElement(name = "Area",namespace = Const.NAMESPACE_URI)
    private CT_PageArea area;

    /*
    * 页资源，指向该页使用资源文件
    * */
    @Valid
    @XmlElement(name = "PageRes",namespace = Const.NAMESPACE_URI)
    private List<ST_Loc> pageReses;

    /*
    * 页面内容描述
    * */
    @NotNull
    @Valid
    @XmlElement(name = "Content",namespace = Const.NAMESPACE_URI)
    private NPageContent content;

    /*
    * 与页面关联的动作序列
    * */
    @Valid
    @XmlElement(name = "Actions",namespace = Const.NAMESPACE_URI)
    private NActions actions;
}
