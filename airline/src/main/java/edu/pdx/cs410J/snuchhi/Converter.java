package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.IOException;

public class Converter {
    private final String textContent;
    private final String xmlContent;

    public Converter(String textContent, String xmlContent) {
        this.textContent = textContent;
        this.xmlContent = xmlContent;
    }

    public void convert() throws ParserException, IOException {
        TextParser textToConvert = new TextParser(this.textContent);
        Airline airline = (Airline) textToConvert.parse();

        XmlDumper airlineToDump = new XmlDumper(this.xmlContent);
        airlineToDump.dump(airline);
    }
}
