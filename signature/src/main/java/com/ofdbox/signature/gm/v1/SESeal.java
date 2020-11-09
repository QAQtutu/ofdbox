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
public class SESeal extends ASN1Object {

    /*
     * 印章信息
     * */
    private SES_SealInfo eSealInfo;

    private SES_SignInfo signInfo;

    private SESeal(ASN1Sequence sequence) {
        Enumeration<Object> emu = sequence.getObjects();
        this.eSealInfo = SES_SealInfo.getInstance(emu.nextElement());
        this.signInfo = SES_SignInfo.getInstance(emu.nextElement());
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
        vector.add(signInfo);
        return new DERSequence(vector);
    }
}
