package edu.pdx.cs410J.snuchhi;
import edu.pdx.cs410J.AbstractFlight;
import java.util.ArrayList;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {
  /**
   *
   * @param args - the argument list passed from command line
   */
  public static void main(String[] args) {
    // Refer to one of Dave's classes so that we can be sure it is on the classpath
    if (args.length == 0) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    int optionPrint = 0;
    int optionReadMe = 0;
    String departDate = "";
    String arrivalDate = "";
    //set optionPrint to 1 if -print specified
    if (args[0].equals("-print") || args[1].equals("-print")) {
      optionPrint = 1;
    }

    //set optionReadMe to 1 if -ReadMe specified
    if (args[0].equals("-ReadMe") || args[1].equals("-ReadMe")) {
      optionReadMe = 1;
    }
    //if the number of arguments are less than 6 throw error
    if (args.length < 8 && optionPrint == 0 && optionReadMe == 0) {
      System.err.println("Missing command line arguments please enter command line arguments");
      System.exit(1);

    }
    //if the number of arguments are greater than 6 and no option is specified throw an error
    if (args.length > 8 && optionPrint == 0 && optionReadMe == 0) {
      System.out.println(args.length);
      System.err.println("Additional command line arguments present please reenter");
      System.exit(1);
    }
    //if the number of arguments are less than 7 and ReadMe or print specified throw error
    if (args.length < 9 && optionPrint == 1 || args.length < 9 && optionReadMe == 1 ) {
      System.err.println("Missing command line arguments please enter command line arguments options entered");
      System.exit(1);
    }
    //if the number of arguments are greater than 7 and ReadMe or print specified throw error
    if (args.length > 9 && optionReadMe == 0 || args.length > 9 && optionPrint == 0 ) {
      System.err.println("Additional command line arguments present please reenter option entered");
      System.exit(1);
    }
    //if the number of arguments are less than 8 and ReadMe and print specified throw error
    if (args.length < 10 && optionPrint == 1 && optionReadMe == 1) {
      System.err.println("Missing command line arguments please enter command line arguments both options entered ");
      System.exit(1);
    }

    //if the number of arguments are greater than 8 and ReadMe and print specified throw error
    if (args.length > 10) {
      System.err.println("Additional command line arguments present please reenter options entered ");
      System.exit(1);
    }

    // if print and readme  is specified create a new flight class using the command line arguments and create an airline class
    // using the airline specified in command line and add the flight to airline flightArrayList
    if (optionPrint == 0 && optionReadMe == 0) {
      departDate = args[3] + " " + args[4];
      arrivalDate = args[6] + " " + args[7];
      System.out.println(departDate);

      Flight flight = new Flight(Integer.parseInt(args[1]), args[2], departDate, args[5], arrivalDate);
      ArrayList<AbstractFlight> flightArrayList = new ArrayList<>();
      Airline<Flight> airline = new Airline<>(args[0]);
      airline.addFlight(flight);
    }
    //if ReadMe is specified and print is not specified print ReadMe and exit
    if(optionPrint == 0 && optionReadMe == 1 )
    {
      printReadMeAndExit();
    }
    // if print is specified create a new flight class using the command line arguments and create an airline class
    // using the airline specified in command line and add the flight to airline flightArrayList
    //print the details
    if (optionPrint == 1 && optionReadMe == 0) {
      departDate = args[4] + " " + args[5];
      arrivalDate = args[7] +" " + args[8];
      Flight flight = new Flight(Integer.parseInt(args[2]), args[3], departDate, args[6], arrivalDate);
      ArrayList<AbstractFlight> flightArrayList = new ArrayList<>();
      Airline<Flight> airline = new Airline<>(args[1]);
      airline.addFlight(flight);
      System.out.println("Airline:" + airline.getName());
      System.out.println(flight.toString());
    }
    // if print and readme  is specified create a new flight class using the command line arguments and create an airline class
    // using the airline specified in command line and add the flight to airline flightArrayList
    // print the details and print the ReadMe as well
    if (optionPrint == 1 && optionReadMe == 1) {
      departDate = args[5] + " " + args[6];
      arrivalDate = args[8] + " " + args[9];
      Flight flight = new Flight(Integer.parseInt(args[3]), args[4], departDate, args[7], arrivalDate);
      ArrayList<AbstractFlight> flightArrayList = new ArrayList<>();
      Airline<Flight> airline = new Airline<>(args[2]);
      airline.addFlight(flight);
      System.out.println("Airline:" + airline.getName());
      System.out.println(flight.toString());
      printReadMeAndExit();

    }

    System.exit(1);
  }

  /**
   * print the ReadMe when readme option is specified
    */
  public static void printReadMeAndExit() {

    System.out.println("\n" + "This is Project for CS510 Advanced Java Course which aims at designing an Airline Application" +
            "\tCreated/Developed by: Shruti Nuchhi" + "\n" +
            "The project aims at created classes namely Flight and Airline from the extended from Abstract classes and creates a flight" +
            "\nfor a given airline.The project also creates a project1.java class which implements the main function and accepts the " + "\n" +
            "command line arguments. There are various checks performed to look for accurate command line arguments " +
                    "and display an error\n" +
            "message if not. The project also provides an optional ability " +
                    "to print the details for an airline or print the ReadMe and exit."
    );
    System.exit(1);
  }
}