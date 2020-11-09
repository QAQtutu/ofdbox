package com.ofdbox.signature.gm.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bouncycastle.asn1.*;

import java.util.Enumeration;

@Data
@AllArgsConstructor
public class TBS_Sign extends ASN1Object {

        private ASN1Integer version;
        private SESeal eseal;
        private DERBitString timeInfo;
        private DERBitString dataHash;
        private DERIA5String propertyInfo;
        private ASN1OctetString cert;
        private ASN1ObjectIdentifier signatureAlgorithm;

        protected TBS_Sign(ASN1Sequence sequence) {
            Enumeration<Object> emu = sequence.getObjects();
            this.version = ASN1Integer.getInstance(emu.nextElement());
            this.eseal = SESeal.getInstance(emu.nextElement());
            this.timeInfo = DERBitString.getInstance(emu.nextElement());
            this.dataHash = DERBitString.getInstance(emu.nextElement());
            this.propertyInfo = DERIA5String.getInstance(emu.nextElement());
            this.cert = ASN1OctetString.getInstance(emu.nextElement());
            if(emu.hasMoreElements()){
                this.signatureAlgorithm= ASN1ObjectIdentifier.getInstance(emu.nextElement());
            }
        }

        public static TBS_Sign getInstance(Object o) {
            if (o instanceof TBS_Sign) {
                return (TBS_Sign) o;
            } else {
                return new TBS_Sign(ASN1Sequence.getInstance(o));
            }
        }

        @Override
        public ASN1Primitive toASN1Primitive() {
            ASN1EncodableVector vector = new ASN1EncodableVector();
            vector.add(version);
            return new DERSequence(vector);
        }
    }
