package edu.pdx.cs410J.snuchhi;

import org.junit.Test;
import edu.pdx.cs410J.AbstractFlight;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.ArrayList;
/**
 * Unit tests for the {@link Flight} class.
 */
public class FlightTest {
  
  @Test(expected = UnsupportedOperationException.class)
  public void getArrivalStringNeedsToBeImplemented() {
    Flight flight = new Flight(00, "tst", "tst",  "00/00/0000 00:00" , "00/00/0000 00:00");
    flight.getArrivalString();
  }

  @Test
  public void initiallyAllFlightsHaveTheSameNumber() {
    Flight flight = new Flight(42, "src", "dest", "00/00/0000 00:00", "00/00/0000 00:00");
    assertThat(flight.getNumber(), equalTo(42));
  }

  @Test
  public void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    Flight flight = new Flight(42, "src", "dest", null, "00/00/0000 00:00");
    assertThat(flight.getDeparture(), is(nullValue()));
  }

  //This is a test for source validation
  private Flight createFlightForSrcValidation(String src){
    return new Flight(42, src, "00/00/0000 00:00", "dest", "00/00/0000 00:00");
  }

  @Test
  public void correctSrcValidation(){
    String src = "lax";
    createFlightForSrcValidation(src);
  }

  @Test(expected = IllegalArgumentException.class)
  public void srcIsLessThanThreeLetters()
  {
    String src = "la";
    createFlightForSrcValidation(src);
  }

  @Test(expected = IllegalArgumentException.class)
  public void srcIsMoreThanThreeLetters()
  {
    String src = "laxp";
    createFlightForSrcValidation(src);
  }

  @Test(expected = IllegalArgumentException.class)
  public void srcDoesNotHaveOnlyLetters()
  {
    String src = "l2x";
    createFlightForSrcValidation(src);
  }
  //This is a test for destination validation
  private Flight createFlightForDestValidation(String dest){
    return new Flight(42, "tst", "00/00/0000 00:00", dest, "00/00/0000 00:00");
  }

  @Test
  public void correctDestValidation(){
    String src = "lax";
    createFlightForDestValidation(src);
  }

  @Test(expected = IllegalArgumentException.class)
  public void DestIsLessThanThreeLetters()
  {
    String src = "la";
    createFlightForSrcValidation(src);
  }

  @Test(expected = IllegalArgumentException.class)
  public void destIsMoreThanThreeLetters()
  {
    String src = "laxp";
    createFlightForDestValidation(src);
  }

  @Test(expected = IllegalArgumentException.class)
  public void DestDoesNotHaveOnlyLetters()
  {
    String src = "l2x";
    createFlightForDestValidation(src);
  }

  //Validation for departure(date and time format)
  private Flight createFlightForDepartValidation(String depart) {
    return new Flight(00, "tst", depart, "tst", "00/00/0000 00:00");
  }

  @Test
  public void whenDepartIsValid(){
    String depart = "00/00/0000 00:00";
    createFlightForDepartValidation(depart);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenDepartIsNotInDateFormat(){
    String invalidDepart = "xx/xx/xxxx xx:xx";
    createFlightForDepartValidation(invalidDepart);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenDepartYearIsInvalid(){
    String depart = "00/00/00000 00:00";
    createFlightForDepartValidation(depart);
  }
  @Test(expected = IllegalArgumentException.class)
  public void whenDepartDateIsInvalid(){
    String depart = "00/000/0000 00:00";
    createFlightForDepartValidation(depart);
  }
  @Test(expected = IllegalArgumentException.class)
  public void whenDepartMonthIsInvalid(){
    String depart = "000/00/0000 00:00";
    createFlightForDepartValidation(depart);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenDepartTimeIsInvalid(){
    String invalidDepart = "00/00/0000 000:00";
    createFlightForDepartValidation(invalidDepart);
  }

  //Validation for departure(date and time format)
  private Flight createFlightForArrivalValidation(String arrive) {
    return new Flight(00, "tst","00/00/0000 00:00" , "tst",arrive );
  }

  @Test
  public void whenArrivalIsValid(){
    String arrive = "00/00/0000 00:00";
    createFlightForDepartValidation(arrive);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenArrivalIsNotInDateFormat(){
    String arrive = "xx/xx/xxxx xx:xx";
    createFlightForDepartValidation(arrive);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenArrivalYearIsInvalid(){
    String arrive = "00/00/00000 00:00";
    createFlightForDepartValidation(arrive);
  }
  @Test(expected = IllegalArgumentException.class)
  public void whenArrivalDateIsInvalid(){
    String arrive = "00/000/0000 00:00";
    createFlightForDepartValidation(arrive);
  }
  @Test(expected = IllegalArgumentException.class)
  public void whenArrivalMonthIsInvalid(){
    String arrive = "000/00/0000 00:00";
    createFlightForDepartValidation(arrive);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenArrivalTimeIsInvalid(){
    String arrive = "00/00/0000 000:00";
    createFlightForDepartValidation(arrive);
  }
  private Flight createFlightForFullCommandLineValidation(String src, String dept, String dest, String arrive){
    return new Flight(00, src, dept, dest, arrive);
  }

  @Test
  public void properFinalTest(){
    String src = "lax";
    String depart = "01/22/2020 15:15";
    String dest = "pdx";
    String arrive = "01/22/2020 17:00";
    createFlightForFullCommandLineValidation(src,depart, dest, arrive);
  }

}
