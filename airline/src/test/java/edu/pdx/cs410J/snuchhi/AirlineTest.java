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

        private Airline createAirlineFlightList(ArrayList<AbstractFlight> abstractFlightArrayList){
                return new Airline("CS410J", abstractFlightArrayList);
        }

        @Test
        public void addProperFlight(){
                Flight flight = new Flight(00, "tst", "00/00/0000 00:00", "tst", "00/00/0000 00:00");
                ArrayList<AbstractFlight> abstractFlightArrayList = new ArrayList<AbstractFlight>();
                Airline airline = createAirlineFlightList(abstractFlightArrayList);
                airline.addFlight(flight);
        }

        @Test
        public void getProperFlight(){
                Flight flight = new Flight(00, "tst", "00/00/0000 00:00", "tst", "00/00/0000 00:00");
                ArrayList<AbstractFlight> abstractFlightArrayList = new ArrayList<AbstractFlight>();
                Airline airline = createAirlineFlightList(abstractFlightArrayList);
                airline.getFlights();
        }
        @Test(expected = IllegalArgumentException.class)
        public void addInvalidSrcInFlight(){
                Flight flight = new Flight(00, "no", "00/00/0000 00:00", "tst", "00/00/0000 00:00");
                ArrayList<AbstractFlight> flightArray = new ArrayList<AbstractFlight>();
                Airline airline = createAirlineFlightList(flightArray);
                airline.addFlight(flight);
        }
        @Test(expected = IllegalArgumentException.class)
        public void addInvalidDestInFlight(){
                Flight flight = new Flight(00, "pdx", "11/11/1111 11:11", "wrong", "22/22/2222 22:22");
                ArrayList<AbstractFlight> flightArray = new ArrayList<AbstractFlight>();
                Airline airline = createAirlineFlightList(flightArray);
                airline.addFlight(flight);
        }

        @Test(expected = IllegalArgumentException.class)
        public void addInvalidDepartInFlight(){
                Flight flight = new Flight(00, "pdx", "000/000/00000 000:000", "tst", "00/00/0000 00:00");
                ArrayList<AbstractFlight> flightArray = new ArrayList<AbstractFlight>();
                Airline airline = createAirlineFlightList(flightArray);
                airline.addFlight(flight);
        }
}
