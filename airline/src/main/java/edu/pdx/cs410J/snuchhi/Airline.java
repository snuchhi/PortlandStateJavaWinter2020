package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.ArrayList;
import java.util.Collection;

public class Airline extends AbstractAirline {

    private String airlineName;
    private ArrayList<AbstractFlight> flightArrayList;

    public Airline(String airlineName, ArrayList<AbstractFlight> flightArrayList){
        this.airlineName = airlineName;
        this.flightArrayList = new ArrayList<AbstractFlight>();
    }
    @Override
    public String getName() {
        return this.airlineName;
    }

    @Override
    public void addFlight(AbstractFlight abstractFlight) {

        this.flightArrayList.add(abstractFlight);
    }

    @Override
    public Collection getFlights() {
        return this.flightArrayList;
    }
}

