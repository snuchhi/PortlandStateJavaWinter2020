package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.ParserException;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TextParserTest {

    private Airline createAirlineWithFlightInfo(String airlineName, String flightNumber, String src, String depart, String dest, String arrive){
        ArrayList<AbstractFlight> flightArray = new ArrayList<AbstractFlight>(); // new Abstract FLight Array List
        Airline airline = new Airline(airlineName, flightArray); // new Airline object
        Flight flight = new Flight(Integer.parseInt(flightNumber), src, depart, dest, arrive);
        airline.addFlight(flight); // add this newly created flight with initialized values into airline
        return airline;
    }

    private TextDumper createTextDumper(String fileName) {
        return new TextDumper(fileName + ".txt");
    }

    private TextParser createTextParser(String fileName) {
        return new TextParser(fileName + ".txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseANonExistingFile() throws ParserException, IOException {
        TextParser toParse = createTextParser("file does not exist");
        Airline parsedAirline = (Airline) toParse.parse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseAIncorrectFileName() throws ParserException, IOException {
        TextParser toParse = createTextParser("$**");
        Airline parsedAirline = (Airline) toParse.parse();
    }

    @Test
    public void parseAnProperExampleFile() throws ParserException, IOException {
        Airline airline = createAirlineWithFlightInfo("TEST", "42", "PDX", "00/00/0000 00:00",
                "LAX", "00/00/0000 00:00");
        TextDumper toDump = createTextDumper("flightInfo");
        toDump.dump(airline);

        TextParser toParse = createTextParser("flightInfo");
        Airline parsedAirline = (Airline) toParse.parse();

        File file = new File("flightInfo.txt");
        if(file.delete()){
            System.out.println("Test success, deleting the file created .");
        }else{
            System.out.println("Test did not parse the file correctly.");
        }
    }
}
