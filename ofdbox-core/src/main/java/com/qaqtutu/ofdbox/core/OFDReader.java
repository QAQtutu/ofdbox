package com.qaqtutu.ofdbox.core;

import com.qaqtutu.ofdbox.core.utils.BeanValidUtils;
import com.qaqtutu.ofdbox.core.utils.OfdXmlUtils;
import com.qaqtutu.ofdbox.core.xmlobj.base.document.CT_TemplatePage;
import com.qaqtutu.ofdbox.core.xmlobj.base.document.XDocument;
import com.qaqtutu.ofdbox.core.xmlobj.base.ofd.NDocBody;
import com.qaqtutu.ofdbox.core.xmlobj.base.ofd.XOFD;
import com.qaqtutu.ofdbox.core.xmlobj.base.page.NTemplate;
import com.qaqtutu.ofdbox.core.xmlobj.base.page.XPage;
import com.qaqtutu.ofdbox.core.xmlobj.base.page.object.NImageObject;
import com.qaqtutu.ofdbox.core.xmlobj.base.pages.NPage;
import com.qaqtutu.ofdbox.core.xmlobj.base.res.XRes;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;

//@Slf4j
public class OFDReader {
    private ParserConfig config = new ParserConfig();

    //    public OFD read(InputStream in){
//
//    }


    public ParserConfig getConfig() {
        return config;
    }

    public OFD read(File file) throws IOException {
        try {
            OFD ofd = new OFD();
            ZipFile zipFile = new ZipFile(file);
            Enumeration<?> entries = zipFile.getEntries();

            FileManager fileManager = new MemoryFileManager();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
//                System.out.println(entry.getName());
                if (!entry.isDirectory()) {
                    fileManager.write("/" + entry.getName(), zipFile.getInputStream(entry));
                }
            }
            zipFile.close();

            ofd.setFileManager(fileManager);
            /*
             * OFD.xml
             * */
            XOFD xofd = OfdXmlUtils.toObject(fileManager.read("/OFD.xml"), XOFD.class);
            valid(xofd);
            ofd.setXofd(xofd);

            ofd.setDocuments(new ArrayList<>());

            for (NDocBody nDocBody : xofd.getDocBodys()) {
                ST_Loc docRoot = nDocBody.getDocRoot();

                /*
                 * Doc_0/Document.xml
                 * */
                XDocument xDocument = OfdXmlUtils.toObject(fileManager.read(docRoot.getFullLoc()), XDocument.class);
                valid(xDocument);

                Document document = new Document();
                document.setNDocBody(nDocBody);
                document.setXDocument(xDocument);

                ofd.addDocument(document);

                /*
                 * 资源
                 * */
                if (xDocument.getCommonData().getDocumentRes() != null) {
                    document.setRes(new ArrayList<>());
                    for (ST_Loc resLoc : xDocument.getCommonData().getDocumentRes()) {
                        resLoc.setParent(docRoot);
                        XRes xRes = OfdXmlUtils.toObject(fileManager.read(resLoc.getFullLoc()), XRes.class);
                        valid(xRes);
                        xRes.getBaseLoc().setParent(resLoc);

                        document.getRes().add(xRes);
                        if (!xRes.getBaseLoc().getLoc().endsWith("/")) {
                            xRes.getBaseLoc().setLoc(xRes.getBaseLoc().getLoc() + "/");
                        }
                    }
                }

                if (xDocument.getCommonData().getPublicRes() != null) {
                    document.setPublicResList(new ArrayList<>());
                    for (ST_Loc resLoc : xDocument.getCommonData().getPublicRes()) {
                        resLoc.setParent(docRoot);
                        XRes xRes = OfdXmlUtils.toObject(fileManager.read(resLoc.getFullLoc()), XRes.class);
                        valid(xRes);
                        xRes.getBaseLoc().setParent(resLoc);

                        document.getPublicResList().add(xRes);
                        if (!xRes.getBaseLoc().getLoc().endsWith("/")) {
                            xRes.getBaseLoc().setLoc(xRes.getBaseLoc().getLoc() + "/");
                        }
                    }
                }


                /*
                 * 模板
                 * */
                if (xDocument.getCommonData().getTemplatePages() != null) {
                    for (CT_TemplatePage ct_templatePage : xDocument.getCommonData().getTemplatePages()) {
                        ST_Loc templateLoc = ct_templatePage.getBaseLoc();
                        templateLoc.setParent(docRoot);
                        XPage xPage = OfdXmlUtils.toObject(fileManager.read(templateLoc.getFullLoc()), XPage.class);
                        Template template = new Template();
                        template.setXPage(xPage);
                        template.setCt_templatePage(ct_templatePage);
                        document.getTemplates().add(template);

                    }
                }


                document.setPages(new ArrayList<>());
                for (NPage nPage : xDocument.getPages().getList()) {

                    Page page = new Page();
                    page.setDocument(document);

                    ST_Loc pageBaseLoc = nPage.getBaseLoc();
                    pageBaseLoc.setParent(docRoot);
                    XPage xPage = OfdXmlUtils.toObject(fileManager.read(pageBaseLoc.getFullLoc()), XPage.class, this.config.ignoreNamespace);

                    page.setXPage(xPage);
                    document.getPages().add(page);
                }
            }
            return ofd;

        } finally {

        }
    }

    private void valid(Object object) {
        if (this.config.valid) {
            BeanValidUtils.valid(object);
        }
    }

    @Data
    public static class ParserConfig {
        private boolean valid = true;
        private boolean ignoreNamespace = false;
    }
}
