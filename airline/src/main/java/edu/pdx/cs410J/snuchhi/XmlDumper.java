package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractAirline;

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


public class XmlDumper implements AirlineDumper {

    public final String fileName;

    public XmlDumper(String fileName) {
        this.fileName = fileName;
    }

    /**
     *
     * @param airline
     * @throws IOException
     */
    @Override
    public void dump(AbstractAirline airline) throws IOException {
        Document document = null;
        ArrayList<Flight> flightArray = (ArrayList<Flight>) airline.getFlights();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation dom = builder.getDOMImplementation();
            DocumentType docType = dom.createDocumentType("airline", AirlineXmlHelper.PUBLIC_ID, AirlineXmlHelper.SYSTEM_ID);
            document = dom.createDocument(null, "airline", docType);
        } catch (ParserConfigurationException e) {
            System.out.println(e);
        }

            //Airline
            Element airlineRoot = document.getDocumentElement();
            Element airlineName = document.createElement("name");
            airlineName.appendChild(document.createTextNode(airline.getName()));
            airlineRoot.appendChild(airlineName);

            //Flight
            for (Flight f : flightArray) {
                String departureDate = f.getLongDeparture().replace(",", "");
                departureDate = convertDate(departureDate);

                String arrivalDate = f.getLongArrival().replace(",", "");
                arrivalDate = convertDate(arrivalDate);

                String num = String.valueOf(f.getNumber());

                //Flight
                Element flightRoot = document.createElement("flight");
                airlineRoot.appendChild(flightRoot);

                //FlightNumber
                Element flightNumber = document.createElement("number");
                flightNumber.appendChild(document.createTextNode(num));
                flightRoot.appendChild(flightNumber);

                //FlightSrc
                Element flightSrc = document.createElement("src");
                flightSrc.appendChild(document.createTextNode(f.getSource()));
                flightRoot.appendChild(flightSrc);

                //FlightDepart
                Element departRoot = document.createElement("depart");
                addDate(document, departureDate, flightRoot, departRoot);

                //FlightDest
                Element flightDest = document.createElement("dest");
                flightDest.appendChild(document.createTextNode(f.getDestination()));
                flightRoot.appendChild(flightDest);

                //FlightArrival
                Element arriveRoot = document.createElement("arrive");
                addDate(document, arrivalDate, flightRoot, arriveRoot);
            }

        try {
            Source src = new DOMSource(document);
            TransformerFactory xFactory = TransformerFactory.newInstance();
            Transformer xForm = xFactory.newTransformer();
            xForm.setOutputProperty(OutputKeys.INDENT, "yes");
            xForm.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, AirlineXmlHelper.SYSTEM_ID);
            StreamResult streamResult = new StreamResult(new File(this.fileName));
            xForm.transform(src, streamResult);
        } catch (TransformerException ex) {
            System.err.println("xml dumper error");
        }

    }

    private String convertDate(String departureDate) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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

    private void addDate(Document doc, String dateString, Element flightRoot, Element dateRoot) {
        flightRoot.appendChild(dateRoot);

        Element departarriveDate = doc.createElement("date");
        dateRoot.appendChild(departarriveDate);

        Attr departarriveDay = doc.createAttribute("day");
        departarriveDay.setValue(dateString.substring(0,2));
        departarriveDate.setAttributeNode(departarriveDay);
        Attr departarriveMonth = doc.createAttribute("month");
        departarriveMonth.setValue(dateString.substring(3,5));
        departarriveDate.setAttributeNode(departarriveMonth);
        Attr departarriveYear = doc.createAttribute("year");
        departarriveYear.setValue(dateString.substring(6,10));
        departarriveDate.setAttributeNode(departarriveYear);

        Element departarriveTime = doc.createElement("time");
        dateRoot.appendChild(departarriveTime);

        Attr departarriveHour = doc.createAttribute("hour");
        departarriveHour.setValue(dateString.substring(11,13));
        departarriveTime.setAttributeNode(departarriveHour);
        Attr departMinute = doc.createAttribute("minute");
        departMinute.setValue(dateString.substring(14,16));
        departarriveTime.setAttributeNode(departMinute);
    }


}