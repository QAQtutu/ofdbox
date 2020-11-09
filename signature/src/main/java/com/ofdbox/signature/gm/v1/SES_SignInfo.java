package com.ofdbox.signature.gm.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bouncycastle.asn1.*;

import java.util.Enumeration;

@Data
@AllArgsConstructor
public class SES_SignInfo extends ASN1Object {

    private ASN1OctetString cert;
    private ASN1ObjectIdentifier signatureAlgorithm;
    private DERBitString signData;


    private SES_SignInfo(ASN1Sequence sequence) {
        Enumeration<Object> emu = sequence.getObjects();
        this.cert = ASN1OctetString.getInstance(emu.nextElement());
        this.signatureAlgorithm = ASN1ObjectIdentifier.getInstance(emu.nextElement());
        this.signData = DERBitString.getInstance(emu.nextElement());
    }

    public static SES_SignInfo getInstance(Object o) {
        if (o instanceof SES_SignInfo) {
            return (SES_SignInfo) o;
        } else {
            return new SES_SignInfo(ASN1Sequence.getInstance(o));
        }
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vector = new ASN1EncodableVector();
        vector.add(cert);
        vector.add(signatureAlgorithm);
        vector.add(signData);
        return new DERSequence(vector);
    }
}
