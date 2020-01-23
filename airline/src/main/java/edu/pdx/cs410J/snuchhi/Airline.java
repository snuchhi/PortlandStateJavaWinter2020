package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import java.util.ArrayList;
import java.util.Collection;

//the Airline classes is derived from AbstractAirline class that has a airline and list of flights for that airline.
public class Airline extends AbstractAirline {
    /**
     * @params
     * airlineName : is a string that represents name of an airline
     * flightArrayList : is an array that holds the list of arrays for a given airline
     */
    private String airlineName;
    private ArrayList<AbstractFlight> flightArrayList;

    /**this is an airline class constructor that initializes the params
     *
     * @param airlineName : initialize airlineName
     * @param flightArrayList : initialize flightArrayList
     */
    public Airline(String airlineName, ArrayList<AbstractFlight> flightArrayList){
        this.airlineName = airlineName;
        this.flightArrayList = new ArrayList<AbstractFlight>();
    }

    /**
     *
     * @return - the airlineName
     */
    @Override
    public String getName() {
        return this.airlineName;
    }

    /**
     *
     * @param abstractFlight - add flight to flightArrayList
     */
    @Override
    public void addFlight(AbstractFlight abstractFlight) {

        this.flightArrayList.add(abstractFlight);
    }

    /**
     *
     * @return null (not sure what this does yet)
     */
    @Override
    public Collection getFlights() {
        return null;
    }
}

