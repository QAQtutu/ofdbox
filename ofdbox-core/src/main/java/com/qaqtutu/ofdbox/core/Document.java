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

    private List<XRes> publicResList;
    private List<XRes> res;

    public OFDFile getMultiMedia(Integer id){
        for(XRes xRes:allRes()){
            if(xRes.getMultiMedias()==null)continue;
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

    public List<XRes> allRes(){
        List<XRes> all=new ArrayList<>();
        if(this.publicResList!=null)
             all.addAll(this.publicResList);
        if(this.res!=null)
            all.addAll(this.res);
        return all;
    }
}
