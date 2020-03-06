package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirportNames;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PrettyPrinter implements AirlineDumper {

    @Override
    public void dump(AbstractAirline airline) throws IOException {
        ArrayList<Flight> flightArray  = (ArrayList<Flight>) airline.getFlights();
        System.out.println("Airline Details for: " + airline.getName() + " Airlines. \n");
        for(Flight f : flightArray){
            createRealDates(f);
        }
    }

    /**
     *
     * @param f flight details
     */
    private void createRealDates(Flight f) {
        String departureDate = f.getDepartureString().replace(",", "");
        String arrivalDate = f.getArrivalString().replace(",", "");
        System.out.println("Flight Number " + f.getNumber()  +
                " will depart from " + f.getSource() + " airport in " + f.getSRCName() + " and flight departs at: " + departureDate + "\n" +
                "The Flight will arrive at " + f.getDestination() + " airport in " + f.getDESTName() + " and flight arrives at: " + arrivalDate +"\n" +
                "The duration of flight is " + f.timesDifferenceInMinutes() + " minutes.\n");
    }

    /**
     *
     * @param airline -airline name
     * @param src - source flight
     * @param dest - destination flight
     */
    public void dumpSearch(Airline airline, String src, String dest) {
        ArrayList<Flight> flightArray  = (ArrayList<Flight>) airline.getFlights();
        int found = 0;
        System.out.println("Airline Details for: " + airline.getName() + " Airlines. \n");
        for(Flight f : flightArray){

            if (f.getSource().equals(src) && f.getDestination().equals(dest)) {
                createRealDates(f);
                found = 1;
            }
        }
        if (found == 0) {
            System.out.println("There are no direct flights found from " + AirportNames.getName(src) +
                    " to " + AirportNames.getName(dest));
        }
    }
}