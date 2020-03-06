package edu.pdx.cs410J.snuchhi;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class AirlineServletTest {

    private AirlineServlet createFlight(String airlineName, int flightNumber, String src, String depart, String dest, String arrive) throws IOException, ServletException {
        AirlineServlet servlet = new AirlineServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("airline")).thenReturn(airlineName);
        when(request.getParameter("flightNumber")).thenReturn(String.valueOf(flightNumber));
        when(request.getParameter("src")).thenReturn(src);
        when(request.getParameter("depart")).thenReturn(depart);
        when(request.getParameter("dest")).thenReturn(dest);
        when(request.getParameter("arrive")).thenReturn(arrive);


        HttpServletResponse response = mock(HttpServletResponse.class);

        PrintWriter pw = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);

        return servlet;
    }

    @Test
    public void addingFlightToServletStoresAirlineWithFlight() throws ServletException, IOException {

        String airlineName = "Test";
        int flightNumber = 41;
        String src = "PDX";
        String depart = "10/10/2020 11:11 pm";
        String dest = "LAX";
        String arrive = "10/11/2020 12:12 am";

        AirlineServlet servlet = createFlight(airlineName, flightNumber, src, depart, dest, arrive);

        Airline airline = servlet.getAirline(airlineName);
        assertThat(airline, not(nullValue()));

        Flight flight = airline.getFlights().iterator().next();
        assertThat(flight.getNumber(), equalTo(flightNumber));
        assertThat(flight.getSource(), equalTo(src));
        assertThat(flight.getDepartureString(), equalTo(depart));
        assertThat(flight.getDestination(), equalTo(dest));
        assertThat(flight.getArrivalString(), equalTo(arrive));
    }

    @Test
    public void getAirlineAsXml() throws IOException, ServletException {
        String airlineName = "Test";
        int flightNumber = 41;
        String src = "PDX";
        String depart = "10/10/2020 1:12 pm";
        String dest = "LAX";
        String arrive = "10/10/2020 2:22 pm";

        AirlineServlet servlet = createFlight(airlineName, flightNumber, src, depart, dest, arrive);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("airline")).thenReturn(airlineName);

        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter out = new StringWriter();
        PrintWriter pw = new PrintWriter(out);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);

        String xml = out.toString();

        assertThat(xml, containsString(airlineName));
        assertThat(xml, containsString(String.valueOf(flightNumber)));
        assertThat(xml, containsString(src));
        assertThat(xml, containsString(dest));


    }

    @Test
    public void delete() throws IOException, ServletException {
        String airlineName = "Test";
        int flightNumber = 41;
        String src = "PDX";
        String depart = "10/10/2020 1:12 pm";
        String dest = "LAS";
        String arrive = "10/10/2020 2:22 pm";


        AirlineServlet servlet = new AirlineServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("airline")).thenReturn(airlineName);
        when(request.getParameter("flightNumber")).thenReturn(String.valueOf(flightNumber));
        when(request.getParameter("src")).thenReturn(src);
        when(request.getParameter("depart")).thenReturn(depart);
        when(request.getParameter("dest")).thenReturn(dest);
        when(request.getParameter("arrive")).thenReturn(arrive);


        HttpServletResponse response = mock(HttpServletResponse.class);

        PrintWriter pw = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(pw);

        servlet.doDelete(request, response);
    }

    @Test
    public void getAirline() {
        AirlineServlet servlet = new AirlineServlet();
        String airlineName = "AIRLINE";
        servlet.getAirline(airlineName);
    }

    @Test
    public void Post() throws IOException, ServletException {
        String airlineName = "Test";
        int flightNumber = 41;
        String src = "PDX";
        String depart = "10/10/2020 1:12 pm";
        String dest = "LAX";
        String arrive = "10/10/2020 3:22 pm";

        AirlineServlet servlet = new AirlineServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("airline")).thenReturn(airlineName);
        when(request.getParameter("flightNumber")).thenReturn(String.valueOf(flightNumber));
        when(request.getParameter("src")).thenReturn(src);
        when(request.getParameter("depart")).thenReturn(depart);
        when(request.getParameter("dest")).thenReturn(dest);
        when(request.getParameter("arrive")).thenReturn(arrive);

        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter pw = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);
    }



}