package com.ofdbox.core;

import com.ofdbox.core.model.Annotations;
import com.ofdbox.core.model.OFD;
import com.ofdbox.core.model.Signatures;
import com.ofdbox.core.model.document.Document;
import com.ofdbox.core.model.page.Page;
import com.ofdbox.core.xmlobj.annotation.NAnnotationPage;
import com.ofdbox.core.xmlobj.annotation.XAnnotations;
import com.ofdbox.core.xmlobj.annotation.XPageAnnot;
import com.ofdbox.core.xmlobj.base.document.CT_TemplatePage;
import com.ofdbox.core.xmlobj.base.document.XDocument;
import com.ofdbox.core.xmlobj.base.ofd.NDocBody;
import com.ofdbox.core.xmlobj.base.ofd.XOFD;
import com.ofdbox.core.xmlobj.base.page.XPage;
import com.ofdbox.core.xmlobj.base.pages.NPage;
import com.ofdbox.core.xmlobj.base.res.XRes;
import com.ofdbox.core.utils.BeanValidUtils;
import com.ofdbox.core.utils.OfdXmlUtils;
import com.ofdbox.core.xmlobj.signature.NSignature;
import com.ofdbox.core.xmlobj.signature.XSignature;
import com.ofdbox.core.xmlobj.signature.XSignatures;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;


import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
            Enumeration<?> entries = zipFile.entries();

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
                        if(xRes.getBaseLoc()==null){
                            xRes.setBaseLoc(new ST_Loc());
                            xRes.getBaseLoc().setLoc("./");
                        }
                        xRes.getBaseLoc().setParent(resLoc);

                        document.getRes().add(xRes);
                        if (!xRes.getBaseLoc().getLoc().endsWith("/")) {
                            xRes.getBaseLoc().setLoc(xRes.getBaseLoc().getLoc() + "/");
                        }
                    }
                }

                /*
                 * 公共资源
                 * */
                if (xDocument.getCommonData().getPublicRes() != null) {
                    document.setPublicResList(new ArrayList<>());
                    for (ST_Loc resLoc : xDocument.getCommonData().getPublicRes()) {
                        resLoc.setParent(docRoot);
                        XRes xRes = OfdXmlUtils.toObject(fileManager.read(resLoc.getFullLoc()), XRes.class);
                        valid(xRes);
                        if(xRes.getBaseLoc()==null){
                            xRes.setBaseLoc(new ST_Loc());
                            xRes.getBaseLoc().setLoc("./");
                        }
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

                        template.setDocument(document);
                    }
                }

                /*
                 * Page
                 * */
                document.setPages(new ArrayList<>());
                for (NPage nPage : xDocument.getPages().getList()) {

                    Page page = new Page();
                    page.setDocument(document);

                    ST_Loc pageBaseLoc = nPage.getBaseLoc();
                    pageBaseLoc.setParent(docRoot);
                    XPage xPage = OfdXmlUtils.toObject(fileManager.read(pageBaseLoc.getFullLoc()), XPage.class, this.config.ignoreNamespace);

                    page.setXPage(xPage);
                    page.setNPage(nPage);
                    document.getPages().add(page);
                }

                /*
                 * Annotations
                 * */
                ST_Loc annotationsLoc = xDocument.getAnnotations();
                if (annotationsLoc != null) {
                    annotationsLoc.setParent(docRoot);
                    XAnnotations xAnnotations = OfdXmlUtils.toObject(fileManager.read(annotationsLoc.getFullLoc()), XAnnotations.class, this.config.ignoreNamespace);

                    Annotations annotations = new Annotations();
                    annotations.setXAnnotations(xAnnotations);
                    annotations.setAnnotationPages(new ArrayList<>());
                    document.setAnnotations(annotations);
                    for (NAnnotationPage annotationPage : xAnnotations.getPages()) {
                        ST_Loc annotationPageLoc = annotationPage.getFileLoc();
                        annotationPageLoc.setParent(annotationsLoc);

                        XPageAnnot xPageAnnot = OfdXmlUtils.toObject(fileManager.read(annotationPageLoc.getFullLoc()), XPageAnnot.class, this.config.ignoreNamespace);
                        System.out.println(xPageAnnot);

                        Annotations.AnnotationPage annotationPage1 = new Annotations.AnnotationPage();
                        annotationPage1.setNAnnotationPage(annotationPage);
                        annotationPage1.setXPageAnnot(xPageAnnot);

                        annotations.getAnnotationPages().add(annotationPage1);
                    }
                }

                /*
                 * Signatures
                 * */
                ST_Loc signaturesLoc = nDocBody.getSignatures();
                if (signaturesLoc != null) {
                    XSignatures xSignatures = OfdXmlUtils.toObject(fileManager.read(signaturesLoc.getFullLoc()), XSignatures.class, this.config.ignoreNamespace);
                    Signatures signatures = new Signatures();
                    signatures.setXSignatures(xSignatures);
                    signatures.setSignatureList(new ArrayList<>());
                    document.setSignatures(signatures);
                    for (NSignature nSignature : xSignatures.getSignatures()) {
                        ST_Loc signatureLoc = nSignature.getBaseLoc();
                        signatureLoc.setParent(signaturesLoc);
                        XSignature xSignature = OfdXmlUtils.toObject(fileManager.read(signatureLoc.getFullLoc()), XSignature.class, this.config.ignoreNamespace);
                        Signatures.Signature signature = new Signatures.Signature();
                        signature.setNSignature(nSignature);
                        signature.setXSignature(xSignature);

                        xSignature.getSignedValue().setParent(signatureLoc);

                        signatures.getSignatureList().add(signature);
                    }
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
