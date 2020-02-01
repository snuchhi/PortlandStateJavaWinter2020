package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Project2 {

    static final String USAGE_MESSAGE = "args are (in this order):\n" +
            "\tAirline - name of airline\n" +
            "\tflightNumber\n" +
            "\tsrc - Three-letter code of departure\n" +
            "\tdepart - Departure date and time (24-hour time)\n" +
            "\tdest - Three-letter code of arrival airport\n" +
            "\tarrive - Arrival date and time (24-hour time)\n" +
            "\toptions are (options may appear in any order):\n" +
            "\t-textFile fileName\n" +
            "\t-print\n" +
            "\t-README\n";

    public static void main(String[] args) throws ParserException, IOException {
        // Refer to one of Dave's classes so that we can be sure it is on the classpath
        if (args.length == 0) {
            printErrorMessageAndExit("Missing command line arguments");
        }

        int optionPrint = 0;
        int optionTextFile = 0;
        String departDate = "";
        String arrivalDate = "";
        String fileName = "";
        int numberOfOptions = 0;
        ArrayList<String> flightCommandArgs = new ArrayList<String>();

        for (int i = 0; i < args.length; i++) {
            // if -README , print README and exit
            if (args[i].equals("-README")) {
                printReadMeAndExit();
            }
            if (args[i].equals("-print")) {
                optionPrint = 1;
                numberOfOptions += 1;
                continue;
            }

            //if -textFile, turn text file flag on, also, store the next argument as the file name
            if (args[i].equals("-textFile")) {
                fileName = args[i + 1];
                optionTextFile = 1;
                numberOfOptions += 2;
            }
        }
        //if the number of arguments are less than 8 + the options throw error
        if (args.length < 8 + numberOfOptions) {
            printErrorMessageAndExit("Missing command line arguments ,too few command line arguments, please enter command line arguments");
        }
        //if the number of arguments are greater than 8 + the number of options entered
        if (args.length > 8 + numberOfOptions) {
            System.out.println(args.length);
            printErrorMessageAndExit("Additional command line arguments present please reenter");
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-print")) {
                continue;
            }

            //if -textFile option  the do i++ as it has the filename
            if (args[i].equals("-textFile")) {
                i++;
                continue;
            }
            //appends command line argument into array list
            flightCommandArgs.add(args[i]);
        }

        String flightNumberCheck = flightCommandArgs.get(1);
        try {
            if (!flightNumberCheck.matches("[0-9]+")) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Flight number is invalid. Flight number must be integer");
            System.exit(1);
        }


        File exists = new File(fileName);
        //if file exists, parse file contents
        if (optionTextFile == 1 && exists.isFile()) {
            departDate = flightCommandArgs.get(3) + " " + flightCommandArgs.get(4);
            arrivalDate = flightCommandArgs.get(6) + " " + flightCommandArgs.get(7);
            TextParser parseText = new TextParser(fileName);
            Airline airline = (Airline) parseText.parse();
            // initialize flight values
            checkIfEqual(flightCommandArgs.get(0), airline.getName());
            Flight flight = new Flight(Integer.parseInt(flightCommandArgs.get(1)),
                    flightCommandArgs.get(2), departDate, flightCommandArgs.get(5), arrivalDate);
            airline.addFlight(flight);
            // if printFlag is on, print new flight description
            if (optionPrint == 1) {
                System.out.println("Airline: " + airline.getName());
                System.out.println(flight.toString());
            }
            // dump updated contents into file
            TextDumper dumpToFile = new TextDumper(fileName);
            dumpToFile.dump(airline);
            System.exit(1);
        }


        ArrayList<AbstractFlight> flightArray = new ArrayList<AbstractFlight>();
        Airline airline = new Airline(flightCommandArgs.get(0), flightArray);
        departDate = flightCommandArgs.get(3) + " " + flightCommandArgs.get(4);
        arrivalDate = flightCommandArgs.get(6) + " " + flightCommandArgs.get(7);
        Flight flight = new Flight(Integer.parseInt(flightCommandArgs.get(1)),
                flightCommandArgs.get(2), departDate, flightCommandArgs.get(5), arrivalDate);
        airline.addFlight(flight);
        // if printFlag is on, print new flight description
        if (optionPrint == 1) {
            System.out.println("Airline " + airline.getName());
            System.out.println(flight.toString());
        }
        // if textFileFlag is on, dump this new airline and flight into a newly created file
        if (optionTextFile == 1) {
            TextDumper dumpToFile = new TextDumper(fileName);
            dumpToFile.dump(airline);
        }
        System.exit(1);
    }

    /**
     * @param errorMessage - the error message
     */
    private static void printErrorMessageAndExit(String errorMessage) {
        System.err.println(errorMessage);
        System.err.println(USAGE_MESSAGE);
        System.exit(1);
    }

    /**
     * print the read me and exit
     */
    public static void printReadMeAndExit() {

        System.out.println("\n" + "This is Project2 for CS510 Advanced Java Course which aims at designing an Airline Application" +
                "\tCreated/Developed by: Shruti Nuchhi" + "\n" +
                "The project aims at created classes namely TextDumper and TextParser extended from Abstract classes. \n The text dumper will dump the" +
                "contents of the airline details to text file. \n The parser is responsible for parsing the text file contents and creating the flight." +
                "If the -print option is provided then the details of the flight will be displayed. \n If -README is specified then README is displayed " +
                "and then program exit gracefully. "
        );
        System.exit(1);
    }

    // check if the airline in the file and on command line is equal
    private static void checkIfEqual(String airlineName, String airlineText) {
        try {
            if (!airlineName.equals(airlineText)) {
                throw new IllegalArgumentException();
            }

        } catch (IllegalArgumentException e) {

            System.out.println("Only one Airline allowed in the text file, enter the correct airline");
            System.exit(1);
        }
    }
}