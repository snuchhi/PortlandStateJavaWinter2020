package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.ProjectXmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class XmlParser extends ProjectXmlHelper implements AirlineParser {
    private final String fileName;

    /** The System ID for the Family Tree DTD */
    protected static final String SYSTEM_ID =
            "http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd";

    /** The Public ID for the Family Tree DTD */
    protected static final String PUBLIC_ID =
            "-//Portland State University//DTD CS410J Airline//EN";

    /**
     * Constructor
     * @param fileName
     */
    public XmlParser(String fileName) {
        super(PUBLIC_ID, SYSTEM_ID, "airline.dtd");
        this.fileName = fileName;
    }



    @Override
    public AbstractAirline parse() throws ParserException {
        String airlineName = null;
        ArrayList<Flight> flightArrayList = new ArrayList<Flight>();
        try{
            File file = new File(this.fileName);
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            document.getDocumentElement().normalize();

            airlineName = document.getDocumentElement().getAttribute("Name");

            NodeList flightNodes = document.getElementsByTagName("flight");
            for(int i=0; i< flightNodes.getLength(); i++) {
                Node flightNode = flightNodes.item(i);

                if (flightNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element flightElement = (Element) flightNode;
                    String number = flightElement.getElementsByTagName("number").item(0).getTextContent();
                    String src = flightElement.getElementsByTagName("src").item(0).getTextContent();
                    String depart = flightElement.getElementsByTagName("depart").item(0).getTextContent();
                    String dest = flightElement.getElementsByTagName("dest").item(0).getTextContent();
                    String arrive = flightElement.getElementsByTagName("arrive").item(0).getTextContent();

                    Flight flight = new Flight(Integer.parseInt(number), src, depart, dest, arrive);
                    flightArrayList.add(flight);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ParserException("The XML is malformed");
        }
        return new Airline(airlineName, flightArrayList);
    }
}
