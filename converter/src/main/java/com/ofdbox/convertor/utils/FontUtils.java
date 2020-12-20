package com.ofdbox.convertor.utils;

import com.ofdbox.core.utils.OSinfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.fontbox.ttf.*;

import java.io.*;
import java.util.*;

@Slf4j
public class FontUtils {
    private static Map<String, String> pathMapping = new HashMap<>();
    private static Map<String, String> nameMapping = new HashMap<>();


    private static final String DEFAULT_FONT_DIR_MAC = "/System/Library/Fonts";
    private static final String DEFAULT_FONT_DIR_WINDOWS = "C:/Windows/Fonts";
    private static final String DEFAULT_FONT_DIR_LINUX = "/usr/share/fonts";

    static {
        if (OSinfo.isWindows()) {
            scanFontDir(new File(DEFAULT_FONT_DIR_WINDOWS));
        } else if (OSinfo.isMacOS()) {
            scanFontDir(new File(DEFAULT_FONT_DIR_MAC));
        } else if (OSinfo.isMacOSX()) {
            scanFontDir(new File(DEFAULT_FONT_DIR_MAC));
        } else if (OSinfo.isLinux()) {
            scanFontDir(new File(DEFAULT_FONT_DIR_LINUX));
        }
    }


    public static void addSystemFontMapping(String familyName, String fontName, String fontFilePath) {
        if (StringUtils.isBlank(familyName) || StringUtils.isBlank(fontName) || StringUtils.isBlank(fontFilePath)) {
            log.error(String.format("添加系统字体映射失败，FamilyName:%s FontName:%s 路径：%s", familyName, fontName, fontFilePath));
        }
        File file = new File(fontFilePath);
        if (!file.exists()) {
            log.error(String.format("添加系统字体映射失败，字体文件：%s 不存在", fontFilePath));
        }
        if (file.isDirectory()) {
            log.error(String.format("添加系统字体映射失败，%s 不是一个文件", fontFilePath));
        }
        if (!file.getName().toLowerCase().endsWith("otf") && !file.getName().toLowerCase().endsWith("ttf") && !file.getName().toLowerCase().endsWith("ttc")) {
            log.error(String.format("添加系统字体映射失败，%s 不是一个OpenType字体文件", fontFilePath));
        }
        synchronized (pathMapping) {
            pathMapping.put(familyName + "$$$$" + fontName, fontFilePath);
        }
    }

    public static TrueTypeFont loadSystemFont(String familyName, String fontName) {
        if (familyName == null) {
            familyName = "null";
        }
//        if (StringUtils.isBlank(fontName)) {
//            return null;
//        }
//        if (familyNameMapping.get(familyName) != null) {
//            familyName = familyNameMapping.get(familyName);
//        }
        String key = familyName + "$$$$" + fontName;
        if (nameMapping.get(key) != null) {
            fontName = nameMapping.get(key);
        }
        String fontFilePath = pathMapping.get(key);
        if (fontFilePath == null) {
            return null;
        }
        File file = new File(fontFilePath);
        try {
            if (fontFilePath.endsWith("ttc")) {
                TrueTypeCollection trueTypeCollection = new TrueTypeCollection(file);
                TrueTypeFont trueTypeFont = trueTypeCollection.getFontByName(fontName);
                return trueTypeFont;
            } else {
                OTFParser parser = new OTFParser(false);
                OpenTypeFont openTypeFont = parser.parse(file);
                return openTypeFont;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TrueTypeFont loadDefaultFont() {
        InputStream in = FontUtils.class.getResourceAsStream("/wqy-zenhei.ttc");
        try {
            TrueTypeCollection trueTypeCollection = new TrueTypeCollection(in);
            TrueTypeFont trueTypeFont = trueTypeCollection.getFontByName("WenQuanYiZenHei");
            return trueTypeFont;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void scanFontDir(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanFontDir(file);
            } else {
                loadFont(file);
            }
        }
    }

    public static void loadFont(File file) {
        if (file.getName().toLowerCase().endsWith("ttc")) {
            try {
                TrueTypeCollection trueTypeCollection = new TrueTypeCollection(file);
                trueTypeCollection.processAllFonts(new TrueTypeCollection.TrueTypeFontProcessor() {
                    @Override
                    public void process(TrueTypeFont trueTypeFont) throws IOException {
                        NamingTable namingTable = trueTypeFont.getNaming();
                        addSystemFontMapping(namingTable, file.getPath());
                    }
                });
            } catch (IOException e) {
                log.warn("加载字体失败：" + file.getAbsolutePath());
            }
        }
        if (!file.getName().toLowerCase().endsWith("otf") && !file.getName().toLowerCase().endsWith("ttf")) {
            return;
        }
        try {
            OTFParser parser = new OTFParser(true);
            OpenTypeFont openTypeFont = parser.parse(file);
            NamingTable namingTable = openTypeFont.getNaming();
            addSystemFontMapping(namingTable, file.getPath());
        } catch (Exception e) {
            log.warn("加载字体失败：" + file.getAbsolutePath());
        }
    }

    private static void addSystemFontMapping(NamingTable namingTable, String path) {
//        String family = null;
        String name = null;
//        String cnFamily = null;
//        String cnName = null;
        Set<String> familyNames = new HashSet<>();
        Set<String> fontNames = new HashSet<>();
        familyNames.add(namingTable.getFontFamily());
        fontNames.add(namingTable.getPostScriptName());
        name = namingTable.getPostScriptName();
        for (NameRecord record : namingTable.getNameRecords()) {
            if (record.getNameId() == 1) {
                familyNames.add(record.getString());
            } else if (record.getNameId() == 4) {
                fontNames.add(record.getString());
            }
            if (record.getLanguageId() == 0) {
//                if (record.getNameId() == 1) {
//                    family = record.getString();
//                } else
                if (record.getNameId() == 4) {
                    name = record.getString();
                }
            }
//            if (record.getLanguageId() == 2052) {
//                if (record.getNameId() == 1) {
//                    cnFamily = record.getString();
//                } else if (record.getNameId() == 4) {
//                    cnName = record.getString();
//                }
//            }

        }
        String finalName = name;
        familyNames.forEach(familyName -> {
            fontNames.forEach(fontName -> {
                nameMapping.put(familyName + "$$$$" + fontName, finalName);
                nameMapping.put("null$$$$" + fontName, finalName);
                log.info(String.format("注册字体 %s,%s,%s", familyName, fontName, path));
                addSystemFontMapping(familyName, fontName, path);
                addSystemFontMapping("null", fontName, path);
            });
        });
//        System.out.println(String.format("%s %s %s %s", family, name, cnFamily, cnName));
    }

    public static void main(String[] args) throws IOException {
        FontUtils.scanFontDir(new File("C:/Windows/Fonts"));
        System.out.println(FontUtils.loadSystemFont("Microsoft YaHei", "MicrosoftYaHei"));
        System.out.println(FontUtils.loadSystemFont("Microsoft YaHei", "微软雅黑"));
        System.out.println(FontUtils.loadSystemFont("微软雅黑", "MicrosoftYaHei"));
        System.out.println(FontUtils.loadSystemFont("微软雅黑", "微软雅黑"));
    }

}
