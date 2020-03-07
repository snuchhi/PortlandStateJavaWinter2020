package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {

   public static final String MISSING_ARG = "Missing command line arguments";

   static final String USAGE_MESSAGE = "args are (in this order):\n" +
           "\tAirline - name of airline\n" +
           "\tflightNumber\n" +
           "\tsrc - Three-letter code of departure\n" +
           "\tdepart - Departure date and time (24-hour time)\n" +
           "\tdest - Three-letter code of arrival airport\n" +
           "\tarrive - Arrival date and time (24-hour time)\n" +
           "\toptions are (options may appear in any order):\n" +
           "\t-host hostname \n" +
           "\t-port port \n" +
           "\t-search airline name Search for flights\n" +
           "\t-print\n" +
           "\t-README\n";

   /**
    *
    * @param args -command line arguments
    */
   public static void main(String... args) {
      String hostName = null;
      String portString = null;
      String airlineName = null;
      String flightNumber = null;
      String src = null;
      String depart = null;
      String dest = null;
      String arrive = null;

      int printFlag = 0;              // if -print, turn flag on
      int searchFlag = 0;             // if -search, turn flag on
      int hostFlag = 0;               // if -host, turn flag on
      int portFlag = 0;               // if -port, turn flag on

      String [] options = {"-README", "-print", "-search", "-host", "-port"}; // list of options

      // error if no arguments are present
      if (args.length <= 0) {
         printErrorMessageAndExit( MISSING_ARG);
      }

      // find options README, print, search
      for (String arg : args) {
         if (arg.equals("-README")) {
            readMe();
         }

         if (arg.equals("-print")){
            printFlag = 1;
         }

         if (arg.equals("-search")){
            searchFlag = 1;
         }

         if (arg.equals("-host")){
            hostFlag = 1;
         }

         if (arg.equals("-port")){
            portFlag = 1;
         }


         if (arg.contains("-")){
            int invalidFlag = 1;
            for (String op : options){
               if (arg.equals(op)){
                  invalidFlag = 0;
                  break;
               }
            }
            if (invalidFlag == 1) {
               printErrorMessageAndExit("Invalid option: " + arg);
            }
         }
      }

      //print and search cant be specified together
      if (printFlag == 1 && searchFlag == 1) {
         printErrorMessageAndExit("-print and -search cannot be specified at same time");
      }

      // initialize variables
      for (int i = 0; i < args.length; i++) {
         if (args[i].equals("-host") && hostName == null) {
            i++;
            hostName = args[i];

         } else if (args[i].equals("-port") && portString == null) {
            i++;
            portString = args[i];

         } else if (args[i].equals("-print")) {
            continue;

         } else if (args[i].equals("-search")) {
            if (hostFlag == 0 && portFlag == 0){
               printErrorMessageAndExit("Host and Port must be specified for search option");
            }
            continue;

         } else if (airlineName == null) {
            airlineName = args[i];

         } else if (flightNumber == null && searchFlag == 0) {
            flightNumber = args[i];

         } else if (src == null) {
            src = args[i];

         } else if (depart == null && searchFlag == 0) {
            depart = args[i];
            i++;
            depart += " " + args[i];
            i++;
            depart += " " + args[i];

         } else if (dest == null) {
            dest = args[i];

         } else if (arrive == null && searchFlag == 0) {
            arrive = args[i];
            i++;
            arrive += " " + args[i];
            i++;
            arrive += " " + args[i];

         } else {
            printErrorMessageAndExit("Extra command line arguments found");
         }
      }

      if (hostFlag == 1 && portFlag == 1) {

         int port = 0;

         // error if host name is null
         if (hostName == null) {
            printErrorMessageAndExit(MISSING_ARG);

            // error if port name is null
         } else if (portString == null) {
            printErrorMessageAndExit("Missing port");

            // error if port is not integer
         } else {
            try {
               port = Integer.parseInt(portString);

            } catch (NumberFormatException ex) {
               printErrorMessageAndExit("Port \"" + portString + "\" must be an integer");
               return;
            }
         }


         // new airline REST client with specified host name and port number
         AirlineRestClient client = new AirlineRestClient(hostName, port);

         try {

            if (airlineName == null) {
               printErrorMessageAndExit("Missing airline name");


            } else if (flightNumber == null && searchFlag == 0) {

               String xml = client.getAirlineAsXml(airlineName);
               XmlParser parser = new XmlParser(xml);
               Airline parsedAirline = (Airline) parser.parse();
               PrettyPrinter toPretty = new PrettyPrinter();
               toPretty.dump(parsedAirline);


            } else if (searchFlag == 1) {
               String xml = client.getAirlineAsXml(airlineName);
               XmlParser parser = new XmlParser(xml);
               Airline parsedAirline = (Airline) parser.parse();
               PrettyPrinter toPretty = new PrettyPrinter();
               toPretty.dumpSearch(parsedAirline, src, dest);


            } else {

               client.addFlight(airlineName, Integer.parseInt(flightNumber), src, depart, dest, arrive);
            }


         } catch (IOException ex) {
            printErrorMessageAndExit("Server error: " + ex);
            return;


         } catch (ParserException ex) {
            printErrorMessageAndExit("Parser error");
         }
      }

      // if print option then print new flight
      if (printFlag == 1) {
         System.out.println("\nAirline: " + airlineName);
         System.out.println("Flight: " + Integer.parseInt(flightNumber) + " " + src + " " + depart + " " + dest + " " + arrive);
      }

      System.exit(0);
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
    * Prints README text then exits
    */
   private static void readMe(){
      System.out.println("\n" + "This is Project5 for CS510 Advanced Java Course which aims at designing an Airline Application" +
                      "\tCreated/Developed by: Shruti Nuchhi" + "\n" +
                      "The project aims at creating RESTFul application for the Airline application . " +
                      "\n The pretty printer option is used to print the airline details in a nice way" +
                      "\n The XmlDumper is used to dump the" +
                      "contents of the airline details.. \n The parser is responsible for parsing the xml file contents and creating the flight." +
                      "If the -print option is provided then the details of the flight will be displayed. \n If -README is specified then README is displayed " +
                      "and then program exit gracefully. "
              );

      System.exit(1);
   }
}
