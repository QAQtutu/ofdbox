package com.ofdbox.core;

import com.ofdbox.core.utils.OfdXmlUtils;
import com.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public abstract class FlushAble {

    @Setter
    @Getter
    private ST_Loc loc;

    protected FileManager fileManager;

    public  void flush() throws IOException{
        String xml= OfdXmlUtils.toXml(this);
        fileManager.write(loc.getFullLoc(),new ByteArrayInputStream(xml.getBytes()));
    }
}
