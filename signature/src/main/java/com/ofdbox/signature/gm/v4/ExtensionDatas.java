package com.ofdbox.signature.gm.v4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Data
@AllArgsConstructor
public class ExtensionDatas extends ASN1Object {

    private List<ExtData> extDatas=new ArrayList<>();

    protected ExtensionDatas(ASN1Sequence sequence){
        Enumeration<Object> emu = sequence.getObjects();
        while (emu.hasMoreElements()){
            extDatas.add(ExtData.getInstance(emu.nextElement()));
        }
    }
    public static ExtensionDatas getInstance(Object o){
        if(o instanceof ExtensionDatas){
            return (ExtensionDatas)o;
        }else{
            return new ExtensionDatas(ASN1Sequence.getInstance(o));
        }
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vector = new ASN1EncodableVector();
        for(ExtData extData:extDatas){
            vector.add(extData);
        }
        return new DERSequence(vector);
    }
}
