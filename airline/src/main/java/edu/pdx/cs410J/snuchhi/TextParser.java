package edu.pdx.cs410J.snuchhi;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Date;

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
            String airlineName = firstLine;
            ArrayList<Flight> flightArrayList = new ArrayList<>();
            Airline airline = new Airline(firstLine,flightArrayList);
            firstLine = bfReader.readLine();

            while (firstLine!=null) {
                flightArgs = firstLine.split(" ");
                String departDate = flightArgs[2] + " " + flightArgs[3] + " " + flightArgs[4];
                String arriveDate = flightArgs[6] + " " + flightArgs[7] + " " + flightArgs[8];
                DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
                try {
                    Date parseDepartDate = df.parse(departDate);
                    Date parseArriveDate = df.parse(arriveDate);
                    departDate = new SimpleDateFormat("MM/dd/YYYY hh:mm aa").format(parseDepartDate);
                    arriveDate = new SimpleDateFormat("MM/dd/YYYY hh:mm aa").format(parseArriveDate);

                } catch (ParseException e) {
                    throw new ParserException("Cannot parse departure date time");
                }

                Flight flight = new Flight(parseInt(flightArgs[0]), flightArgs[1],
                        departDate, flightArgs[5], arriveDate);

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

