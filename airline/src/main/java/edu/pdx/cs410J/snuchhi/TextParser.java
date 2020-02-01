package edu.pdx.cs410J.snuchhi;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import static java.lang.Integer.parseInt;

public class TextParser implements AirlineParser {

    private String fileName;

    public TextParser(String fileName) {
        // check to look for special characters in filename

       // if (!fileName.matches("([a-z]|[A-Z]|[0-9]|[.])*")) {
        //    throw new IllegalArgumentException("File name invalid.");
      //  }


        this.fileName = fileName;
    }
    //parse the contents of the text file and create flight details.
    @Override
    public AbstractAirline parse() throws ParserException {

        String[] flightArgs;
        try {
            FileReader fileReader = new FileReader(this.fileName);
            BufferedReader bfReader = new BufferedReader(fileReader);
            String firstLine = bfReader.readLine();

            ArrayList<AbstractFlight> flightArrayList = new ArrayList<AbstractFlight>();
            Airline airline = new Airline<>(firstLine,flightArrayList);
            firstLine = bfReader.readLine();
            while (firstLine!=null){
                flightArgs = firstLine.split(" ");
                Flight flight = new Flight(parseInt(flightArgs[0]), flightArgs[1],
                        flightArgs[2] + " " + flightArgs[3],
                        flightArgs[4], flightArgs[5] + " " + flightArgs[6]);

                airline.addFlight(flight);
                firstLine = bfReader.readLine();
            }
            bfReader.close();
            return airline;
        } catch (IOException e){
            System.out.println("Some IO exception occurred");
        }
    return null;
}

}

