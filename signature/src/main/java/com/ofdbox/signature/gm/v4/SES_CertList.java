package com.ofdbox.signature.gm.v4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.*;

import java.util.Enumeration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SES_CertList extends ASN1Object {

    private CertInfoList certInfoList;
    private CertDigestList certDigestList;

    private SES_CertList(ASN1Sequence sequence,int type){

        if(type==1){
            this.certInfoList=CertInfoList.getInstance(sequence);
        }else{
            this.certDigestList=CertDigestList.getInstance(sequence);
        }
    }

    public static SES_CertList getInstance(Object o,int type) {
        if (o instanceof SES_CertList) {
            return (SES_CertList) o;
        } else {
            return new SES_CertList(ASN1Sequence.getInstance(o),type);
        }
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vector = new ASN1EncodableVector();
        vector.add(certInfoList);
        vector.add(certDigestList);
        return new DERSequence(vector);
    }
}
