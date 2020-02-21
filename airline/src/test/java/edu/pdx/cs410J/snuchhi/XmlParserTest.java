package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class XmlParserTest {
    private Airline createAirline(String airlineName, ArrayList<Flight> flightArrayList){
        Airline airline = new Airline(airlineName, flightArrayList); // new Airline object
        return airline;
    }

    private Flight createFlight(String flightNumber, String src, String depart, String dest, String arrive){
        Flight flight = new Flight(Integer.parseInt(flightNumber), src, depart, dest, arrive);
        return flight;
    }

    private XmlDumper createXmlDumper(String fileName){
        return new XmlDumper(fileName);
    }

    private XmlParser createXmlParser(String fileName) {
        return new XmlParser(fileName);
    }

    @Test
    public void  createFileAndAirlineWithOneFlight() throws IOException, ParserException {
        XmlDumper exampleDump = createXmlDumper("Testparse1.xml");
        ArrayList<Flight> flightArray = new ArrayList<Flight>();
        Airline exampleAirline = createAirline("CS410J", flightArray);
        exampleAirline.addFlight(createFlight("01", "PDX", "12/12/2020 10:00 PM", "LAX", "12/12/2020 11:00 PM"));
        exampleDump.dump(exampleAirline);

        XmlParser exampleParse = createXmlParser("Testparse1.xml");
        Airline exampleAirlineP = (Airline) exampleParse.parse();
        exampleAirlineP.printAirlineName();
        exampleAirlineP.printFlights();
    }

    @Test
    public void  createFileAndAirlineWithTwoFlight() throws IOException, ParserException {
        XmlDumper exampleXml = createXmlDumper("Testparse2.xml");
        ArrayList<Flight> flightArray = new ArrayList<Flight>();
        Airline exampleAirline = createAirline("CS410J", flightArray);
        exampleAirline.addFlight(createFlight("42", "PDX", "12/12/2020 10:00 AM", "PDX", "12/12/2020 11:22 AM"));
        exampleAirline.addFlight(createFlight("41", "LAX", "10/10/2020 10:00 PM", "LAX", "10/10/2020 11:11 PM"));
        exampleXml.dump(exampleAirline);

        XmlParser exampleParse = createXmlParser("Testparse2.xml");
        Airline exampleAirlineP = (Airline) exampleParse.parse();
        exampleAirlineP.printAirlineName();
        exampleAirlineP.printFlights();
    }

}
