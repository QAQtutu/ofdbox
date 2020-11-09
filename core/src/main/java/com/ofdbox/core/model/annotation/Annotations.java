package com.ofdbox.core.model.annotation;

import com.ofdbox.core.model.PropertyChange;
import com.ofdbox.core.model.document.Document;
import com.ofdbox.core.model.resource.Resource;
import com.ofdbox.core.xmlobj.annotation.XAnnotations;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Getter;

import java.util.List;

public class Annotations {

    @Getter
    private ST_Loc loc;
    private Document document;
    private XAnnotations xAnnotations;
    private List<AnnotationPage> annotationPages;

    private Resource resource;
    private PropertyChange propertyChange=new PropertyChange() {
        @Override
        public void onPropertyChange() {

        }
    };

    /*
    * 用于新建
    * */
    public Annotations(Document document){
        this.document=document;
        xAnnotations=new XAnnotations();
        loc=new ST_Loc();
        loc.setParent(document.getLoc());
        loc.setLoc("Annotations.xml");
        document.getNDocBody().setSignatures(loc);
    }
    /*
    * 用于解析
    * */
    public Annotations(Document document,XAnnotations xAnnotations,List<AnnotationPage> annotationPages){
        this.document=document;
        this.loc=document.getNDocBody().getSignatures();
        this.loc.setParent(document.getLoc());
        this.xAnnotations=xAnnotations;
        this.annotationPages=annotationPages;
    }
}
