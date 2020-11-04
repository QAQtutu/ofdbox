package com.ofdbox.core;

import com.ofdbox.core.xmlobj.base.ofd.XOFD;
import com.ofdbox.core.utils.OfdXmlUtils;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class OFD extends FlushAble{

    private XOFD xofd;
    private FileManager fileManager;

    private List<Document> documents=new ArrayList<>();

    @Override
    public void flush() throws IOException {
        String ofdXml= OfdXmlUtils.toXml(this.xofd);
        String ofdPath="OFD.xml";
        fileManager.write(ofdPath,new ByteArrayInputStream(ofdXml.getBytes()));
    }

    public void addDocument(Document document){
        this.documents.add(document);
        document.setOfd(this);
    }
}
