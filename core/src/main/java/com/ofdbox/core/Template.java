package com.ofdbox.core;

import com.ofdbox.core.model.page.Page;
import com.ofdbox.core.xmlobj.base.document.CT_TemplatePage;
import lombok.Data;

@Data
public class Template extends Page {

    private CT_TemplatePage ct_templatePage;

}
