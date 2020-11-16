//package com.ofdbox.convertor.test.pdf;
//
//import com.ofdbox.convertor.pdf.Ofd2Pdf;
//import com.ofdbox.core.model.OFD;
//import com.ofdbox.core.OFDReader;
//import com.ofdbox.convertor.test.utils.PathUtils;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//
//public class PdfConverterTest {
//    protected static final String basePath = PathUtils.getClassPath(PdfConverterTest.class);
//
//
//    @Test
//    public void toPdf() throws IOException {
//
//        OFDReader reader = new OFDReader();
//        OFD ofd = reader.read(new File(basePath+"发票.ofd"));
//        OFD ofd1 = reader.read(new File(basePath+"旋转测试.ofd"));
//
//        Ofd2Pdf ofd2pdf = new Ofd2Pdf();
//        ofd2pdf.toPdf(ofd.getDocuments().get(0), new File(basePath,"发票.pdf"));
//        ofd2pdf.toPdf(ofd1.getDocuments().get(0), new File(basePath,"旋转测试.pdf"));
//    }
//}
