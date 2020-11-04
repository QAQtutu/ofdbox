package com.ofdbox.convertor.utils;

import com.ofdbox.core.utils.OSinfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.fontbox.ttf.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FontUtils {
    private static Map<String,String> pathMapping=new HashMap<>();
    private static Map<String,String> nameMapping=new HashMap<>();
    private static Map<String,String> familyNameMapping=new HashMap<>();

    static {
        String fontLocation=null;
        if(OSinfo.isWindows()){
            fontLocation= "/font_mapping/windows.txt";
        }
        if(fontLocation!=null){
            List<String[]> pathMappings=readConfig(fontLocation);
            for(String[] s:pathMappings){
                addSystemFontMapping(s[0],s[1],s[2]);
            }
        }
        List<String[]> nameMappings=readConfig("/font_mapping/name_mapping.txt");
        for(String[] s:nameMappings){
            nameMapping.put(s[0],s[1]);
        }
        List<String[]> familyNameMappings=readConfig("/font_mapping/family_name_mapping.txt");
        for(String[] s:familyNameMappings){
            familyNameMapping.put(s[0],s[1]);
        }

    }
    private static List<String[]> readConfig(String path){
        List<String[]> arr=new ArrayList<>();
        InputStream in = FontUtils.class.getResourceAsStream(path);
        InputStreamReader readStream= null;
        try {
            readStream = new InputStreamReader(in,"UTF-8");
            BufferedReader reader=new BufferedReader(readStream);
            String temp=null;
            while((temp=reader.readLine())!=null){
                String[] s=temp.split(",");
                arr.add(s);

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static void addSystemFontMapping(String familyName,String fontName,String fontFilePath){
        if(StringUtils.isBlank(familyName)||StringUtils.isBlank(fontName)||StringUtils.isBlank(fontFilePath)){
            log.error(String.format("添加系统字体映射失败，FamilyName:%s FontName:%s 路径：%s",familyName,fontName,fontFilePath));
        }
        File file=new File(fontFilePath);
        if(!file.exists()){
            log.error(String.format("添加系统字体映射失败，字体文件：%s 不存在",fontFilePath));
        }
        if(file.isDirectory()){
            log.error(String.format("添加系统字体映射失败，%s 不是一个文件",fontFilePath));
        }
        if(!file.getName().endsWith("otf")&&!file.getName().endsWith("ttf")&&!file.getName().endsWith("ttc")){
            log.error(String.format("添加系统字体映射失败，%s 不是一个OpenType字体文件",fontFilePath));
        }
        synchronized (pathMapping){
            pathMapping.put(familyName+"$$$$"+fontName,fontFilePath);
        }
    }

    public static TrueTypeFont loadSystemFont(String familyName,String fontName){
        if(StringUtils.isBlank(familyName)||StringUtils.isBlank(fontName)){
            return null;
        }
        if(familyNameMapping.get(familyName)!=null){
            familyName=familyNameMapping.get(familyName);
        }
        if(nameMapping.get(fontName)!=null){
            fontName=nameMapping.get(fontName);
        }
        String fontFilePath=pathMapping.get(familyName+"$$$$"+fontName);
        if(fontFilePath==null) {
            return null;
        }
        File file=new File(fontFilePath);
        try {
            if(fontFilePath.endsWith("ttc")){
                TrueTypeCollection trueTypeCollection=new TrueTypeCollection(file);
                TrueTypeFont trueTypeFont=trueTypeCollection.getFontByName(fontName);
                return trueTypeFont;
            }else{
                OTFParser parser = new OTFParser(false);
                OpenTypeFont openTypeFont= parser.parse(file);
                return openTypeFont;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TrueTypeFont loadDefaultFont(){
        InputStream in=FontUtils.class.getResourceAsStream("/wqy-zenhei.ttc");
        try {
            TrueTypeCollection trueTypeCollection = new TrueTypeCollection(in);
            TrueTypeFont trueTypeFont=trueTypeCollection.getFontByName("WenQuanYiZenHei");
            return trueTypeFont;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void scanFontDir(File fontDir){
        if(fontDir==null ||!fontDir.exists())
            return;
        for(File file:fontDir.listFiles()){
            if (file.getName().endsWith("ttc")){
                try {
                    TrueTypeCollection trueTypeCollection=new TrueTypeCollection(file);
                    trueTypeCollection.processAllFonts(new TrueTypeCollection.TrueTypeFontProcessor() {
                        @Override
                        public void process(TrueTypeFont trueTypeFont) throws IOException {
                            NamingTable namingTable=trueTypeFont.getNaming();
                            log.info(String.format("加载字体 %s,%s,%s",namingTable.getFontFamily(),namingTable.getPostScriptName(),file.getAbsoluteFile()));

                            addSystemFontMapping(namingTable.getFontFamily(),namingTable.getPostScriptName(),file.getAbsolutePath());
                        }
                    });
                } catch (IOException e) {
                    log.warn("加载字体失败："+file.getAbsolutePath());
                }
            }
            if(!file.getName().endsWith("otf") && !file.getName().endsWith("ttf")){
                continue;
            }
            try {
                OTFParser parser = new OTFParser(true);
                OpenTypeFont openTypeFont= parser.parse(file);
                NamingTable namingTable=openTypeFont.getNaming();
                log.info(String.format("加载字体 %s,%s,%s",namingTable.getFontFamily(),namingTable.getPostScriptName(),file.getAbsoluteFile()));
                addSystemFontMapping(namingTable.getFontFamily(),namingTable.getPostScriptName(),file.getAbsolutePath());
            }catch (Exception e){
                log.warn("加载字体失败："+file.getAbsolutePath());
            }
        }
    }

    public static void main(String[] args) throws IOException {
//        scanFontDir(new File("C:\\Windows\\Fonts"));
    }

}
