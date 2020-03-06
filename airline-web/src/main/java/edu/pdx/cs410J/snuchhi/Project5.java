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
   // when there are missing arguments
   public static final String MISSING_ARGS = "Missing command line arguments";

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

   public static void main(String... args) {
      String hostName = null;         // host name
      String portString = null;       // port name
      String airlineName = null;      // airline name
      String flightNumber = null;     // flight number
      String src = null;              // source code
      String depart = null;           // depart date and time
      String dest = null;             // destination code
      String arrive = null;           // arrive date and time

      int printFlag = 0;              // if -print, turn flag on
      int searchFlag = 0;             // if -search, turn flag on
      int hostFlag = 0;               // if -host, turn flag on
      int portFlag = 0;               // if -port, turn flag on

      String [] options = {"-README", "-print", "-search", "-host", "-port"}; // list of options

      // error if no args
      if (args.length <= 0) {
         printErrorMessageAndExit( MISSING_ARGS);
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

         // find option that is bad / doesn't exist
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

      // cannot have print and search
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

         int port = 0;       // port number as actual integer

         // error if host name is null
         if (hostName == null) {
            printErrorMessageAndExit(MISSING_ARGS);

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
            // if no airline name, automatic error
            if (airlineName == null) {
               printErrorMessageAndExit("Missing airline name");

               // if just airline name, pretty print all flights of airline
            } else if (flightNumber == null && searchFlag == 0) {

               String xml = client.getAirlineAsXml(airlineName);
               XmlParser parser = new XmlParser(xml);
               Airline parsedAirline = (Airline) parser.parse();
               PrettyPrinter toPretty = new PrettyPrinter();
               toPretty.dump(parsedAirline);

               // if -search, print direct flights of airline: SRC -> DEST
            } else if (searchFlag == 1) {
               String xml = client.getAirlineAsXml(airlineName);
               XmlParser parser = new XmlParser(xml);
               Airline parsedAirline = (Airline) parser.parse();
               PrettyPrinter toPretty = new PrettyPrinter();
               toPretty.dumpSearch(parsedAirline, src, dest);

               // create a full airline and add it to airline REST client
            } else {
               // Post the airlineName/flightNumber pair
               client.addFlight(airlineName, Integer.parseInt(flightNumber), src, depart, dest, arrive);
            }

            // bad connection/error
         } catch (IOException ex) {
            printErrorMessageAndExit("Server error: " + ex);
            return;

            // bad parse
         } catch (ParserException ex) {
            printErrorMessageAndExit("Parser error");
         }
      }

      // if print option, print new flight
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
