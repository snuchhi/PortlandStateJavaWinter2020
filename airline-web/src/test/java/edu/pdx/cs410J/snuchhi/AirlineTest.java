package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractFlight;
import org.junit.Test;

import java.util.ArrayList;

public class AirlineTest {
    @Test
    public void createAirline() {
        Airline airline = new Airline("Test");
    }

    private Airline createAirlineFlightList(ArrayList<AbstractFlight> flightArrayListList){
        return new Airline("Test");
    }
    @Test
    public void checkForProperFlight(){
        Flight flight = new Flight(00, "pdx", "12/12/2020 10:00 AM", "lax", "12/12/2020 11:00 AM");
        ArrayList<AbstractFlight> flightArrayList = new ArrayList<AbstractFlight>();
        Airline airline = createAirlineFlightList(flightArrayList);
        airline.addFlight(flight);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkForInvalidSrcInFlight(){
        Flight flight = new Flight(00, "invalid", "12/12/2020 10:00 AM", "lax", "12/12/2020 11:00 AM");
        ArrayList<AbstractFlight> flightArrayList = new ArrayList<AbstractFlight>();
        Airline airline = createAirlineFlightList(flightArrayList);
        airline.addFlight(flight);
    }

    @Test
    public void testForGettingProperFlight(){
        Flight flight = new Flight(00, "pdx", "12/12/2020 10:00 AM", "lax", "12/12/2020 11:00 AM");
        ArrayList<AbstractFlight> flightArrayList = new ArrayList<AbstractFlight>();
        Airline airline = createAirlineFlightList(flightArrayList);
        airline.getFlights();
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkForInvalidDestInFlight(){
        Flight flight = new Flight(00, "pdx", "12/12/2020 10:00 AM", "invalid", "12/12/2020 11:00 AM");
        ArrayList<AbstractFlight> flightArrayList = new ArrayList<AbstractFlight>();
        Airline airline = createAirlineFlightList(flightArrayList);
        airline.addFlight(flight);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkForInvalidArriveDateInFlight(){
        Flight flight = new Flight(00, "pdx", "12/112/2020 10:00 AM", "sfx", "12/12/2020 11:00 AM");
        ArrayList<AbstractFlight> flightArrayList = new ArrayList<AbstractFlight>();
        Airline airline = createAirlineFlightList(flightArrayList);
        airline.addFlight(flight);
    }

}
