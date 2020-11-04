package com.ofdbox.core;

import com.ofdbox.core.xmlobj.base.page.XPage;
import com.ofdbox.core.xmlobj.st.ST_Box;
import lombok.Data;

@Data
public class Page {

    private Document document;
    private XPage xPage;

    public ST_Box getPhysicalBox(){
        if(this.xPage.getArea()!=null){
            return this.xPage.getArea().getPhysicalBox();
        }
        if(this.document.getXDocument().getCommonData().getPageArea()!=null){
            return this.document.getXDocument().getCommonData().getPageArea().getPhysicalBox();
        }
        return null;
    }

}
