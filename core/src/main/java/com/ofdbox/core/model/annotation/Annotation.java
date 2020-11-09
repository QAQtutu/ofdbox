package com.ofdbox.core.model.annotation;

import com.ofdbox.core.model.PropertyChange;
import com.ofdbox.core.xmlobj.annotation.AnnotationType;
import com.ofdbox.core.xmlobj.annotation.NAnnot;
import com.ofdbox.core.xmlobj.annotation.NParameters;
import com.ofdbox.core.xmlobj.base.page.CT_PageBlock;
import com.ofdbox.core.xmlobj.st.ST_ID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Annotation implements PropertyChange {
    private AnnotationPage annotationPage;
    private NAnnot nAnnot;

    public Annotation(AnnotationPage annotationPage) {
        this.nAnnot = new NAnnot();
    }

    public Annotation(AnnotationPage annotationPage, NAnnot nAnnot) {
        this.nAnnot = nAnnot;
    }

    public ST_ID getId() {
        return nAnnot.getId();
    }


    public AnnotationType getType() {
        return nAnnot.getType();
    }


    public String getCreator() {
        return nAnnot.getCreator();
    }


    public String getLastModDate() {
        return nAnnot.getLastModDate();
    }


    public Boolean getVisible() {
        return nAnnot.getVisible() == null || nAnnot.getVisible();
    }


    public String getSubtype() {
        return nAnnot.getSubtype();
    }


    public Boolean getPrint() {
        return nAnnot.getPrint() == null || nAnnot.getVisible();
    }


    public Boolean getNoZoom() {
        return nAnnot.getNoZoom() != null && nAnnot.getNoZoom();
    }


    public Boolean getNoRotate() {
        return nAnnot.getNoRotate() != null && nAnnot.getNoRotate();
    }


    public Boolean getReadOnly() {
        return nAnnot.getReadOnly() == null || nAnnot.getReadOnly();
    }


    public String getRemark() {
        return nAnnot.getRemark();
    }


    public CT_PageBlock getAppearance() {
        return nAnnot.getAppearance();
    }


    public void setId(ST_ID id) {
        nAnnot.setId(id);
        this.onPropertyChange();
    }


    public void setType(AnnotationType type) {
        nAnnot.setType(type);
        this.onPropertyChange();
    }


    public void setCreator(String creator) {
        nAnnot.setCreator(creator);
        this.onPropertyChange();
    }


    public void setLastModDate(String lastModDate) {
        nAnnot.setLastModDate(lastModDate);
        this.onPropertyChange();
    }

    public void setVisible(Boolean visible) {
        nAnnot.setVisible(false);
        this.onPropertyChange();
    }


    public void setSubtype(String subtype) {
        nAnnot.setSubtype(subtype);
        this.onPropertyChange();
    }


    public void setPrint(Boolean print) {
        nAnnot.setPrint(false);
        this.onPropertyChange();
    }


    public void setNoZoom(Boolean noZoom) {
        nAnnot.setNoZoom(noZoom);
        this.onPropertyChange();
    }


    public void setNoRotate(Boolean noRotate) {
        nAnnot.setNoRotate(noRotate);
        this.onPropertyChange();
    }


    public void setReadOnly(Boolean readOnly) {
        nAnnot.setReadOnly(readOnly);
        this.onPropertyChange();
    }


    public void setRemark(String remark) {
        nAnnot.setRemark(remark);
        this.onPropertyChange();
    }


    public void setAppearance(CT_PageBlock appearance) {
        nAnnot.setAppearance(appearance);
        this.onPropertyChange();
    }

    public Map<String, String> getParameters() {
        Map<String, String> map = new HashMap<>();
        if (this.nAnnot.getParameters() == null || this.nAnnot.getParameters().getList() == null) {
            for (NParameters.NParameter parameter : this.nAnnot.getParameters().getList()) {
                map.put(parameter.getName(), parameter.getValue());
            }
        }
        return map;
    }

    public void setParameter(String name, String vale) {
        if (this.nAnnot.getParameters() == null) {
            this.nAnnot.setParameters(new NParameters());
            this.nAnnot.getParameters().setList(new ArrayList<>());
            NParameters.NParameter nParameter = new NParameters.NParameter();
            nParameter.setName(name);
            nParameter.setValue(vale);
            this.nAnnot.getParameters().getList().add(nParameter);
        }
        this.onPropertyChange();
    }

    @Override
    public void onPropertyChange() {
        annotationPage.onPropertyChange();
    }
}
