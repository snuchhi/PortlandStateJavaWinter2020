package edu.pdx.cs410J.snuchhi;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import static org.mockito.Mockito.mock;

/**
 * test the xml dumper
 */
public class XmlDumperTest {

    private XmlDumper createXmlDumper(){
        PrintWriter pw = mock(PrintWriter.class);
        return new XmlDumper(pw);
    }


    private Airline createAirline(String airlineName){
        Airline airline = new Airline(airlineName); // new Airline object
        return airline;
    }
    // return brand new flight
    private Flight createFlight(String flightNumber, String src, String depart, String dest, String arrive) throws ParseException {
        Flight flight = new Flight(Integer.parseInt(flightNumber), src, depart, dest, arrive);
        return flight;
    }


    @Test
    public void  createXmlFileAndAirlineWithOneFlight() throws IOException, ParseException {
        XmlDumper testXml = createXmlDumper();
        ArrayList<Flight> flightArrayList = new ArrayList<Flight>();
        Airline testAirline = createAirline("CS410J");
        testAirline.addFlight(createFlight("01", "PDX", "12/12/2020 10:00 PM", "LAX", "12/12/2020 11:00 PM"));
        testXml.dump(testAirline);
    }

    @Test
    public void  createXmlFileAndAirlineWithTwoFlights() throws IOException, ParseException {
        XmlDumper testXml = createXmlDumper();
        ArrayList<Flight> flightArrayList = new ArrayList<Flight>();
        Airline testAirline = createAirline("CS410J");
        testAirline.addFlight(createFlight( "42", "PDX", "12/12/2020 10:00 AM", "PDX", "12/12/2020 11:22 AM"));
        testAirline.addFlight(createFlight("41", "LAX", "10/10/2020 10:00 PM", "LAX", "10/10/2020 11:11 PM"));
        testXml.dump(testAirline);
    }

}
