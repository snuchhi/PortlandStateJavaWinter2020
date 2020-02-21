package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class ConverterTest {
    private XmlDumper createXmlDumper(String content) {
        return new XmlDumper(content);
    }
    private XmlParser createXmlParser(String content) {
        return new XmlParser(content);
    }
    private TextDumper createTextDumper(String content) {
        return new TextDumper(content);
    }
    private TextParser createTextParser(String content) {
        return new TextParser(content);
    }

    private Airline createAirline(String airlineName, ArrayList<Flight> flightArray){
        Airline airline = new Airline(airlineName, flightArray); // new Airline object
        return airline;
    }

    private Flight createFlight(String flightNumber, String src, String depart, String dest, String arrive){
        Flight flight = new Flight(Integer.parseInt(flightNumber), src, depart, dest, arrive);
        return flight;
    }

    @Test
    public void  createFileAndAirlineWithOneFlight() throws IOException, ParserException {
        XmlDumper exampleDump = createXmlDumper("Test.txt");
        ArrayList<Flight> flightArray = new ArrayList<Flight>();
        Airline exampleAirline = createAirline("CS410J", flightArray);
        exampleAirline.addFlight(createFlight("01", "PDX", "12/12/2020 10:00 PM", "LAX", "12/12/2020 11:00 PM"));
        exampleDump.dump(exampleAirline);

        Converter toConvert = new Converter("Test.txt", "Test.xml");
        toConvert.convert();
    }

    @Test
    public void  createFileAndAirlineWithTwoFlights() throws IOException, ParserException {
        XmlDumper exampleXml = createXmlDumper("Test1.txt");
        ArrayList<Flight> flightArray = new ArrayList<Flight>();
        Airline exampleAirline = createAirline("CS410J", flightArray);
        exampleAirline.addFlight(createFlight("42", "PDX", "12/12/2020 10:00 AM", "PDX", "12/12/2020 11:22 AM"));
        exampleAirline.addFlight(createFlight("41", "LAX", "10/10/2020 10:00 PM", "LAX", "10/10/2020 11:11 PM"));
        exampleXml.dump(exampleAirline);

        Converter toConvert = new Converter("Test1.txt", "Test1.xml");
        toConvert.convert();
    }
}
