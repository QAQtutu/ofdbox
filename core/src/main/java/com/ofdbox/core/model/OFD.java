package com.ofdbox.core.model;

import com.ofdbox.core.model.document.Document;
import com.ofdbox.core.FileManager;
import com.ofdbox.core.FlushAble;
import com.ofdbox.core.xmlobj.base.ofd.XOFD;
import com.ofdbox.core.utils.OfdXmlUtils;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class OFD extends FlushAble {

    private XOFD xofd;
    private FileManager fileManager;

    private List<Document> documents=new ArrayList<>();

    @Override
    public void flush() throws IOException {

    }

    public void addDocument(Document document){
        this.documents.add(document);
        document.setOfd(this);
    }
}
