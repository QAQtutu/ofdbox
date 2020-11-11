package com.ofdbox.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description: 文件管理器内存实现
 * @author: 张家尧
 * @create: 2020/10/01 17:09
 */
public class MemoryFileManager implements FileManager{

    private Map<String,byte[]> store=new HashMap<>();
    @Override
    public void write(String loc, InputStream in) throws IOException {

        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = in.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }

        byte[] in2b = swapStream.toByteArray();

        store.put(loc,in2b);
    }

    @Override
    public InputStream read(String loc) {
        byte[] bytes=store.get(loc);
        if(bytes==null){
            loc=loc.toLowerCase();
            for(Map.Entry<String,byte[]> entry:store.entrySet()){
                if(entry.getKey().toLowerCase().equals(loc))
                    bytes=entry.getValue();
            }
        }
        ByteArrayInputStream in=new ByteArrayInputStream(bytes);
        return in;
    }

    @Override
    public byte[] readBytes(String loc) {
        byte[] bytes=store.get(loc);
        if(bytes==null){
            loc=loc.toLowerCase();
            for(Map.Entry<String,byte[]> entry:store.entrySet()){
                if(entry.getKey().toLowerCase().equals(loc))
                    return entry.getValue();
            }
        }
        return bytes;
    }

    @Override
    public Set<String> files() {
        return store.keySet();
    }

}