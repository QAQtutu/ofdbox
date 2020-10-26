package com.qaqtutu.ofdbox.pdf;

import com.qaqtutu.ofdbox.convertor.pdf.Ofd2pdf;
import com.qaqtutu.ofdbox.core.OFD;
import com.qaqtutu.ofdbox.core.OFDReader;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class PdfConverterTest {
    private static File base=new File("target/test/pdf");

    @Test
    public void init() throws IOException {
        if(!base.exists()){
            FileUtils.forceMkdir(base);
        }
    }

    @Test
    public void toPdf() throws IOException {

        OFDReader reader = new OFDReader();
        OFD ofd = reader.read(new File("src/test/resources/发票.ofd"));
        OFD ofd1 = reader.read(new File("src/test/resources/旋转测试.ofd"));


        Ofd2pdf ofd2pdf = new Ofd2pdf();
        ofd2pdf.toPdf(ofd.getDocuments().get(0), new File(base,"发票.pdf"));
        ofd2pdf.toPdf(ofd1.getDocuments().get(0), new File(base,"旋转测试.pdf"));

    }
}
