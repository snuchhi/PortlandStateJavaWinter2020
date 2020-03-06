package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirlineDumper;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class XmlDumper implements AirlineDumper<Airline> {
    private final PrintWriter pw;

    public XmlDumper(PrintWriter pw) {
        this.pw = pw;

    }

    @Override
    public void dump(Airline airline) throws IOException {

        Document doc = null;
        ArrayList<Flight> flightArray = (ArrayList<Flight>) airline.getFlights();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            DOMImplementation dom = documentBuilder.getDOMImplementation();
            DocumentType documentType = dom.createDocumentType("airline", AirlineXmlHelper.PUBLIC_ID, AirlineXmlHelper.SYSTEM_ID);
            doc = dom.createDocument(null, "airline", documentType);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        try {
            //Airline
            Element airlineRoot = doc.getDocumentElement();

            //AirlineName
            Element airlineName = doc.createElement("name");
            airlineName.appendChild(doc.createTextNode(airline.getName()));
            airlineRoot.appendChild(airlineName);

            //Flight
            for (Flight f : flightArray) {

                String departureDate = convertDate(f.getDepartureString().replace(",", ""));;
                String arrivalDate = convertDate(f.getArrivalString().replace(",", ""));

                //Flight
                Element flightRoot = doc.createElement("flight");
                airlineRoot.appendChild(flightRoot);

                //FlightNumber
                Element flightNumber = doc.createElement("number");
                flightNumber.appendChild(doc.createTextNode(String.valueOf(f.getNumber())));
                flightRoot.appendChild(flightNumber);

                //FlightSrc
                Element flightSrc = doc.createElement("src");
                flightSrc.appendChild(doc.createTextNode(f.getSource()));
                flightRoot.appendChild(flightSrc);

                //FlightDepart
                Element departRoot = doc.createElement("depart");
                appendDateTime(doc, departureDate, flightRoot, departRoot);

                //FlightDest
                Element flightDest = doc.createElement("dest");
                flightDest.appendChild(doc.createTextNode(f.getDestination()));
                flightRoot.appendChild(flightDest);

                //FlightArrival
                Element arriveRoot = doc.createElement("arrive");
                appendDateTime(doc, arrivalDate, flightRoot, arriveRoot);
            }

        } catch (DOMException e) {
            System.err.println("DTD is not correct.");
        }

        transformDoc(doc);


    }

    private String convertDate(String theDate) {
        DateFormat xmlFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String output = null;
        try{
            output = outputFormat.format(xmlFormat.parse(theDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }

    private void appendDateTime(Document doc, String dateString, Element flightRoot, Element dateRoot) {

        flightRoot.appendChild(dateRoot);

        Element departDate = doc.createElement("date");
        dateRoot.appendChild(departDate);

        Attr departDay = doc.createAttribute("day");
        departDay.setValue(dateString.substring(0,2));
        departDate.setAttributeNode(departDay);
        Attr departMonth = doc.createAttribute("month");
        departMonth.setValue(dateString.substring(3,5));
        departDate.setAttributeNode(departMonth);
        Attr departYear = doc.createAttribute("year");
        departYear.setValue(dateString.substring(6,10));
        departDate.setAttributeNode(departYear);

        Element departTime = doc.createElement("time");
        dateRoot.appendChild(departTime);

        Attr departHour = doc.createAttribute("hour");
        departHour.setValue(dateString.substring(11,13));
        departTime.setAttributeNode(departHour);
        Attr departMinute = doc.createAttribute("minute");
        departMinute.setValue(dateString.substring(14,16));
        departTime.setAttributeNode(departMinute);
    }

    public void dumpSearch(Airline airline, String src, String dest) throws IOException {

        Document doc = null;
        ArrayList<Flight> flightArray = (ArrayList<Flight>) airline.getFlights();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            DOMImplementation dom = documentBuilder.getDOMImplementation();
            DocumentType documentType = dom.createDocumentType("airline", AirlineXmlHelper.PUBLIC_ID, AirlineXmlHelper.SYSTEM_ID);
            doc = dom.createDocument(null, "airline", documentType);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        try {
            //Airline
            Element airlineRoot = doc.getDocumentElement();

            //AirlineName
            Element airlineName = doc.createElement("name");
            airlineName.appendChild(doc.createTextNode(airline.getName()));
            airlineRoot.appendChild(airlineName);

            //Flight
            for (Flight f : flightArray) {
                if (f.getSource().equals(src) && f.getDestination().equals(dest)) {

                    String departureDate = convertDate(f.getDepartureString().replace(",", ""));
                    String arrivalDate = convertDate(f.getArrivalString().replace(",", ""));

                    //Flight
                    Element flightRoot = doc.createElement("flight");
                    airlineRoot.appendChild(flightRoot);

                    //FlightNumber
                    Element flightNumber = doc.createElement("number");
                    flightNumber.appendChild(doc.createTextNode(String.valueOf(f.getNumber())));
                    flightRoot.appendChild(flightNumber);

                    //FlightSrc
                    Element flightSrc = doc.createElement("src");
                    flightSrc.appendChild(doc.createTextNode(f.getSource()));
                    flightRoot.appendChild(flightSrc);

                    //FlightDepart
                    Element departRoot = doc.createElement("depart");
                    appendDateTime(doc, departureDate, flightRoot, departRoot);

                    //FlightDest
                    Element flightDest = doc.createElement("dest");
                    flightDest.appendChild(doc.createTextNode(f.getDestination()));
                    flightRoot.appendChild(flightDest);

                    //FlightArrival
                    Element arriveRoot = doc.createElement("arrive");
                    appendDateTime(doc, arrivalDate, flightRoot, arriveRoot);
                }
            }

        } catch (DOMException e) {
            System.err.println("DTD is not correct.");
        }

        transformDoc(doc);


    }

    private void transformDoc(Document doc) {
        try {
            Source source = new DOMSource(doc);
            Result result = new StreamResult(pw);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, AirlineXmlHelper.SYSTEM_ID);
            transformer.transform(source, result);

        } catch (TransformerException ex) {
            System.err.println("Error in xml dumper...");
        }
    }
}