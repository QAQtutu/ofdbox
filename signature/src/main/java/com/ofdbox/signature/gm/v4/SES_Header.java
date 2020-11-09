package com.ofdbox.signature.gm.v4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.*;

import java.util.Enumeration;

/*
* 印章头
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SES_Header extends ASN1Object {

    /*
    * 头标识
    * */
    private DERIA5String id;
    /*
    * 印章版本号
    * */
    private ASN1Integer version;
    /*
    * 厂商标识
    * */
    private DERIA5String vid;

    protected SES_Header(ASN1Sequence sequence) {
        Enumeration<Object> emu = sequence.getObjects();
        this.id = DERIA5String.getInstance(emu.nextElement());
        this.version = ASN1Integer.getInstance(emu.nextElement());
        this.vid = DERIA5String.getInstance(emu.nextElement());
    }

    public static SES_Header getInstance(Object o) {
        if (o instanceof SES_Header) {
            return (SES_Header) o;
        } else {
            return new SES_Header(ASN1Sequence.getInstance(o));
        }
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vector = new ASN1EncodableVector();
        vector.add(id);
        vector.add(version);
        vector.add(vid);
        return new DERSequence(vector);
    }
}
