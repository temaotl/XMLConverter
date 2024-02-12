package org.example.xmlparser.xmlParser;

import org.example.xmlparser.components.SaxXmlHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

@Service
public class XmlParserService {

    @Autowired
    private SaxXmlHandler saxXmlHandler;

    public void parseXmlFile(String filePath) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(saxXmlHandler);
            xmlReader.parse(new InputSource(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

