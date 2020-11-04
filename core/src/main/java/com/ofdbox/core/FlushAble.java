package com.ofdbox.core;

import lombok.Data;

import java.io.IOException;

@Data
public abstract class FlushAble {
    protected FileManager fileManager;
    public abstract void flush() throws IOException;
}
