package com.qaqtutu.ofdbox.core;

import com.qaqtutu.ofdbox.core.xmlobj.base.document.XDocument;
import com.qaqtutu.ofdbox.core.xmlobj.base.ofd.NDocBody;
import com.qaqtutu.ofdbox.core.xmlobj.base.res.NMultiMedia;
import com.qaqtutu.ofdbox.core.xmlobj.base.res.NMultiMedias;
import com.qaqtutu.ofdbox.core.xmlobj.base.res.XRes;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Document {

    private OFD ofd;
    private NDocBody nDocBody;
    private XDocument xDocument;

    private List<Page> pages=new ArrayList<>();
    private List<Template> templates=new ArrayList<>();

    private XRes xRes;

    public OFDFile getMultiMedia(Integer id){
        if(xRes.getMultiMedias()==null)return null;
        for(NMultiMedias nMultiMedias:xRes.getMultiMedias()){
            for(NMultiMedia nMultiMedia:nMultiMedias.getList()){
                if(nMultiMedia.getId().getId().equals(id)){
                    ST_Loc mediaLoc=new ST_Loc();
                    mediaLoc.setParent(xRes.getBaseLoc());
                    mediaLoc.setLoc(nMultiMedia.getMediaFile().getLoc());
                    byte[] bytes=ofd.getFileManager().readBytes(mediaLoc.getFullLoc());

                    OFDFile ofdFile=new OFDFile();
                    ofdFile.setLoc(mediaLoc.getFullLoc());
                    ofdFile.setBytes(bytes);
                    return ofdFile;
                }
            }
        }
        return null;
    }

    public Template getTemplate(Integer id){
        for(Template template:templates){
            if(template.getCt_templatePage().getId().getId().equals(id)){
                return template;
            }
        }
        return null;
    }
}
