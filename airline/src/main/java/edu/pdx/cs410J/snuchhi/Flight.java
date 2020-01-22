package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AbstractAirline;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Flight extends AbstractFlight {

  private int flightNum ;
  private String src;
  private String depart;
  private String dest;
  private String arrive;

  public Flight(int flightNum, String src, String depart, String dest,  String arrive) {
    this.flightNum = flightNum;

    //source string validation (less than 3)
    if (src.length() < 3) {
      throw new IllegalArgumentException("source must be a 3 letter code. too small");
    }
    //source string validation(greater than 3)
    if (src.length() > 3) {
      throw new IllegalArgumentException("source must be a 3 letter code, too big ");
    }
    //source string validation(if source contains anything apart from letters
    if (Pattern.compile("[^a-zA-Z]").matcher(src).find()) {
      throw new IllegalArgumentException("src contains invalid character, should have only 3 letters");
    }
    //initialize the source argument
    this.src = src;

    //destination string validation(less than 3)
    if (dest.length() < 3) {
      throw new IllegalArgumentException("destination must be a 3 letter code , too small");
    }
    //destination string validation(greater than 3)
    if (dest.length() > 3) {
      throw new IllegalArgumentException("destination must be a 3 letter code, too big ");
    }
    //destination string validation(if source contains anything apart from letters
    if (Pattern.compile("[^a-zA-Z]").matcher(dest).find()) {
      throw new IllegalArgumentException("destination contains invalid character, should have only 3 letters");
    }
    //initialize the dest argument
    this.dest = dest;

    //departure date & time validation
    if (depart.length() < 15) { // if the date and time string is smaller
      throw new IllegalArgumentException("please enter correct format mm/dd/yyyy 00:00");
    }
    if (depart.length() > 15) {
      // if the date and time string is bigger
      throw new IllegalArgumentException("please enter correct format mm/dd/yyyy 00:00");
    }
    if (depart.contains("[a-zA-Z]+")) {
      // if departure has letters
      throw new IllegalArgumentException("the date contains letters,please enter correct format mm/dd/yyyy 00:00");
    }
    //initialize the departure time
    this.depart = depart;

    //departure date & time validation
    if (arrive.length() < 15) { // if the date and time string is smaller
      throw new IllegalArgumentException("please enter correct format mm/dd/yyyy 00:00");
    }
    if (arrive.length() > 15) {
      // if the date and time string is bigger
      throw new IllegalArgumentException("please enter correct format mm/dd/yyyy 00:00");
    }
    if (arrive.contains("[a-zA-Z]+")) {
      // if arrival has letters
      throw new IllegalArgumentException("the date contains letters,please enter correct format mm/dd/yyyy 00:00");
    }
    //initialize the arrival time
    this.arrive = arrive;
  }
  @Override
  public int getNumber() {
    return this.flightNum;
  }

  @Override
  public String getSource() {

    //throw new UnsupportedOperationException("This method is not implemented yet");
    return this.src;
  }

  @Override
  public String getDepartureString() {

    //throw new UnsupportedOperationException("This method is not implemented yet");
    return this.depart;
  }

  @Override
  public String getDestination() {

    //throw new UnsupportedOperationException("This method is not implemented yet");
    return this.dest;
  }

  @Override
  public String getArrivalString() {

    //throw new UnsupportedOperationException("This method is not implemented yet");
    return this.arrive;
  }
}
