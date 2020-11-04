package com.ofdbox.core.xmlobj.adapter;

import com.ofdbox.core.utils.FormatUtils;
import com.ofdbox.core.xmlobj.st.ST_Box;
import com.ofdbox.core.exception.TypeConvertException;

/**
 * @description: ST_Box适配器
 * @author: 张家尧
 * @create: 2020/10/01 11:27
 */
public class StBoxAdapter extends BaseAdapter<ST_Box> {
    @Override
    public ST_Box unmarshal1(String v) throws Exception {
        if(v==null)return null;
        ST_Box st_box=new ST_Box();
        String[] s=v.split("\\s+");
        if(s.length!=4){
            throw new TypeConvertException(v);
        }
        st_box.setX(Double.valueOf(s[0]));
        st_box.setY(Double.valueOf(s[1]));
        st_box.setW(Double.valueOf(s[2]));
        st_box.setH(Double.valueOf(s[3]));
        return st_box;
    }

    @Override
    public String marshal1(ST_Box v) throws Exception {
        return String.format("%s %s %s %s", FormatUtils.doubleFormat(v.getX()),FormatUtils.doubleFormat(v.getY())
                ,FormatUtils.doubleFormat(v.getW()),FormatUtils.doubleFormat(v.getH()));
    }
}