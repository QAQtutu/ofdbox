package com.ofdbox.signature.gm.v4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.*;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.Enumeration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertDigestObj extends ASN1Object {

    private DERPrintableString objType;
    private ASN1OctetString value;

    private CertDigestObj(ASN1Sequence sequence){
        Enumeration<Object> emu = sequence.getObjects();
        this.objType=DERPrintableString.getInstance(emu.nextElement());
        this.value=ASN1OctetString.getInstance(emu.nextElement());
    }

    public static CertDigestObj getInstance(Object o) {
        if (o instanceof CertDigestObj) {
            return (CertDigestObj) o;
        } else {
            return new CertDigestObj(ASN1Sequence.getInstance(o));
        }
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vector = new ASN1EncodableVector();
        vector.add(objType);
        vector.add(value);
        return new DERSequence(vector);
    }
}
