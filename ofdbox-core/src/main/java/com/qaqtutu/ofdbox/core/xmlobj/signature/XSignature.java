package com.qaqtutu.ofdbox.core.xmlobj.signature;

import com.qaqtutu.ofdbox.core.contance.Const;
import com.qaqtutu.ofdbox.core.utils.OfdXmlUtils;
import com.qaqtutu.ofdbox.core.xmlobj.adapter.StLocAdapter;
import com.qaqtutu.ofdbox.core.xmlobj.st.ST_Loc;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.ByteArrayInputStream;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "Signature",namespace = Const.NAMESPACE_URI)
public class XSignature {

    /*
    * 签名信息
    * */
    @NotNull
    @Valid
    @XmlElement(name = "SignedInfo",namespace = Const.NAMESPACE_URI)
    private NSignedInfo signedInfo;

    /*
    * 签名值文件
    * */
    @Valid
    @NotNull
    @XmlJavaTypeAdapter(value = StLocAdapter.class)
    @XmlElement(name = "SignedValue",namespace = Const.NAMESPACE_URI)
    private ST_Loc signedValue;


    public static void main(String[] args) {
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><ofd:Signature xmlns:ofd=\"http://www.ofdspec.org/2016\"><ofd:SignedInfo><ofd:Provider ProviderName=\"VGVzdA==\" Version=\"1\" Company=\"RzRC\" /><ofd:SignatureMethod>1.2.156.10197.1.501</ofd:SignatureMethod><ofd:SignatureDateTime>20200928165140Z</ofd:SignatureDateTime><ofd:References CheckMethod=\"1.2.156.10197.1.401\"> <Reference FileRef=\"/Doc_0/Document.xml\"><CheckValue>8leHbNmudyFqM7JqYVNn5NLkdY2ZMe5jjr9X+TKewbA=</CheckValue></Reference><Reference FileRef=\"/Doc_0/Pages/Page_0/Content.xml\"><CheckValue>5JttKNGiG/UyVR8PGGMRjU4vLUSbvcHCIm8gWRbPRNw=</CheckValue></Reference><Reference FileRef=\"/Doc_0/PublicRes.xml\"><CheckValue>2Io47ukhAMhlxfsQ5ZvNixC8u41tz5cMrtIERsdyDiM=</CheckValue></Reference></ofd:References><ofd:StampAnnot ID=\"1\" PageRef=\"1\" Boundary=\"80.95 144.95 49.30 49.30\" /></ofd:SignedInfo><ofd:SignedValue>SignatureValue.dat</ofd:SignedValue></ofd:Signature>";

        XSignature signature= OfdXmlUtils.toObject(new ByteArrayInputStream(xml.getBytes()),XSignature.class);
        System.out.println(signature);

        System.out.println(OfdXmlUtils.toXml(signature));
    }
}
