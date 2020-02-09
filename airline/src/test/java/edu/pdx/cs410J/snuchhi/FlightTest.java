package edu.pdx.cs410J.snuchhi;

import org.junit.Ignore;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.ArrayList;
/**
 * Unit tests for the {@link Flight} class.
 */
public class FlightTest {

  @Ignore
  @Test(expected = UnsupportedOperationException.class)
  public void getArrivalStringNeedsToBeImplemented() {
    Flight flight = new Flight(00, "las",  "12/12/2020 12:00 AM","lax", "12/12/2020 02:00 AM");
    assertThat(flight.getArrivalString(), equalTo("Flight 0 departs src at 12/12/2020 12:00 AM arrives dst at 12/12/2020 02:00 AM"));
  }

  @Test
  public void initiallyAllFlightsHaveTheSameNumber() {
    Flight flight = new Flight(42, "pdx", "12/12/2020 12:00 AM", "lax", "12/12/2020 02:00 AM");
    assertThat(flight.getNumber(), equalTo(42));
  }

  private Flight createFlightForFullCommandLineValidation(String src, String dept, String dest, String arrive){
    return new Flight(00, src, dept, dest, arrive);
  }

  @Test
  public void checkForAllCorrectValues(){
    String src = "lax";
    String depart = "01/22/2020 10:15 AM";
    String dest = "pdx";
    String arrive = "01/22/2020 11:50 AM";
    createFlightForFullCommandLineValidation(src,depart, dest, arrive);
  }
  //This is a test for source validation
  private Flight createFlightForSrcValidation(String src){
    return new Flight(42, src, "12/12/2020 12:00 AM", "pdx", "12/12/2020 02:00 AM");
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
  private Flight createFlightForDestValidation(String dst){
    return new Flight(42, "pdx", "12/12/2020 12:00 AM", dst, "12/12/2020 02:00 AM");
  }
  // the test is to validate that the destination string is correctly input
  @Test
  public void correctDestValidation(){
    String dst = "lax";
    createFlightForDestValidation(dst);
  }
  // the test is to validate for the destination not having 3 letters
  @Test(expected = IllegalArgumentException.class)
  public void DestIsLessThanThreeLetters()
  {
    String dst = "la";
    createFlightForSrcValidation(dst);
  }
  // test is to validate destination having more than 3 letters
  @Test(expected = IllegalArgumentException.class)
  public void destIsMoreThanThreeLetters()
  {
    String dst = "laxp";
    createFlightForDestValidation(dst);
  }
  // test is to validate for non characters in destination
  @Test(expected = IllegalArgumentException.class)
  public void DestDoesNotHaveOnlyLetters()
  {
    String dst = "l2x";
    createFlightForDestValidation(dst);
  }

  //Validation for departure(date and time format)
  private Flight createFlightForDepartValidation(String depart) {
    return new Flight(00, "pdx", depart, "lax", "12/12/2020 03:00 AM");
  }

  @Test
  public void whenDepartIsValid(){
    String depart = "12/12/2020 12:00 AM";
    createFlightForDepartValidation(depart);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenDepartIsNotInDateFormat(){
    String invalidDepart = "xx/xx/xx xx:xx xx";
    createFlightForDepartValidation(invalidDepart);
  }

  @Ignore
  @Test(expected = IllegalArgumentException.class)
  public void whenDepartYearIsInvalid(){
    String depart = "12/12/20200 12:00 AM";
    createFlightForDepartValidation(depart);
  }
  @Test(expected = IllegalArgumentException.class)
  public void whenDepartDateIsInvalid(){
    String depart = "12/112/2020 12:00 AM";
    createFlightForDepartValidation(depart);
  }
  @Test(expected = IllegalArgumentException.class)
  public void whenDepartMonthIsInvalid(){
    String depart = "112/12/2020 12:00 AM";
    createFlightForDepartValidation(depart);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenDepartTimeIsInvalid(){
    String invalidDepart = "12/12/2020 121:00 AM";
    createFlightForDepartValidation(invalidDepart);
  }

  //Validation for arrival(date and time format)
  private Flight createFlightForArrivalValidation(String arrive) {
    return new Flight(00, "tst","12/12/2020 11:00 AM" , "tst",arrive );
  }

  @Test
  public void whenArrivalIsValid(){
    String arrive = "12/12/2020 12:00 AM";
    createFlightForDepartValidation(arrive);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenArrivalIsNotInDateFormat(){
    String arrive = "xx/xx/xx xx:xx xx";
    createFlightForDepartValidation(arrive);
  }

  @Ignore
  @Test(expected = IllegalArgumentException.class)
  public void whenArrivalYearIsInvalid(){
    String arrive = "12/12/20002 12:00 AM";
    createFlightForDepartValidation(arrive);
  }
  @Test(expected = IllegalArgumentException.class)
  public void whenArrivalDateIsInvalid(){
    String arrive = "12/112/2020 12:00 AM";
    createFlightForDepartValidation(arrive);
  }
  @Test(expected = IllegalArgumentException.class)
  public void whenArrivalMonthIsInvalid(){
    String arrive = "112/12/2020 12:00 AM";
    createFlightForDepartValidation(arrive);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenArrivalTimeIsInvalid(){
    String arrive = "12/12/2020 112:00 AM";
    createFlightForDepartValidation(arrive);
  }

  @Test(expected = IllegalArgumentException.class)
  public void DepartAirportCodeNotInAirportClass() {
    Flight flight = new Flight(123, "PX", "12/12/2020 12:00 AM", "LAX", "12/12/2020 14:00 pm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ArriveAirportCodeNotInAirportClass() {
    Flight flight = new Flight(123, "PDX", "12/12/2020 12:00 AM", "AX", "12/12/2020 14:00 pm");
  }

  @Test
  public void FlightDescriptionShouldBeMatched() {
    int number = 123;
    String src = "PDX";
    String departDate = "12/12/2020 11:22 PM";
    String dest = "PDX";
    String arriveDate = "12/13/2020 11:20 PM";
    Flight flight = new Flight(number,src,departDate,dest,arriveDate);
    assertThat(flight.toString(), equalTo("Flight 123 departs PDX at 12/12/20, 11:22 PM arrives PDX at 12/13/20, 11:20 PM"));
  }



}
