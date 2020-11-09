package com.ofdbox.convertor.test.font;

import com.ofdbox.core.xmlobj.base.res.NFonts;
import com.ofdbox.core.xmlobj.base.res.XRes;
import com.ofdbox.core.xmlobj.object.text.CT_Font;
import com.ofdbox.core.model.document.Document;
import com.ofdbox.core.model.OFD;
import com.ofdbox.core.OFDReader;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import com.ofdbox.convertor.test.utils.PathUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.apache.fontbox.ttf.TTFTable;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

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
        reader.getConfig().setValid(false);

//        for(File file:new File(basePath,"font").listFiles()){
//            if(file.isDirectory()||!file.getName().endsWith("ofd"))
//                continue;
        ofd(reader, new File(basePath, "font/字体测试-数科.ofd"));
//        ofd(reader, new File(basePath, "font/0000000005.ofd"));
        pdf(new File(basePath, "font/字体测试.pdf"));
//        pdf(new File(basePath, "font/字体测试-数科.pdf"));
//        pdf(new File(basePath, "font/字体测试-福昕.pdf"));

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

                        PDDocument doc=new PDDocument();

                        System.out.println(openTypeFont.getVersion()    );
                        openTypeFont.getOS2Windows();
                        System.out.println(openTypeFont.getGlyph());
//                        OS2WindowsMetricsTable os2=new OS2WindowsMetricsTable(openTypeFont);
//                        PDFont pdFont=PDType0Font.loadVertical(doc,openTypeFont,false);

//                        PDType0Font.load(doc,openTypeFont,false);
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
                PDType0Font pdType0Font=(PDType0Font)font;
                System.out.println(pdType0Font.getDescendantFont().getCIDSystemInfo());
                System.out.println(pdType0Font);


                try {
                    System.out.println(font.encode("宋"));
                }catch (Exception e){}

                // do stuff with the font
            }
        }

    }
}