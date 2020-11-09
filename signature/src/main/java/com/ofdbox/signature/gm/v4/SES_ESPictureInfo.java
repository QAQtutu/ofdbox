package com.ofdbox.signature.gm.v4;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bouncycastle.asn1.*;

import java.util.Enumeration;

@Data
@AllArgsConstructor
public class SES_ESPictureInfo extends ASN1Object {
    private DERIA5String type;
    private ASN1OctetString data;
    private ASN1Integer width;
    private ASN1Integer height;

    private SES_ESPictureInfo(ASN1Sequence sequence){
        Enumeration<Object> emu = sequence.getObjects();
        this.type=DERIA5String.getInstance(emu.nextElement());
        this.data=ASN1OctetString.getInstance(emu.nextElement());
        this.width=ASN1Integer.getInstance(emu.nextElement());
        this.height=ASN1Integer.getInstance(emu.nextElement());
    }

    public static SES_ESPictureInfo getInstance(Object o) {
        if (o instanceof SES_ESPictureInfo) {
            return (SES_ESPictureInfo) o;
        } else {
            return new SES_ESPictureInfo(ASN1Sequence.getInstance(o));
        }
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vector = new ASN1EncodableVector();
        vector.add(type);
        vector.add(data);
        vector.add(width);
        vector.add(height);
        return new DERSequence(vector);
    }
}
