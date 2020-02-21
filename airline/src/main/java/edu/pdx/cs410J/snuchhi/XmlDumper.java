package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirlineDumper;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlDumper implements AirlineDumper {

    public String fileName;

    /**
     * constructor
     *
     * @param fileName - name of the file
     */
    public XmlDumper(String fileName) {

        //  if(!fileName.matches("([a-z]|[A-Z]|[0-9]|[.])*")){
        //    throw new IllegalArgumentException("File name is invalid, cannot dump");
        //  }
        this.fileName = fileName;
    }

    @Override
    public void dump(AbstractAirline abstractAirline) throws IOException {

        ArrayList<Flight> flightArray = (ArrayList<Flight>) abstractAirline.getFlights();

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            //Airline
            Element airlineRoot = document.createElement("Airline");
            document.appendChild(airlineRoot);
            //AirlineName
            Attr airlineName = document.createAttribute("Name");
            airlineName.setValue(abstractAirline.getName());
            airlineRoot.setAttributeNode(airlineName);

            //Insert the details of flight
            for(Flight f : flightArray) {
                String departureDate = f.getDepartureString().replace(",", "");
                String arrivalDate = f.getArrivalString().replace(",", "");
                String num = String.valueOf(f.getNumber());

                //Flight
                Element flightRoot = document.createElement("Flight");
                airlineRoot.appendChild(flightRoot);
                //FlightNumber
                Attr flightNumber = document.createAttribute("Number");
                flightNumber.setValue(num);
                flightRoot.setAttributeNode(flightNumber);
                //Source
                Element flightSrc = document.createElement("SRC");
                flightSrc.appendChild(document.createTextNode(f.getSource()));
                flightRoot.appendChild(flightSrc);
                //Departure
                Element flightDepart = document.createElement("Departure");
                flightDepart.appendChild(document.createTextNode(departureDate));
                flightRoot.appendChild(flightDepart);
                //Destination
                Element flightDest = document.createElement("DEST");
                flightDest.appendChild(document.createTextNode(f.getDestination()));
                flightRoot.appendChild(flightDest);
                //Arrival
                Element flightArrival = document.createElement("Arrival");
                flightArrival.appendChild(document.createTextNode(arrivalDate));
                flightRoot.appendChild(flightArrival);
            }
            //create XML and transform DOM to XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(this.fileName));

            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException e) {
            System.out.println(e);
        }
    }
}
