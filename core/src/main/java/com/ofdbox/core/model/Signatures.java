package com.ofdbox.core.model;

import com.ofdbox.core.xmlobj.signature.NSignature;
import com.ofdbox.core.xmlobj.signature.XSignature;
import com.ofdbox.core.xmlobj.signature.XSignatures;
import lombok.Data;

import java.util.List;

@Data
public class Signatures {
    private XSignatures xSignatures;
    private List<Signature> signatureList;

    @Data
    public  static class Signature{
        private NSignature nSignature;
        private XSignature xSignature;
    }
}
