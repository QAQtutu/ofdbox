package com.ofdbox.signature.gm.v1;

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
public class SES_CertList extends ASN1Object {

    private List<ASN1OctetString> certs;

    private SES_CertList(ASN1Sequence sequence){
        certs=new ArrayList<>();
        Enumeration<Object> emu = sequence.getObjects();
        while (emu.hasMoreElements()){
            certs.add(ASN1OctetString.getInstance(emu.nextElement()));
        }
    }

    public static SES_CertList getInstance(Object o) {
        if (o instanceof SES_CertList) {
            return (SES_CertList) o;
        } else {
            return new SES_CertList(ASN1Sequence.getInstance(o));
        }
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vector = new ASN1EncodableVector();
        for(ASN1OctetString cert:certs){
            vector.add(cert);
        }
        return new DERSequence(vector);
    }
}
