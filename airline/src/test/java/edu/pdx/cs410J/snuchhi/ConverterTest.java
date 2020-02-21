package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class ConverterTest {

    private Airline createAirline(String airlineName, ArrayList<Flight> flightArray){
        Airline airline = new Airline(airlineName, flightArray); // new Airline object
        return airline;
    }

    private Flight createFlight(String flightNumber, String src, String depart, String dest, String arrive){
        Flight flight = new Flight(Integer.parseInt(flightNumber), src, depart, dest, arrive);
        return flight;
    }
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



    @Test
    public void  createFileAndAirlineWithOneFlight() throws IOException, ParserException {
        TextDumper Dump = createTextDumper("TestConvert.txt");
        ArrayList<Flight> flightArray = new ArrayList<Flight>();
        Airline exampleAirlineD = createAirline("CS410J", flightArray);
        exampleAirlineD.addFlight(createFlight("41", "PDX", "10/10/2020 10:10 PM", "LAX", "10/10/2020 11:11 PM"));
        Dump.dump(exampleAirlineD);

        Converter toConvert = new Converter("TestConvert.txt", "TestConvert.xml");
        toConvert.convert();
    }

    @Test
    public void  createFileAndAirlineWithTwoFlights() throws IOException, ParserException {
        TextDumper exampleDump = createTextDumper("TestConvert2.txt");
        ArrayList<Flight> flightArray = new ArrayList<Flight>();
        Airline exampleAirlineD = createAirline("CS410J", flightArray);
        exampleAirlineD.addFlight(createFlight("41", "PDX", "10/10/2020 10:10 PM", "LAX", "10/10/2020 11:11 PM"));
        exampleAirlineD.addFlight(createFlight("42", "LAX", "10/10/2020 11:11 AM", "PDX", "10/10/2020 12:12 PM"));
        exampleDump.dump(exampleAirlineD);

        Converter toConvert = new Converter("TestConvert2.txt", "TestConvert2.xml");
        toConvert.convert();

    }
}
