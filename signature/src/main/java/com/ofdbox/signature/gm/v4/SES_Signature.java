package com.ofdbox.signature.gm.v4;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bouncycastle.asn1.*;

import java.util.Enumeration;

@Data
@AllArgsConstructor
public class SES_Signature extends ASN1Object {

    private TBS_Sign toSign;
    private ASN1OctetString cert;
    private ASN1ObjectIdentifier signatureAlgId;
    private DERBitString signature;
    private DERBitString timeStamp;


    protected SES_Signature(ASN1Sequence sequence) {
        Enumeration<Object> emu = sequence.getObjects();
        this.toSign = TBS_Sign.getInstance(emu.nextElement());
        this.cert = ASN1OctetString.getInstance(emu.nextElement());
        this.signatureAlgId = ASN1ObjectIdentifier.getInstance(emu.nextElement());
        this.signature = DERBitString.getInstance(emu.nextElement());

        if(emu.hasMoreElements()){
            this.timeStamp= DERBitString.getInstance(emu.nextElement());
        }
    }

    public static SES_Signature getInstance(Object o) {
        if (o instanceof SES_Signature) {
            return (SES_Signature) o;
        } else {
            return new SES_Signature(ASN1Sequence.getInstance(o));
        }
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vector = new ASN1EncodableVector();
        vector.add(toSign);
        vector.add(cert);
        vector.add(signatureAlgId);
        vector.add(signature);
        if(timeStamp!=null){
            vector.add(timeStamp);
        }
        return new DERSequence(vector);
    }
}
