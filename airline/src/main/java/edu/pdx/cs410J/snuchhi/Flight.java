package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.AbstractFlight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
  private  int flightNum ;
  private  String src;
  private  Date depart;
  private DateFormat departFormat;
  private  String dest;
  private  Date arrive;
  private DateFormat arriveFormat ;
  private  String errorMsg = "";

  /**
   * Constructor
   * @param flightNum - flightNumber
   * @param src - source
   * @param depart - departure date and time
   * @param dest - destination
   * @param arrive - arrival date and time
   */
  public Flight(int flightNum, String src, String depart, String dest,  String arrive) {
    this.flightNum = flightNum;

    //set the source argument
    this.src = validateSrcDest(src);

    //set the dest argument
    this.dest = validateSrcDest(dest);

    //set departure date and time
    try {
      if (depart.substring(0,15).contains("[a-zA-Z]+")) { // if depart contains letters
        errorMsg = "Date should not have letters.";
        throw new IllegalArgumentException("The date format is wrong");
      }
      DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      Date date = format.parse(depart);
      this.depart = date;
      this.departFormat = format; // initialize

    } catch (IllegalArgumentException | ParseException e) {
      System.err.println("The departure date and time is invalid . " + errorMsg);
      throw new IllegalArgumentException();
    }



    //set arrival date and time
    try {
      if (arrive.substring(0,15).contains("[a-zA-Z]+")) { // if depart contains letters
        errorMsg = "Date should not have letters.";
        throw new IllegalArgumentException("The date format is wrong");
      }
      DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      Date departDate = format.parse(depart);
      Date arriveDate = format.parse(arrive);
      if(!arriveDate.after(departDate)) {
        errorMsg = "Arrival date cannot be before departure time.Please correct and reenter";
        throw new IllegalArgumentException();
      }
      this.arrive = arriveDate;
      this.arriveFormat = format; // initialize
    } catch (IllegalArgumentException | ParseException e) {
      System.err.println("The arrival date is invalid." + errorMsg);
      throw new IllegalArgumentException();
    }

    long diff = this.timesDifferenceInMinutes();
    try{
      if (diff < 0 ) {
        throw new IllegalArgumentException();
      }
    }catch (IllegalArgumentException e){
      System.out.println("The arrive date & time is before the departure Date & time. Check and enter correct details");
      System.exit(1);
    }
  }


  public String validateSrcDest(String srcdest) {
    //source string validation (less than 3)
    if (srcdest.length() < 3) {
      throw new IllegalArgumentException("source must be a 3 letter code. too small");
    }
    //source string validation(greater than 3)
    if (srcdest.length() > 3) {
      throw new IllegalArgumentException("source must be a 3 letter code, too big ");
    }
    //source string validation(if source contains anything apart from letters
    if (Pattern.compile("[^a-zA-Z]").matcher(srcdest).find()) {
      throw new IllegalArgumentException("src contains invalid character, should have only letters");
    }
    srcdest = srcdest.toUpperCase();
    if (AirportNames.getName(srcdest) == null) {
      throw new IllegalArgumentException(srcdest + " does not exist in airport names");
    }
    return srcdest;
  }



  /**
   *
   * @return return the flightNum
   */
  @Override
  public int getNumber() {
    return this.flightNum;
  }

  /**
   *
   * @return
   */
  @Override
  public String getSource() {

    //throw new UnsupportedOperationException("This method is not implemented yet");
    return this.src;
  }

  /**
   *
   * @return
   */
  @Override
  public String getDepartureString() {

    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
    return df.format(this.depart);
   // return this.depart.toString();
  }

  /**
   *
   * @return
   */
  public String getLongDeparture() {
    return departFormat.format(this.depart);
  }

  /**
   *
   * @return
   */
  @Override
  public String getDestination() {

    //throw new UnsupportedOperationException("This method is not implemented yet");
    return this.dest;
  }

  /**
   *
   * @return
   */
  @Override
  public String getArrivalString() {
    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
    return df.format(this.arrive);
    //return this.arrive.toString();
  }

  /**
   *
   * @return
   */
  public String getLongArrival() {
    return arriveFormat.format(this.arrive);
  }

  /**
   *
   * @return difference of time in minutes
   */
  public long timesDifferenceInMinutes() {
    long minDiff = this.arrive.getTime() - this.depart.getTime();
    return minDiff/(1000*60);
  }

  /**
   *
   * @param flight - the comparator to compare the src and dest of flights to sort
   * @return
   */
  @Override
  public int compareTo(Flight flight) {
    int i = this.src.compareToIgnoreCase(flight.src);
    if (i != 0) return i;
    return Long.compare(this.depart.getTime(), flight.depart.getTime());
  }
}

