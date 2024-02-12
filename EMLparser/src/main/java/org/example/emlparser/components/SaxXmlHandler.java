package org.example.emlparser.components;

import org.example.emlparser.data.entity.CastObce;
import org.example.emlparser.data.entity.Obec;
import org.example.emlparser.data.repository.CastObceRepository;
import org.example.emlparser.data.repository.ObecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

@Component
public class SaxXmlHandler extends DefaultHandler {

    @Autowired
    private ObecRepository obecRepository;
    @Autowired
    private CastObceRepository castObceRepository;

    private Obec currentObec;
    private CastObce currentCastObce;
    private boolean isObecKodElement = false;
    private StringBuilder elementValue;
    private boolean isInsideObec = false;
    private boolean isInsideCastObce = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        elementValue = new StringBuilder();
        if (qName.equalsIgnoreCase("vf:Obec")) {
            currentObec = new Obec();
            isInsideObec = true;
            isInsideCastObce = false;
        } else if (qName.equalsIgnoreCase("vf:CastObce")) {
            currentCastObce = new CastObce();
            isInsideCastObce = true;
            isInsideObec = false;
            isObecKodElement = false;
        } else if (qName.equalsIgnoreCase("coi:Obec")) {
            isObecKodElement = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName.toLowerCase()) {
            case "obi:kod":
                if (isInsideObec && !isObecKodElement) {
                    currentObec.setKod(elementValue.toString());
                } else if (isInsideCastObce && isObecKodElement) {
                    Obec linkedObec = obecRepository.findById(elementValue.toString())
                            .orElseThrow(() -> new RuntimeException("Obec not found: " + elementValue.toString()));
                    currentCastObce.setKodObce(linkedObec);
                }
                break;
            case "obi:nazev":
                if (isInsideObec) currentObec.setNazev(elementValue.toString());
                break;
            case "coi:nazev":
                if (isInsideCastObce) currentCastObce.setNazev(elementValue.toString());
                break;
            case "coi:kod":
                if (isInsideCastObce) currentCastObce.setKod(elementValue.toString());
                break;
            case "vf:obec":
                obecRepository.save(currentObec);
                currentObec = null;
                isInsideObec = false;
                break;
            case "vf:castobce":
                castObceRepository.save(currentCastObce);
                currentCastObce = null;
                isInsideCastObce = false;
                break;
        }
    }


    @Override
    public void characters(char[] ch, int start, int length) {
        elementValue.append(new String(ch, start, length));
    }
}



