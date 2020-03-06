package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractAirline;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Airline class is derived from AbstractAirline has the details for Airline
 */
public class Airline extends AbstractAirline<Flight>{

    private final String name;
    Collection<Flight> flightArrayList = new ArrayList<>();

    public Airline(String name) {
        this.name = name;

    }

    /**
     *
     * @return airline name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     *
     * @param flight -add flight to airline
     */
    @Override
    public void addFlight(Flight flight) {
        this.flightArrayList.add(flight);
        ArrayList<Flight> newFlights = (ArrayList<Flight>) this.flightArrayList;
        Collections.sort(newFlights);
        Collection<Flight> flights = newFlights;
    }

    /**
     * print the flight details for pretty print
     */
    public void printFlights(){
        for(Flight f : this.flightArrayList){
            System.out.println(f.getNumber() + " " + f.getSource() + " " + f.getDepartureString() + " " + f.getDestination() + " " + f.getArrivalString());
        }

    }

    @Override
    public Collection<Flight> getFlights() {
        return this.flightArrayList;
    }
}