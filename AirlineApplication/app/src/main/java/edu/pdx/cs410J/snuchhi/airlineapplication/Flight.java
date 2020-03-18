package edu.pdx.cs410J.snuchhi.airlineapplication;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import edu.pdx.cs410J.AbstractFlight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DateFormat;
import edu.pdx.cs410J.AirportNames;

//The flight class is derived from AbstractFlight
public class Flight extends AbstractFlight implements Comparable<Flight> {
  /**
   * flightNum : the flightNumber
   * src : 3 letter source code
   * depart : the departure date and time
   * dest : 3 letter destination code
   * arrive : the arrival date and time
   */
  private int flightNum;
  private String src;
  private String depart;
  private DateFormat departFormat;
  private String dest;
  private String arrive;
  private DateFormat arriveFormat;
  private String errorMsg = "";

  /**
   * Constructor
   *
   * @param flightNum - flightNumber
   */
  public Flight(String flightNum) {
    if (validateFlightNum(flightNum)) {
      this.flightNum = Integer.parseInt(flightNum);
    } else {
      throw new IllegalArgumentException("Invalid Flight Number");
    }
  }

  private boolean validateFlightNum(String flightNum) {
    String regex = "[0-9]+";
    return flightNum.matches(regex);
  }

  /**
   * @param source - set source
   */
  public void setSource(String source) {
    this.src = validateSrcDest(source);
  }

  /**
   * @param dest -set detsination
   */
  public void setDestination(String dest) {
    this.dest = validateSrcDest(dest);
  }

  public void setDepartDate(String departureDate, String departureTime) throws Exception {
    String date = null, time;
    try {
      if (checkdateandtime("Departure", departureDate, departureTime)) {
        DateFormat sd = new SimpleDateFormat("MM/dd/yyyy");
        Date departdate = sd.parse(departureDate);
        date = DateFormat.getDateInstance(DateFormat.SHORT).format(departdate);
      }
    } catch (Exception e) {
      throw new Exception("Invalid Departure Date");
    }
    try {
      DateFormat sd1 = new SimpleDateFormat("hh:mm aa");
      Date departtime = sd1.parse(departureTime);
      time = DateFormat.getTimeInstance(DateFormat.SHORT).format(departtime);

    } catch (Exception e) {
      throw new Exception("Invalid Departure time");
    }
    this.depart = date + " " + time;

  }

  public void setArrivalDate(String arrivalDate, String arrivalTime) throws Exception {
    String date = null, time;
    try {
      if (checkdateandtime("Arrival", arrivalDate, arrivalTime)) {
        DateFormat sd = new SimpleDateFormat("MM/dd/yyyy");
        Date arrivedate = sd.parse(arrivalDate);
        date = DateFormat.getDateInstance(DateFormat.SHORT).format(arrivedate);
      }
    } catch (Exception e) {
      throw new Exception("Invalid Arrival Date ");
    }
    try {
      DateFormat sd1 = new SimpleDateFormat("hh:mm aa");
      Date arrivetime = sd1.parse(arrivalTime);
      time = DateFormat.getTimeInstance(DateFormat.SHORT).format(arrivetime);

    } catch (Exception e) {
      throw new Exception("Invalid Arrival Time");
    }
    this.arrive = date + " " + time;

  }

  private boolean checkdateandtime(String departure, String date, String departureTime) {

    String dateregex = "^(1[0-2]|0[1-9]|[1-9])/(3[01]|[12][0-9]|0[1-9]|[1-9])/([0-9]{4}|[0-9]{2})$";
    Pattern datepattern = Pattern.compile(dateregex);
    Matcher matcher = datepattern.matcher(date);
    boolean validdate = matcher.matches();
    if (!validdate) {
      throw new IllegalArgumentException(departure + "date is invalid");
    }
    return true;
  }

  private String validateSrcDest(String srcdest) {
    //source string validation (less than 3)
    if (srcdest.length() < 3) {
      throw new IllegalArgumentException("source/dest must be a 3 letter code. too small");
    }
    //source string validation(greater than 3)
    if (srcdest.length() > 3) {
      throw new IllegalArgumentException("source/dest must be a 3 letter code, too big ");
    }
    //source string validation(if source contains anything apart from letters
    if (Pattern.compile("[^a-zA-Z]").matcher(srcdest).find()) {
      throw new IllegalArgumentException("src/dest contains invalid character, should have only letters");
    }
    srcdest = srcdest.toUpperCase();
    if (AirportNames.getName(srcdest) == null) {
      throw new IllegalArgumentException(srcdest + " does not exist in airport names");
    }
    return srcdest;
  }


  /**
   * @return return the flightNum
   */
  @Override
  public int getNumber() {
    return this.flightNum;
  }

  /**
   * @return
   */
  @Override
  public String getSource() {

    if (src == null) {
      throw new UnsupportedOperationException("Source not set");
    }
    return this.src;
  }

  /**
   * @return
   */
  @Override
  public String getDepartureString() {
    if (depart == null) {
      throw new UnsupportedOperationException("Departure Date and Time not set");
    }
    return this.depart;
    // return this.depart.toString();
  }

  /**
   * @return
   */
  @Override
  public String getDestination() {

    if (dest == null) {
      throw new UnsupportedOperationException("Destination not set");
    }
    return this.dest;
  }

  /**
   * @return
   */
  @Override
  public String getArrivalString() {
    if (arrive == null) {
      throw new UnsupportedOperationException("Arrival Date and Time not set");
    }
    return this.arrive;
    //return this.arrive.toString();
  }

  /**
   * @return difference of time in minutes
   */
  public long timesDifferenceInMinutes(String depart,String arrival) throws ParseException {
    @SuppressLint("SimpleDateFormat") DateFormat sdf = new SimpleDateFormat("MM/dd/yy hh:mm a");
    Date departdate = sdf.parse(depart);
    Date arrivaldate=sdf.parse(arrival);
    assert arrivaldate != null;
    assert departdate != null;
    long diff = arrivaldate.getTime()- departdate.getTime();
    return diff / (60 * 1000);
  }

  /**
   * @param departuredate
   * @param arrivaldate
   * @return true/false
   * @throws Exception
   */
  public static boolean checkarrivalanddepartdate(String departuredate, String arrivaldate) throws Exception {

    try {
      @SuppressLint("SimpleDateFormat") DateFormat sdf = new SimpleDateFormat("MM/dd/yy hh:mm a");
      Date depart = sdf.parse(departuredate);
      Date arrive = sdf.parse(arrivaldate);
      assert depart != null;
      if (depart.compareTo(arrive) < 0) {
        return true;
      }
    } catch (Exception e) {
      throw new Exception("Arrival date or time must be after departure date or time.");
    }
    return false;
  }

  /**
   * @param flight - the comparator to compare the src and dest of flights to sort
   * @return value1
   */
  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @Override
  public int compareTo(Flight flight) {
    int value1 = this.getSource().compareTo(flight.getSource());
    if (value1 == 0) {
      return this.getDepartureString().compareTo(flight.getDepartureString());
    }
    return value1;
  }
}

