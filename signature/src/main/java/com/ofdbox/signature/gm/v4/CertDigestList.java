package com.ofdbox.signature.gm.v4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Data
@AllArgsConstructor
public class CertDigestList extends ASN1Object {

    List<CertDigestObj> certDigestObjs = new ArrayList<>();

    private CertDigestList(ASN1Sequence sequence) {
        Enumeration<Object> emu = sequence.getObjects();
        while (emu.hasMoreElements()) {
            certDigestObjs.add(CertDigestObj.getInstance(emu.nextElement()));
        }
    }

    public static CertDigestList getInstance(Object o) {
        if (o instanceof CertDigestList) {
            return (CertDigestList) o;
        } else {
            return new CertDigestList(ASN1Sequence.getInstance(o));
        }
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vector = new ASN1EncodableVector();
        for (CertDigestObj certDigestObj : certDigestObjs) {
            vector.add(certDigestObj);
        }
        return new DERSequence(vector);
    }
}
