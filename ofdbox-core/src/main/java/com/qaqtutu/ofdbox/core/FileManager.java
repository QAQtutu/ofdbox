package com.qaqtutu.ofdbox.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public interface FileManager {
    void write(String loc, InputStream in) throws IOException;

    InputStream read(String loc);

    byte[] readBytes(String loc);

    Set<String> files();
}
