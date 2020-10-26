package com.qaqtutu.ofdbox.core;

import com.qaqtutu.ofdbox.core.xmlobj.base.page.NTemplate;
import com.qaqtutu.ofdbox.core.xmlobj.base.page.XPage;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Box;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
