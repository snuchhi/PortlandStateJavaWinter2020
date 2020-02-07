package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PrettyPrinterTest {
    private Airline createAirlineWithFlightInfo(String airlineName, String flightNumber, String src, String depart, String dest, String arrive){
        ArrayList<AbstractFlight> flightArray = new ArrayList<AbstractFlight>(); // new Abstract FLight Array List
        Airline airline = new Airline(airlineName, flightArray); // new Airline object
        Flight flight = new Flight(Integer.parseInt(flightNumber), src, depart, dest, arrive);
        airline.addFlight(flight); // add this newly created flight with initialized values into airline
        return airline;
    }
    private PrettyPrinter createPrettyPrinter(String fileName) {
        return new PrettyPrinter(fileName);
    }


    @Test
    public void parseACorrectFile() throws ParserException, IOException {
        Airline airline = createAirlineWithFlightInfo("TEST", "42", "pdx", "12/12/2020 12:00 AM",
                "lax", "12/12/2020 02:00 AM");
        PrettyPrinter toDump = new PrettyPrinter("flightInfo.txt");
        toDump.dump(airline);


        File file = new File("flightInfo.txt");
        if(file.delete()){
            System.out.println("Test success, deleting the file created .");
        }else{
            System.out.println("Test did not parse the file correctly.");
        }
    }
}
