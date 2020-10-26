package com.qaqtutu.ofdbox.core.xmlobj.adapter;

import com.qaqtutu.ofdbox.core.utils.FormatUtils;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Pos;
import com.qaqtutu.ofdbox.core.exception.TypeConvertException;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @description: ST_Pos适配器
 * @author: 张家尧
 * @create: 2020/10/01 13:33
 */
public class StPosAdapter extends XmlAdapter<String, ST_Pos> {
    @Override
    public ST_Pos unmarshal(String v) throws Exception {
        if (v == null)
            throw new TypeConvertException("错误的ST_Pos格式：" + v);
        String[] s = v.split(" ");
        if (s.length != 2) {
            throw new TypeConvertException("错误的ST_Pos格式：" + v);
        }
        ST_Pos st_pos = new ST_Pos();
        st_pos.setX(Double.valueOf(s[0]));
        st_pos.setY(Double.valueOf(s[1]));
        return st_pos;
    }

    @Override
    public String marshal(ST_Pos v) throws Exception {
        return String.format("%s %s", FormatUtils.doubleFormat(v.getX()), FormatUtils.doubleFormat(v.getY()));
    }
}