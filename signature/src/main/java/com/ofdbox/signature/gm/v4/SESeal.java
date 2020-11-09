package com.ofdbox.signature.gm.v4;

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
public class SESeal extends ASN1Object {

    /*
     * 印章信息
     * */
    private SES_SealInfo eSealInfo;
    /*
     * 制章者证书
     * */
    private ASN1OctetString cert;
    /*
     * 签名算法标识
     * */
    private ASN1ObjectIdentifier signAlgID;
    /*
     * 签名值
     * */
    private ASN1BitString signedValue;

    private SESeal(ASN1Sequence sequence) {
        Enumeration<Object> emu = sequence.getObjects();
        this.eSealInfo = SES_SealInfo.getInstance(emu.nextElement());
        this.cert = ASN1OctetString.getInstance(emu.nextElement());
        this.signAlgID = ASN1ObjectIdentifier.getInstance(emu.nextElement());
        this.signedValue = (ASN1BitString) emu.nextElement();
    }

    public static SESeal getInstance(Object o) {
        if (o instanceof SESeal) {
            return (SESeal) o;
        } else {
            return new SESeal(ASN1Sequence.getInstance(o));
        }
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vector = new ASN1EncodableVector();
        vector.add(eSealInfo);
        vector.add(cert);
        vector.add(signAlgID);
        vector.add(signedValue);
        return new DERSequence(vector);
    }
}
