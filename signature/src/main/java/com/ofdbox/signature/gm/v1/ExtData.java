package com.ofdbox.signature.gm.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bouncycastle.asn1.*;

import java.util.Enumeration;

@Data
@AllArgsConstructor
public class ExtData extends ASN1Object {
    private ASN1ObjectIdentifier extnId;
    private ASN1Boolean critical;
    private ASN1OctetString extnValue;

    private ExtData(ASN1Sequence sequence) {
        Enumeration<Object> emu = sequence.getObjects();
        extnId=ASN1ObjectIdentifier.getInstance(emu.nextElement());
        critical=ASN1Boolean.getInstance(emu.nextElement());
        extnValue=ASN1OctetString.getInstance(emu.nextElement());
    }

    public static ExtData getInstance(Object o) {
        if (o instanceof ExtData) {
            return (ExtData) o;
        } else {
            return new ExtData(ASN1Sequence.getInstance(o));
        }
    }


    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vector = new ASN1EncodableVector();
        vector.add(extnId);
        vector.add(critical);
        vector.add(extnValue);
        return new DERSequence(vector);
    }
}
