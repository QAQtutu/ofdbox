package com.qaqtutu.ofdbox.font;

import com.qaqtutu.ofdbox.core.Document;
import com.qaqtutu.ofdbox.core.OFD;
import com.qaqtutu.ofdbox.core.OFDReader;
import com.qaqtutu.ofdbox.core.xmlobj.base.res.NFont;
import com.qaqtutu.ofdbox.core.xmlobj.base.res.NFonts;
import com.qaqtutu.ofdbox.core.xmlobj.base.res.XRes;
import com.qaqtutu.ofdbox.core.xmlobj.object.text.CT_Font;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
import com.qaqtutu.ofdbox.utils.PathUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.apache.fontbox.ttf.TTFTable;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张家尧
 * @create: 2020/10/28 13:29
 */
@Slf4j
public class FontTest {

    protected static final String basePath = PathUtils.getClassPath(FontTest.class);

    @Test
    public void consoleAllFont() throws IOException {

        OFDReader reader = new OFDReader();
        reader.getConfig().setIgnoreNamespace(true);

//        for(File file:new File(basePath,"font").listFiles()){
//            if(file.isDirectory()||!file.getName().endsWith("ofd"))
//                continue;
//        ofd(reader, new File(basePath, "font/字体测试-数科.ofd"));
//        ofd(reader, new File(basePath, "font/字体测试-福昕.ofd"));
//        pdf(new File(basePath, "font/字体测试.pdf"));
        pdf(new File(basePath, "font/字体测试-数科.pdf"));
        pdf(new File(basePath, "font/字体测试-福昕.pdf"));

    }

    public void ofd(OFDReader reader, File file) throws IOException {
        OFD ofd = reader.read(file);
        for (Document document : ofd.getDocuments()) {
            for (XRes xRes : document.allRes()) {
                if (xRes.getFonts() == null) continue;
                for (NFonts nFonts : xRes.getFonts()) {
                    for (CT_Font ct_font : nFonts.getList()) {
                        if (ct_font.getFontFile() == null) continue;

                        ST_Loc loc = new ST_Loc();
                        loc.setLoc(ct_font.getFontFile().getLoc());
                        loc.setParent(xRes.getBaseLoc());

                        OTFParser parser = new OTFParser(true);
                        OpenTypeFont openTypeFont = parser.parse(ofd.getFileManager().read(loc.getFullLoc()));

                        log.info("-----------------文件名:" + file.getName() + "-----------------");
                        log.info("-----------------字体文件名:" + loc.getLoc() + "-----------------");
                        log.info("-----------------字体名:" + openTypeFont.getName() + "-----------------");
                        log.info("-----------------Version:" + openTypeFont.getVersion() + "-----------------");

                        for (TTFTable ttfTable : openTypeFont.getTables()) {
                            log.info(ttfTable.getTag());
                        }

                    }
                }
            }

        }
    }

    public void pdf(File file) throws IOException {
        PDDocument document = PDDocument.load(file);
//        document.getResourceCache().get
        System.out.println(file.getName());

//        document.font

        PDFTextStripper stripper = new PDFTextStripper();
        System.out.println(stripper.getText(document));

        for (int i = 0; i < document.getNumberOfPages(); ++i)
        {

            PDPage page = document.getPage(i);



            PDResources res = page.getResources();
            for (COSName fontName : res.getFontNames())
            {
                PDFont font = res.getFont(fontName);
                System.out.println(font);
                // do stuff with the font
            }
        }

    }
}