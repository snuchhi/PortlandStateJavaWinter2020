package edu.pdx.cs410J.snuchhi;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XmlDumperTest {

    private Airline createAirline(String airlineName, ArrayList<Flight> flightArrayList){
        Airline airline = new Airline(airlineName, flightArrayList); // new Airline object
        return airline;
    }

    private Flight createFlight(String flightNumber, String src, String depart, String dest, String arrive){
        Flight flight = new Flight(Integer.parseInt(flightNumber), src, depart, dest, arrive);
        return flight;
    }

    private XmlDumper createXmlDumper(String content){
        return new XmlDumper(content);
    }

    @Test
    public void  createFileAndAirlineWithOneFlight() throws IOException {
        XmlDumper exampleXml = createXmlDumper("Test.xml");
        ArrayList<Flight> flightArray = new ArrayList<Flight>();
        Airline exampleAirline = createAirline("CS410J", flightArray);
        exampleAirline.addFlight(createFlight("01", "PDX", "12/12/2020 10:00 PM", "LAX", "12/12/2020 11:00 PM"));
        exampleXml.dump(exampleAirline);
    }

    @Test
    public void  createFileAndAirlineWithTwoFlight() throws IOException {
        XmlDumper exampleXml = createXmlDumper("Test1.xml");
        ArrayList<Flight> flightArray = new ArrayList<Flight>();
        Airline exampleAirline = createAirline("CS410J", flightArray);
        exampleAirline.addFlight(createFlight( "42", "PDX", "12/12/2020 10:00 AM", "PDX", "12/12/2020 11:22 am"));
        exampleAirline.addFlight(createFlight("41", "LAX", "10/10/2020 10:00 PM", "LAX", "10/10/2020 11:11 PM"));
        exampleXml.dump(exampleAirline);
    }

}
