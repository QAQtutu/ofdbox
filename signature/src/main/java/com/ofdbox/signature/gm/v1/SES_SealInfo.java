package com.ofdbox.signature.gm.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.*;

import java.util.Enumeration;

/*
 * 印章信息
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SES_SealInfo extends ASN1Object {

    /*
    * 印章头
    * */
    private SES_Header header;
    /*
    * 印章标识
    * */
    private DERIA5String esID;
    /*
    * 印章属性
    * */
    private SES_ESPropertyInfo property;
    /*
    * 印章图像数据
    * */
    private SES_ESPictureInfo picture;
    /*
    * 自定义数据
    * */
    private ExtensionDatas extDatas;

    private SES_SealInfo(ASN1Sequence sequence) {
        Enumeration<Object> emu = sequence.getObjects();
        this.header = SES_Header.getInstance(emu.nextElement());
        this.esID = DERIA5String.getInstance(emu.nextElement());
        this.property = SES_ESPropertyInfo.getInstance(emu.nextElement());
        this.picture = SES_ESPictureInfo.getInstance(emu.nextElement());
        if (emu.hasMoreElements()) {
            this.extDatas = ExtensionDatas.getInstance(emu.nextElement());
        }
    }

    public static SES_SealInfo getInstance(Object o) {
        if (o instanceof SES_SealInfo) {
            return (SES_SealInfo) o;
        } else {
            return new SES_SealInfo(ASN1Sequence.getInstance(o));
        }
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vector = new ASN1EncodableVector();
        vector.add(header);
        vector.add(esID);
        vector.add(property);
        vector.add(picture);
        if(extDatas!=null){
            vector.add(extDatas);
        }
        return new DERSequence(vector);
    }
}
