//package com.qaqtutu.ofdbox.core;
//
//import com.qaqtutu.ofdbox.core.utils.BeanValidUtils;
//import com.qaqtutu.ofdbox.core.utils.OfdXmlUtils;
//import com.qaqtutu.ofdbox.core.xmlobj.base.outlines.CT_OutlineElem;
//import com.qaqtutu.ofdbox.core.xmlobj.base.page.CT_PageBlock;
//import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
//import com.qaqtutu.ofdbox.core.xmlobj.base.ofd.XOFD;
//import com.qaqtutu.ofdbox.core.xmlobj.base.document.XDocument;
//import com.qaqtutu.ofdbox.core.xmlobj.base.page.XPage;
//import com.qaqtutu.ofdbox.core.xmlobj.base.page.object.NImageObject;
//import com.qaqtutu.ofdbox.core.xmlobj.base.page.object.NPageBlock;
//import com.qaqtutu.ofdbox.core.xmlobj.base.page.object.NPathObject;
//import org.apache.tools.zip.ZipEntry;
//import org.apache.tools.zip.ZipFile;
//
//import java.io.IOException;
//import java.util.Enumeration;
//import java.util.List;
//
//
//public class Test {
//
//    public static void main(String[] args) throws IOException {
////        String xml=FileUtils.readFileToString(new File("OFD.xml"),"utf-8");
////        System.out.println(xml);
////        XOFD xofd=OfdXmlUtils.toObject(new ByteArrayInputStream(xml.getBytes()), XOFD.class);
////        System.out.println(xofd);
////        System.out.println(OfdXmlUtils.toXml(xofd));
////
////        BeanValidUtils.valid(xofd);
////
//////        String xml1=FileUtils.readFileToString(new File("Document.xml"),"utf-8");
////        XDocument document=OfdXmlUtils.toObject(new FileInputStream(new File("Document.xml")), XDocument.class);
////        System.out.println(document);
////        System.out.println(OfdXmlUtils.toXml(document));
////
////        BeanValidUtils.valid(document);
////
////        System.out.println(Const.VERSION);
//
////        ZipFile zipFile =new ZipFile("C:\\Users\\hututu\\Desktop\\GBT_33190-2016_电子文件存储与交换格式版式文档.pdf.ofd");
//        ZipFile zipFile =new ZipFile("C:\\Users\\hututu\\Desktop\\helloworld_v2签章.ofd");
//        Enumeration<?> entries = zipFile.getEntries();
//
//        FileManager fileManager=new MemoryFileManager();
//
//        while (entries.hasMoreElements()) {
//            ZipEntry entry = (ZipEntry) entries.nextElement();
//            System.out.println(entry.getName());
//            if(!entry.isDirectory()){
//                fileManager.write("/"+entry.getName(),zipFile.getInputStream(entry));
//            }
//        }
//
//        /*
//         * OFD.xml
//         * */
//        XOFD xofd=OfdXmlUtils.toObject(fileManager.read("/OFD.xml"),XOFD.class);
//        BeanValidUtils.valid(xofd);
//        xofd.getDocBodys().forEach(xDocBody -> {
//            ST_Loc docRoot=xDocBody.getDocRoot();
//            docRoot.setPwd("/");
////            System.out.println(docRoot.getFullLoc());
//
//            /*
//            * Document.xml
//            * */
//            XDocument document=OfdXmlUtils.toObject(fileManager.read(docRoot.getFullLoc()),XDocument.class);
//            BeanValidUtils.valid(document);
//
////            defMark(document.getOutlines().getList(),0);
//
//            if(document.getCommonData().getDocumentRes()!=null){
//                document.getCommonData().getDocumentRes().forEach(st_loc -> {
//                    st_loc.setPwd(docRoot.getFullLoc());
//                    System.out.println(st_loc.getFullLoc());
//                    System.out.println(st_loc);
//                });
//            }
//
//            document.getPages().getList().forEach(xPage -> {
//                ST_Loc pageLoc=xPage.getBaseLoc();
//                pageLoc.setPwd(docRoot.getFullLoc());
//
//                /*
//                 * Page.xml
//                 * */
//                XPage page=OfdXmlUtils.toObject(fileManager.read(pageLoc.getFullLoc()),XPage.class);
//                BeanValidUtils.valid(xPage);
//
//                page.getContent().getLayers().size();
//
//                page.getContent().getLayers().forEach(xLayer -> {
////                    System.out.println("Layer:"+xLayer.getId());
//                    xLayer.getContent().forEach(xObject -> {
////                        System.out.println(xObject);
//                        if(xObject instanceof NPathObject){
//
//                        }else if(xObject instanceof NPageBlock){
//                            CT_PageBlock pageBlock=(CT_PageBlock) xObject;
//                            pageBlock.getContent().forEach(xObject1 -> {
//                                if(xObject1 instanceof NImageObject){
//                                    NImageObject obj=(NImageObject) xObject1;
//                                }
////                                System.out.println(xObject1);
//                            });
//                        }if(xObject instanceof NImageObject){
//                            NImageObject imageObject=(NImageObject) xObject;
////                            System.out.println(imageObject.getResourceId());
//                        }
//                    });
//                });
//            });
//        });
//    }
//
//    public static void defMark(List<CT_OutlineElem> outlineElems,int level){
//        if(outlineElems==null)return;
//        for(CT_OutlineElem outlineElem:outlineElems){
//            for(int i=0;i<level;i++){
//                System.out.print("---");
//            }
//            System.out.println(outlineElem.getTitle()+outlineElem.getActions());
//            defMark(outlineElem.getOutlineElems(),level+1);
//        }
//    }
//
//}
