package edu.pdx.cs410J.snuchhi.airlineapplication;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import java.util.ArrayList;
import java.util.Collection;

//the Airline classes is derived from AbstractAirline class that has a airline and list of flights for that airline.
public class Airline<T extends AbstractFlight> extends AbstractAirline<T> {
    /**
     * @params
     * airlineName : is a string that represents name of an airline
     * flightArrayList : is an array that holds the list of arrays for a given airline
     */
    private String airlineName;
    private ArrayList<AbstractFlight>  flightArrayList = new ArrayList<>();

    /**
     *
     * @param name - airline name
     */
    public Airline(String name) {
        super();
        this.airlineName = name;
    }

    /**this is an airline class constructor that initializes the params
     *
     * @param airlineName : initialize airlineName
     */
    public Airline(String airlineName,ArrayList<AbstractFlight> flightArrayList){
        if(airlineName.length()!=0)
        {
            this.airlineName = airlineName;
            this.flightArrayList = new ArrayList<AbstractFlight>();
        }
        else
            {
            throw new IllegalArgumentException("Airline name is not set");
        }
    }

    public Airline(Airline airline) {
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

       flightArrayList.add(abstractFlight);
    }

    /**
     *
     * @return null (not sure what this does yet)
     */
    @Override
    public Collection getFlights() {
        return flightArrayList;
    }

    public void printAirlineName() {
        System.out.println(this.airlineName);
    }

    public void printFlights() {
        for(AbstractFlight f : this.flightArrayList){
            System.out.println(f.getNumber() + " " + f.getSource() + " " + f.getDepartureString() + " " + f.getDestination() + " " + f.getArrivalString());
        }
    }
}

