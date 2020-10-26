//package com.qaqtutu.ofdbox.pdf;
//
//import com.qaqtutu.ofdbox.convertor.pdf.Ofd2pdf;
//import com.qaqtutu.ofdbox.core.OFD;
//import com.qaqtutu.ofdbox.core.OFDReader;
//import org.junit.Test;
//
//import java.io.IOException;
//
//public class PdfConverterTest {
//
//    @Test
//    public void toPdf() throws IOException {
//        OFDReader reader = new OFDReader();
//        OFD ofd = reader.read(PdfConverterTest.class.getResourceAsStream("发票.pdf"));
//
//        Ofd2pdf ofd2pdf = new Ofd2pdf();
//        ofd2pdf.toPdf(ofd.getDocuments().get(0), );
//    }
//}
