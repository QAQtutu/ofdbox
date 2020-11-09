package com.ofdbox.core.model.document;

import com.ofdbox.core.OFDFile;
import com.ofdbox.core.Template;
import com.ofdbox.core.model.Annotations;
import com.ofdbox.core.model.OFD;
import com.ofdbox.core.model.Signatures;
import com.ofdbox.core.model.page.Page;
import com.ofdbox.core.xmlobj.annotation.XAnnotations;
import com.ofdbox.core.xmlobj.base.document.XDocument;
import com.ofdbox.core.xmlobj.base.ofd.NDocBody;
import com.ofdbox.core.xmlobj.base.res.NFonts;
import com.ofdbox.core.xmlobj.base.res.NMultiMedia;
import com.ofdbox.core.xmlobj.base.res.NMultiMedias;
import com.ofdbox.core.xmlobj.base.res.XRes;
import com.ofdbox.core.xmlobj.object.text.CT_Font;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class Document {

    @Getter
    @Setter
    private ST_Loc loc;
    private OFD ofd;
    private NDocBody nDocBody;
    private XDocument xDocument;

    private List<Page> pages=new ArrayList<>();
    private List<Template> templates=new ArrayList<>();

    private List<XRes> publicResList;
    private List<XRes> res;

    private Annotations annotations;
    private Signatures signatures;

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
    public CT_Font getFont(Integer id){
        for(XRes xRes:allRes()){
            if(xRes.getFonts()==null)continue;
            for(NFonts nFonts:xRes.getFonts()){
                for(CT_Font ctFont:nFonts.getList()){
                    if(ctFont.getId().getId().equals(id)){
                        if(ctFont.getFontFile()!=null){
                            ctFont.getFontFile().setParent(xRes.getBaseLoc());
                        }
                        return ctFont;
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
