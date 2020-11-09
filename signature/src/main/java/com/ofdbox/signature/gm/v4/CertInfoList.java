package com.ofdbox.signature.gm.v4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertInfoList extends ASN1Object {

    List<ASN1OctetString> certs;

    private CertInfoList(ASN1Sequence sequence) {
        Enumeration<Object> emu = sequence.getObjects();
        certs = new ArrayList<>();
        while (emu.hasMoreElements()) {
            certs.add(ASN1OctetString.getInstance(emu.nextElement()));
        }
    }

    public static CertInfoList getInstance(Object o) {
        if (o instanceof CertInfoList) {
            return (CertInfoList) o;
        } else {
            return new CertInfoList(ASN1Sequence.getInstance(o));
        }
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vector = new ASN1EncodableVector();
        for (ASN1OctetString cert : certs) {
            vector.add(cert);
        }
        return new DERSequence(vector);
    }
}
