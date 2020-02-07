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
  private final int flightNum ;
  private final String src;
  private final Date depart;
  private final String dest;
  private final Date arrive;

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
    this.depart = validateDateTime(depart);

    //set arrival date and time
    this.arrive = validateDateTime(arrive);
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

  public Date validateDateTime(String dateTime) throws IllegalArgumentException {

    Date parsedDate = null;
    String dateCheck = "";
    try {
      SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
      dateFormat1.setLenient(false);
      parsedDate = dateFormat1.parse(dateTime);
      dateCheck = dateFormat1.format(parsedDate);

    } catch (ParseException e) {
      try {
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("M/dd/yyyy hh:mm aa");
        dateFormat2.setLenient(false);
        parsedDate = dateFormat2.parse(dateTime);
        dateCheck = dateFormat2.format(parsedDate);
      } catch (ParseException ex) {
        try {
          SimpleDateFormat dateFormat3 = new SimpleDateFormat("MM/dd/yyyy h:mm aa");
          dateFormat3.setLenient(false);
          parsedDate = dateFormat3.parse(dateTime);
          dateCheck = dateFormat3.format(parsedDate);
        } catch (ParseException exception) {
          try {
            SimpleDateFormat dateFormat4 = new SimpleDateFormat("M/dd/yyyy h:mm aa");
            dateFormat4.setLenient(false);
            parsedDate = dateFormat4.parse(dateTime);
            dateCheck = dateFormat4.format(parsedDate);
          } catch (ParseException e1) {
            try {
              SimpleDateFormat dateFormat5 = new SimpleDateFormat("M/d/yyyy hh:mm aa");
              dateFormat5.setLenient(false);
              parsedDate = dateFormat5.parse(dateTime);
              dateCheck = dateFormat5.format(parsedDate);
            } catch (ParseException e2) {
              throw new IllegalArgumentException("Please enter correct format MM/dd/yyyy hh:mm aa");
            }
          }
        }
      }
    }
    if (!dateTime.toUpperCase().equals(dateCheck) || parsedDate == null) {
      throw new IllegalArgumentException("date cannot be parsed to date format");
    }
    return parsedDate;
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
   * @return difference of time in minutes
   */
  public long timesDifferenceInMinutes() {
    long minDiff = this.arrive.getTime() - this.depart.getTime();
    return minDiff/(1000*60);
  }

  /**
   *
   * @param o - the comparator to compare the src and dest of flights to sort
   * @return
   */
  @Override
  public int compareTo(Flight o) {
    String src = o.getSource();
    String dest = o.getDestination();
    int compare = this.src.compareTo(src);
    if (compare == 0)
      compare = this.dest.compareTo(dest);
    if (compare == 0)
      return 0;
    else
      return compare;
  }

}

