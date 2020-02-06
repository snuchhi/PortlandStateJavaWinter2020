package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirlineDumper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


public class PrettyPrinter implements AirlineDumper {

    private String fileName;

    /**
     * constructor
     */
    PrettyPrinter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void dump(AbstractAirline abstractAirline) throws IOException {
        String airlineName = abstractAirline.getName();
        ArrayList<AbstractFlight> flightArrayList = (ArrayList<AbstractFlight>) abstractAirline.getFlights();
        // Collection<AbstractFlight> flightCollection = abstractAirline.getFlights();
        ArrayList<Flight> flights = new ArrayList<>();
        for (AbstractFlight f : flightArrayList) {
            flights.add((Flight) f);
        }
        Collections.sort(flights);
        int num_flights = flights.size();

        if (fileName.equals("-")) {
            fileName = "prettyFile.txt";
            System.out.println("The airline is : " + airlineName + " which has the following flights scheduled");

            for (int i = 0; i < num_flights; i++) {
                Flight flight = (Flight) flights.get(i);
                System.out.println(flight.toString() + " and the duration of flight is: " + flight.timesDifferenceInMinutes() + " minutes");
            }
        }
        BufferedWriter bufferedWriter = null;
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            bufferedWriter = new BufferedWriter(fileWriter);

            //write airline name to file
            bufferedWriter.write("The airline is:" + abstractAirline.getName() + " which has the following flights: ");
            bufferedWriter.newLine();

            //write the flight details into pretty file
            num_flights = flights.size();
            for (int i = 0; i < num_flights; i++) {
                bufferedWriter.write("Flight:" + i);
                bufferedWriter.newLine();
                Flight flight = (Flight) flights.get(i);
                bufferedWriter.write(flight.toString());
                bufferedWriter.write(" duration of the flight is : " + flight.timesDifferenceInMinutes() + " minutes");
                bufferedWriter.newLine();
                System.out.println("The airline details dumped to pretty file.");

            }

            bufferedWriter.close();

        } catch (FileNotFoundException e) {

            try {
                File file = new File(this.fileName);
                file.createNewFile();
            } catch (IOException e1) {
                throw new IOException("File does not exist and cannot create a new file;");
            }
        }
    }

}

