package com.qaqtutu.ofdbox.core;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @description: test
 * @author: 张家尧
 * @create: 2020/10/01 17:45
 */
public class Test1 {

    public static void main(String[] args) throws IOException {

        OFDReader reader=new OFDReader();
        OFD ofd=reader.read(new File("C:\\Users\\hututu\\Desktop\\GBT_33190-2016_电子文件存储与交换格式版式文档.pdf.ofd"));
        ofd.flush();
        OFDWriter.write(ofd,new File("C:\\Users\\hututu\\Desktop\\helloworld_v2签章2.ofd"));
    }
}