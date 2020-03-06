package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * xmlparser implements airlineparser to parse the airline contents in xml format
 */
public class XmlParser implements AirlineParser {
    private final String xmlFile;
    public XmlParser(String xml) {
        this.xmlFile = xml;
    }

    @Override
    public AbstractAirline parse() throws ParserException {

        String[] commandArgs = new String[13];
        String departureDateTime = null;
        String arrivalDateTime = null;
        Airline airline = null;

        try{
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            InputSource input = new InputSource();
            input.setCharacterStream(new StringReader(this.xmlFile));
            Document document = documentBuilder.parse(input);

            document.getDocumentElement().normalize();

            airline = new Airline(document.getElementsByTagName("name").item(0).getTextContent());

            NodeList fList = document.getElementsByTagName("flight");
            for (int i = 0; i < fList.getLength(); i++){
                Node fNode = fList.item(i);
                if (fNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) fNode;
                    commandArgs[0] = eElement.getElementsByTagName("number").item(0).getTextContent();
                    commandArgs[1] = eElement.getElementsByTagName("src").item(0).getTextContent();

                    NodeList dList = document.getElementsByTagName("depart");
                    departureDateTime = getString(commandArgs, departureDateTime, dList);

                    commandArgs[7] = eElement.getElementsByTagName("dest").item(0).getTextContent();

                    NodeList aList = document.getElementsByTagName("arrive");
                    arrivalDateTime = getString(commandArgs, arrivalDateTime, aList);
                }
                Flight flight = new Flight(Integer.parseInt(commandArgs[0]), commandArgs[1], departureDateTime, commandArgs[7], arrivalDateTime);
                airline.addFlight(flight);
            }
        } catch (Exception e) {
            System.err.println("FILE DOES NOT CONFORM TO CORRECT DTD.");
            throw new IllegalArgumentException();
        }

        return airline;
    }

    private String getString(String[] commandArgs, String dateTime, NodeList list) {
        for (int j = 0; j < list.getLength(); j++) {
            Element dElement = (Element) list.item(j);

            Element date = (Element) dElement.getElementsByTagName("date").item(0);
            commandArgs[2] = date.getAttribute("day");
            commandArgs[3] = date.getAttribute("month");
            commandArgs[4] = date.getAttribute("year");

            Element time = (Element) dElement.getElementsByTagName("time").item(0);
            commandArgs[5] = time.getAttribute("hour");
            commandArgs[6] = time.getAttribute("minute");

            dateTime = commandArgs[2] + "/" + commandArgs[3] + "/" + commandArgs[4] +
                    " " + commandArgs[5] + ":" + commandArgs[6];
            dateTime = convertDate(dateTime);
        }
        return dateTime;
    }

    private String convertDate(String departureDate) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        String output = null;
        try{
            output = outputFormat.format(df.parse(departureDate));
        } catch (ParseException e) {
            System.err.println("parse error");
        }
        return output;
    }
}