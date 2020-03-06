package edu.pdx.cs410J.snuchhi;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AirlineRestClient extends HttpRequestHelper {
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final String url;


    /**
     * Creates a client to the airline REST service running on the given host and port
     *
     * @param hostName The name of the host
     * @param port     The port
     */
    public AirlineRestClient(String hostName, int port) {
        this.url = String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET);
    }

    /**
     * Returns all dictionary entries from the server
     */
    public Map<String, String> getAllDictionaryEntries() throws IOException {
        Response response = get(this.url, Map.of());
        return Messages.parseDictionary(response.getContent());
    }

    /**
     * Returns the airline details in xml format
     */
    public String getAirlineAsXml(String airlineName) throws IOException {
        Response response = get(this.url, Map.of("airline", airlineName));
        throwExceptionIfNotOkayHttpStatus(response);
        String xml = response.getContent();
        return xml;
    }

    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getCode();
        if (code != HTTP_OK) {
            throw new AirlineRestException(code);
        }
        return response;
    }

    public void addFlight(String airlineName, int flightNumber, String src, String depart, String dest, String arrive) throws IOException {
        Response response = postToMyURL(Map.of("airline", airlineName, "flightNumber", String.valueOf(flightNumber),
                "src", src, "depart", depart, "dest", dest, "arrive", arrive));
        throwExceptionIfNotOkayHttpStatus(response);
    }

    @VisibleForTesting
    Response postToMyURL(Map<String, String> dictionaryEntries) throws IOException {
        return post(this.url, dictionaryEntries);
    }

    public void removeAllDictionaryEntries() throws IOException {
        Response response = delete(this.url, Map.of());
        throwExceptionIfNotOkayHttpStatus(response);
    }



    @VisibleForTesting
    class AirlineRestException extends RuntimeException {
        AirlineRestException(int httpStatusCode) {
            super("Got an HTTP Status Code of " + httpStatusCode);
        }
    }
}