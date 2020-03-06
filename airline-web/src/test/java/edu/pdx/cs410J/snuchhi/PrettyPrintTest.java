package edu.pdx.cs410J.snuchhi;

import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;

/**
 * test for pretty printing
 */
public class PrettyPrintTest {


    private Airline createAirlineWithFlights(String airlineName, String flightNumber, String src, String depart, String dest, String arrive) throws ParseException {
        Airline airline = new Airline(airlineName); // new Airline object
        Flight flight = new Flight(Integer.parseInt(flightNumber), src, depart, dest, arrive);
        airline.addFlight(flight); // add this newly created flight with initialized values into airline
        return airline;
    }


    private Flight createFlights(String flightNumber, String src, String depart, String dest, String arrive) throws ParseException {
        Flight flight = new Flight(Integer.parseInt(flightNumber), src, depart, dest, arrive);
        return flight;
    }

    @Test
    public void  testPrettyPrintAirlineDetails() throws IOException, ParseException {
        PrettyPrinter pretty = new PrettyPrinter();
        Airline exampleAirline = createAirlineWithFlights("Test", "41", "PDX", "10/10/2020 10:10 PM", "LAX", "10/10/2020 11:11 PM");
        pretty.dump(exampleAirline);
    }

    @Test(expected = IllegalArgumentException.class)
    public void  testPrintInvalidSRC() throws IOException, ParseException {
        PrettyPrinter pretty = new PrettyPrinter();
        Airline exampleAirline = createAirlineWithFlights("Test", "00", "XXX", "10/10/2020 10:11 PM", "LAX", "10/10/2020 11:11 PM");
        pretty.dump(exampleAirline);
    }

    @Test(expected = IllegalArgumentException.class)
    public void  testPrintInvalidDEST() throws IOException, ParseException {
        PrettyPrinter pretty = new PrettyPrinter();
        Airline exampleAirline = createAirlineWithFlights("TEST", "00", "PDX", "10/10/2020 10:11 PM", "XXX", "10/10/2020 11:11 PM");
        pretty.dump(exampleAirline);
    }


    @Test
    public void  testPrintSortName() throws IOException, ParseException {
        PrettyPrinter pretty = new PrettyPrinter();
        Airline exampleAirline = createAirlineWithFlights("Test", "01", "PDX", "10/10/2020 10:10 PM", "LAX", "10/10/2020 11:11 PM");
        exampleAirline.addFlight(createFlights("00", "ORD", "10/10/2020 10:10 PM", "LAX", "10/10/2020 11:11 PM"));
        pretty.dump(exampleAirline);
    }



}
