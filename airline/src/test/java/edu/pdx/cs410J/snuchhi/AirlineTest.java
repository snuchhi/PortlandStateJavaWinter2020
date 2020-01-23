package edu.pdx.cs410J.snuchhi;

import org.junit.Test;
import edu.pdx.cs410J.AbstractFlight;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.ArrayList;

/**
 * Unit tests for the {@link Airline} class.
 */

public class AirlineTest{

        private Airline createAirlineFlightList(ArrayList<AbstractFlight> flightArray){
                return new Airline("Test", flightArray);
        }

        @Test
        public void addNewFlight(){
                Flight flight = new Flight(00, "tst", "00/00/000000:00", "tst", "00/00/000000:00");
                ArrayList<AbstractFlight> flightArray = new ArrayList<AbstractFlight>();
                Airline airline = createAirlineFlightList(flightArray);
                airline.addFlight(flight);
        }

        @Test
        public void getCorrectFlight(){
                Flight flight = new Flight(00, "tst", "00/00/000000:00", "tst", "00/00/000000:00");
                ArrayList<AbstractFlight> flightArray = new ArrayList<AbstractFlight>();
                Airline airline = createAirlineFlightList(flightArray);
                airline.getFlights();
        }

        @Test(expected = IllegalArgumentException.class)
        public void addInvalidSrcInFlight(){
                Flight flight = new Flight(00, "no", "00/00/000000:00", "tst", "00/00/000000:00");
                ArrayList<AbstractFlight> flightArray = new ArrayList<AbstractFlight>();
                Airline airline = createAirlineFlightList(flightArray);
                airline.addFlight(flight);
        }
        @Test(expected = IllegalArgumentException.class)
        public void addInvalidDestInFlight(){
                Flight flight = new Flight(00, "pdx", "11/11/111111:11", "wrong", "22/22/222222:22");
                ArrayList<AbstractFlight> flightArray = new ArrayList<AbstractFlight>();
                Airline airline = createAirlineFlightList(flightArray);
                airline.addFlight(flight);
        }

        @Test(expected = IllegalArgumentException.class)
        public void addInvalidDepartInFlight(){
                Flight flight = new Flight(00, "pdx", "000/000/00000000:000", "tst", "00/00/000000:00");
                ArrayList<AbstractFlight> flightArray = new ArrayList<AbstractFlight>();
                Airline airline = createAirlineFlightList(flightArray);
                airline.addFlight(flight);
        }
}
