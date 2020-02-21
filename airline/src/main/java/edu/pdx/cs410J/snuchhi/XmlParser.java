package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class XmlParser implements AirlineParser {
    private final String fileName;

    public XmlParser(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public AbstractAirline parse() throws ParserException {
        String[] newCommandArgs = new String[13];
        String airlineName = null;
        String departureDateTime = null;
        String arrivalDateTime = null;
        ArrayList<Flight> flightArray = new ArrayList<Flight>();


        try{
            File file = new File(this.fileName);
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            document.getDocumentElement().normalize();

            airlineName = document.getElementsByTagName("name").item(0).getTextContent();

            NodeList fList = document.getElementsByTagName("flight");
            for (int i = 0; i < fList.getLength(); i++){
                Node fNode = fList.item(i);
                if (fNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) fNode;
                    newCommandArgs[0] = eElement.getElementsByTagName("number").item(0).getTextContent();
                    newCommandArgs[1] = eElement.getElementsByTagName("src").item(0).getTextContent();

                    NodeList dList = document.getElementsByTagName("depart");
                    departureDateTime = getDate(newCommandArgs, departureDateTime, dList);

                    newCommandArgs[7] = eElement.getElementsByTagName("dest").item(0).getTextContent();

                    NodeList aList = document.getElementsByTagName("arrive");
                    arrivalDateTime = getDate(newCommandArgs, arrivalDateTime, aList);
                }
                Flight flight = new Flight(Integer.parseInt(newCommandArgs[0]), newCommandArgs[1], departureDateTime, newCommandArgs[7], arrivalDateTime);
                flightArray.add(flight);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return new Airline(airlineName, flightArray);
    }

    private String convertDate(String departureDate) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        Date date = null;
        String output = null;
        try{
            date = df.parse(departureDate);
            output = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }

    private String getDate(String[] newCommandArgs, String dateTime, NodeList list) {
        for (int j = 0; j < list.getLength(); j++) {
            Element dElement = (Element) list.item(j);

            Element dDate = (Element) dElement.getElementsByTagName("date").item(0);
            newCommandArgs[2] = dDate.getAttribute("day");
            newCommandArgs[3] = dDate.getAttribute("month");
            newCommandArgs[4] = dDate.getAttribute("year");

            Element dTime = (Element) dElement.getElementsByTagName("time").item(0);
            newCommandArgs[5] = dTime.getAttribute("hour");
            newCommandArgs[6] = dTime.getAttribute("minute");

            dateTime = newCommandArgs[2] + "/" + newCommandArgs[3] + "/" + newCommandArgs[4] +
                    " " + newCommandArgs[5] + ":" + newCommandArgs[6];
            dateTime = convertDate(dateTime);
        }
        return dateTime;
    }


    
}