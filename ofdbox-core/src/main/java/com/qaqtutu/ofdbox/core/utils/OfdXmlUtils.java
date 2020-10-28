package com.qaqtutu.ofdbox.core.utils;

import com.qaqtutu.ofdbox.core.contance.Const;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import java.io.*;

/**
 * @Description: xml工具
 * @Author: 张家尧
 * @date: 2020/10/1 9:54
 */
public class OfdXmlUtils {

    /**
     * @Description: ofd内建对象转xml
     * @Param: [obj] 要转换的对象
     * @return: java.lang.String
     * @Author: 张家尧
     * @date: 2020/10/1 9:53
     */
    public static String toXml(Object obj) {
        StringWriter sw = new StringWriter();
        sw.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
                @Override
                public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
                    if (namespaceUri.equals(Const.NAMESPACE_URI)) return Const.NAMESPACE_PREFIX;
                    return suggestion;
                }
            });
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    /**
     * @Description: xml转ofd内建对象
     * @Param: xml xml文本
     * @Param: clazz 目标类对象
     * @return: T
     * @Author: 张家尧
     * @date: 2020/10/1 9:54
     */

    public static <T> T toObject(InputStream in, Class<T> clazz) {
        return toObject(in, clazz, false);
    }

    public static <T> T toObject(InputStream in, Class<T> clazz, boolean ignoreNamespace) {
        Object xmlObject = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            SAXParserFactory sax = SAXParserFactory.newInstance();
            sax.setNamespaceAware(false);
            XMLReader xmlReader = sax.newSAXParser().getXMLReader();

            Source source = new SAXSource(xmlReader, new InputSource(in));
            xmlObject = unmarshaller.unmarshal(source);

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return clazz.cast(xmlObject);
    }

}
