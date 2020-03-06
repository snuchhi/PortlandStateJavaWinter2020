package edu.pdx.cs410J.snuchhi;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AirlineServlet extends HttpServlet {
    static final String Airline_name= "airline";
    static final String Flightnum = "flightNumber";
    static final String Src = "src";
    static final String Depart = "depart";
    static final String Dest = "dest";
    static final String Arrival = "arrive";

    private final Map<String, Airline> airlines = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String airlineName = getParameter(Airline_name, request );
        Airline airline = getAirline(airlineName);


        XmlDumper xmlDmp = new XmlDumper(response.getWriter());
        xmlDmp.dump(airline);
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String airlineName = getParameter(Airline_name, request );
        if (airlineName == null) {
            missingRequiredParameter(response, Airline_name);
            return;
        }

        Airline airline = getOrCreateAirline(airlineName);

        String flightNumber = getParameter(Flightnum, request );
        if ( flightNumber == null) {
            missingRequiredParameter( response, Flightnum);
            return;
        }

        String src = getParameter(Src, request );
        if ( src == null) {
            missingRequiredParameter( response, Src);
            return;
        }

        String depart = getParameter(Depart, request );
        if ( depart == null) {
            missingRequiredParameter( response, Depart);
            return;
        }

        String dest = getParameter(Dest, request );
        if ( dest == null) {
            missingRequiredParameter( response, Dest);
            return;
        }

        String arrive = getParameter(Arrival, request );
        if ( arrive == null) {
            missingRequiredParameter( response, Arrival);
            return;
        }

        try {
            Flight flight = new Flight(Integer.parseInt(flightNumber), src, depart, dest, arrive);
            airline.addFlight(flight);

        } catch (IllegalArgumentException ex){
            throw new IllegalArgumentException();
        }

        PrintWriter pw = response.getWriter();
        pw.println(airlineName + flightNumber + src + depart + dest + arrive);
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);
    }

    private Airline getOrCreateAirline(String airlineName) {
        Airline airline = getAirline(airlineName);
        if (airline == null) {
            airline = new Airline(airlineName);
            this.airlines.put(airlineName, airline);
        }

        return airline;
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.airlines.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
            throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }


    /**
     *
     * @param name parameter name
     * @param request http request method
     * @return null
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }

    @VisibleForTesting
    Airline getAirline(String airlineName) {
        return this.airlines.get(airlineName);
    }
}