package com.qaqtutu.ofdbox.pdf;

import com.qaqtutu.ofdbox.convertor.pdf.Ofd2pdf;
import com.qaqtutu.ofdbox.core.OFD;
import com.qaqtutu.ofdbox.core.OFDReader;
import com.qaqtutu.ofdbox.img.ImgConverterTest;
import com.qaqtutu.ofdbox.utils.PathUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class PdfConverterTest {
    protected static final String basePath = PathUtils.getClassPath(PdfConverterTest.class);


    @Test
    public void toPdf() throws IOException {

        OFDReader reader = new OFDReader();
        OFD ofd = reader.read(new File(basePath+"发票.ofd"));
        OFD ofd1 = reader.read(new File(basePath+"旋转测试.ofd"));
        OFD ofd2 = reader.read(new File(basePath+"222.ofd"));

        Ofd2pdf ofd2pdf = new Ofd2pdf();
        ofd2pdf.toPdf(ofd.getDocuments().get(0), new File(basePath,"发票.pdf"));
        ofd2pdf.toPdf(ofd1.getDocuments().get(0), new File(basePath,"旋转测试.pdf"));
        ofd2pdf.toPdf(ofd2.getDocuments().get(0), new File(basePath,"222.pdf"));
    }
}
