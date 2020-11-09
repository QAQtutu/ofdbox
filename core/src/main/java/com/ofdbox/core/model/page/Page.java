package com.ofdbox.core.model.page;

import com.ofdbox.core.model.document.Document;
import com.ofdbox.core.xmlobj.base.page.XPage;
import com.ofdbox.core.xmlobj.base.pages.NPage;
import com.ofdbox.core.xmlobj.st.ST_Box;
import lombok.Data;

@Data
public class Page {

    private Document document;
    private XPage xPage;
    private NPage nPage;

    public ST_Box getPhysicalBox(){
        if(this.xPage.getArea()!=null && this.xPage.getArea().getPhysicalBox()!=null){
            return this.xPage.getArea().getPhysicalBox();
        }
        if(this.document.getXDocument().getCommonData().getPageArea()!=null){
            return this.document.getXDocument().getCommonData().getPageArea().getPhysicalBox();
        }
        return null;
    }

}
