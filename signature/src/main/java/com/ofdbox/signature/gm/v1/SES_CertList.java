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
public class SES_CertList extends ASN1Object {

    //    private List<DERIA5String> certs;
    private ASN1Sequence certs;

    private SES_CertList(ASN1Sequence sequence) {
//        certs=new ArrayList<>();
//        Enumeration<Object> emu = sequence.getObjects();
//        while (emu.hasMoreElements()){
//            certs.add(DERIA5String.getInstance(emu.nextElement()));
//        }
        certs = sequence;
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
//        for(DERIA5String cert:certs){
//            vector.add(cert);
//        }
        vector.add(this.certs);
        return new DERSequence(vector);
    }
}
