package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.ParserException;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TextDumperTest {

    private Flight createFlight(String flightNumber, String src, String depart, String dest, String arrive){
        Flight flight = new Flight(Integer.parseInt(flightNumber), src, depart, dest, arrive);
        return flight;
    }

    private TextDumper createTextDumper(String fileName){
        return new TextDumper(fileName + ".txt");
    }

    private TextParser createTextParser(String fileName) {
        return new TextParser(fileName + ".txt");
    }

    private Airline createAirlineWithFlightInfo(String airlineName, String flightNumber, String src, String depart, String dest, String arrive){
        ArrayList<AbstractFlight> flightArray = new ArrayList<AbstractFlight>(); // new Abstract FLight Array List
        Airline airline = new Airline(airlineName, flightArray); // new Airline object
        Flight flight = new Flight(Integer.parseInt(flightNumber), src, depart, dest, arrive);
        airline.addFlight(flight); // add this newly created flight with initialized values into airline
        return airline;
    }

    @Test(expected = IllegalArgumentException.class)
    public void IncorrectFileNameTest() throws ParserException, IOException {
        TextDumper toDump = createTextDumper("&&&");
        Airline exampleAirline1 = createAirlineWithFlightInfo("CS410J", "00", "PDX", "00/00/0000 00:00", "LAX", "00/00/0000 00:00");
        toDump.dump(exampleAirline1);
    }

    @Test
    public void  createAFlightWithOneAirlineInfo() throws IOException {
        TextDumper dumpText = createTextDumper("flightInfo");
        Airline airline = createAirlineWithFlightInfo("CS410J", "00", "PDX", "00/00/0000 00:00", "LAX", "00/00/0000 00:00");
        dumpText.dump(airline);
        File file = new File("flightInfo.txt");
        if(file.delete()){
            System.out.println("Test createAFlightWithOneAirlineInfo created and deleted.");
        }else{
            System.out.println("Test to create a flight failed.");
        }
    }


}
