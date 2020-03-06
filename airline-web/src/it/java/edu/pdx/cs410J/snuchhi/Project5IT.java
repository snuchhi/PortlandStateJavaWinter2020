package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.snuchhi.AirlineRestClient.AirlineRestException;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * An integration test for {@link Project5} that invokes its main method with
 * various arguments
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Project5IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    public void test0RemoveAllMappings() throws IOException {
        AirlineRestClient client = new AirlineRestClient(HOSTNAME, Integer.parseInt(PORT));
        client.removeAllDictionaryEntries();
    }

    @Test
    public void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain(Project5.class);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project5.MISSING_ARGS));
    }

    @Test
    public void test2EmptyServer() {
        MainMethodResult result = invokeMain(Project5.class, "-host", HOSTNAME, "-port", PORT);
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
    }

    @Test(expected = AirlineRestException.class)
    public void test3NoDefinitionsThrowsAppointmentBookRestException() throws Throwable {
        String word = "WORD";
        try {
            invokeMain(Project5.class, "-host", HOSTNAME, "-port", PORT, word);

        } catch (IllegalArgumentException ex) {
            throw ex.getCause().getCause();
        }
    }

    @Test
    public void test4AddFlightDetailsToAirline() {
        String airlineName = "Airline";
        int flightNumber = 111;
        String src = "PDX";
        String depart = "10/10/2020";
        String departTime = "10:02";
        String departTimeSymbol = "pm";
        String dest = "LAX";
        String arrive = "10/10/2020";
        String arriveTime = "11:22";
        String arriveTimeSymbol = "pm";

        MainMethodResult result = invokeMain(Project5.class, "-host", HOSTNAME, "-port", PORT, airlineName, String.valueOf(flightNumber), src, depart, departTime, departTimeSymbol,
                dest, arrive, arriveTime, arriveTimeSymbol);
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));
    }


}