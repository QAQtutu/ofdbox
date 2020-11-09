package com.ofdbox.signature.gm;

import com.ofdbox.signature.gm.v4.SES_Signature;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Sequence;

import java.io.IOException;
import java.io.InputStream;

public class Gm {

    public static SES_Signature readV4(InputStream in) throws IOException {
        ASN1InputStream derInStream = new ASN1InputStream(in);
        ASN1Object obj = derInStream.readObject();
        ASN1Sequence asn1Sequence = (ASN1Sequence) obj;

        SES_Signature ses_signature = SES_Signature.getInstance(asn1Sequence);
        return ses_signature;
    }

    public static com.ofdbox.signature.gm.v1.SES_Signature readV1(InputStream in) throws IOException {
        ASN1InputStream derInStream = new ASN1InputStream(in);
        ASN1Object obj = derInStream.readObject();
        ASN1Sequence asn1Sequence = (ASN1Sequence) obj;

        com.ofdbox.signature.gm.v1.SES_Signature ses_signature = com.ofdbox.signature.gm.v1.SES_Signature.getInstance(asn1Sequence);
        return ses_signature;
    }

}
