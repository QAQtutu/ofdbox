package com.ofdbox.core.model;

import com.ofdbox.core.model.annotation.AnnotationPage;
import com.ofdbox.core.model.document.Document;
import com.ofdbox.core.model.resource.Resource;
import com.ofdbox.core.xmlobj.annotation.NAnnotationPage;
import com.ofdbox.core.xmlobj.annotation.XAnnotations;
import com.ofdbox.core.xmlobj.annotation.XPageAnnot;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/*
* 临时对象
* */
@Data
public class Annotations {
    private XAnnotations xAnnotations;
    private List<AnnotationPage> annotationPages;

    @Data
    public static class AnnotationPage{
        private NAnnotationPage nAnnotationPage;
        private XPageAnnot xPageAnnot;
    }
}
