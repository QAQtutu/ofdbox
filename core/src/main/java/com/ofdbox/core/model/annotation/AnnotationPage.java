package com.ofdbox.core.model.annotation;

import com.ofdbox.core.model.PropertyChange;
import com.ofdbox.core.xmlobj.annotation.NAnnotationPage;
import com.ofdbox.core.xmlobj.annotation.XPageAnnot;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class AnnotationPage implements PropertyChange {

    @Getter
    @Setter
    private ST_Loc loc;

    private NAnnotationPage nAnnotationPage;
    private XPageAnnot xPageAnnot;
    private List<Annotation> annotations;

    public AnnotationPage(Annotations annotations){

    }
    public AnnotationPage(Annotations annotations,XPageAnnot xPageAnnot){
        this.xPageAnnot=xPageAnnot;
    }

    @Override
    public void onPropertyChange() {

    }
}
