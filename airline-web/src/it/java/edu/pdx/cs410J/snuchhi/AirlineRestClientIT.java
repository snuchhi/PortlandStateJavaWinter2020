package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Integration test that tests the REST calls made by {@link AirlineRestClient}
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AirlineRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AirlineRestClient newAirlineRestClient() {
    int port = Integer.parseInt(PORT);
    return new AirlineRestClient(HOSTNAME, port);
  }

    @Test
    public void test0RemoveAllDictionaryEntries() throws IOException {
        AirlineRestClient client = newAirlineRestClient();
        client.removeAllDictionaryEntries();
    }

    @Test
    public void test1gettingDictionary() throws IOException {
        AirlineRestClient client = newAirlineRestClient();
        client.getAllDictionaryEntries();
    }
    @Test
    public void test2addOneFlight() throws IOException {
        AirlineRestClient client = newAirlineRestClient();
        String airlineName = "Test";
        int flightNumber = 41;
        String src = "PDX";
        String depart = "10/10/2020 10:02 pm";
        String dest = "LAX";
        String arrive = "10/10/2020 11:22 pm";

        client.addFlight(airlineName, flightNumber, src, depart, dest, arrive);

        String xml = client.getAirlineAsXml(airlineName);
        System.out.println(xml);

        assertThat(xml, containsString(airlineName));
        assertThat(xml, containsString(String.valueOf(flightNumber)));
        assertThat(xml, containsString(src));
        assertThat(xml, containsString(dest));
    }



}
