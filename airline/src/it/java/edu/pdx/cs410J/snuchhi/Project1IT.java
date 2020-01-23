package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project1} main class.
 */
public class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

    /**
     * tests that invoking main method with less command line arguments
     */
  @Test
    public void testWithLessCommandLineArguments()
  {
      MainMethodResult result = invokeMain("CS410J", "42");
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments please enter command line arguments"));
  }
    /**
     * tests that invoking main method with more command line arguments
     */
    @Test
    public void testWithManyCommandLineArguments()
    {
        MainMethodResult result = invokeMain("CS410J", "42", "lax", "01/22/2020 15:30", "pdx", "01/22/2020 17:30","mam");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Additional command line arguments present please reenter"));
    }

    /**
     * test that invokes with correct number of command line arguments
     */
    @Test
    public void testWithCorrectCommandLineArgumentsAndPrintOption() {
        MainMethodResult result = invokeMain("-print", "CS410J", "42", "lax", "01/22/2020 15:30", "pdx", "01/22/2020 17:30");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 42 departs lax at 01/22/2020 15:30 arrives pdx at 01/22/2020 17:30"));
    }

    /**
     * test that invokes with correct number of command line arguments and readme option
     */
    @Test
    public void testWithCorrectCommandLineArgumentsAndReadMeOption() {
        MainMethodResult result = invokeMain("-ReadMe", "CS410J", "42", "lax", "01/22/2020 15:30", "pdx", "01/22/2020 17:30");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("" +
                "This is Project for CS510 Advanced Java Course which aims at designing an Airline Application\tCreated/Developed by: Shruti Nuchhi\n" +
                "The project aims at created classes namely Flight and Airline from the extended from Abstract classes and creates a flight\n" +
                "for a given airline.The project also creates a project1.java class which implements the main function and accepts the \n" +
                "command line arguments. There are various checks performed to look for accurate command line arguments and display an error\n" +
                "message if not. The project also provides an optional ability to print the details for an airline or print the ReadMe and exit."));
    }

    /**
     * test that invokes with correct number of command line arguments and no read me option
     */
    @Test
    public void testWithCorrectCommandLineArgumentsAndWithoutOption() {
        MainMethodResult result = invokeMain("CS410J", "42", "lax", "01/22/2020 15:30", "pdx", "01/22/2020 17:30");
        assertThat(result.getExitCode(), equalTo(1));
    }
    /**
     * test that invokes with less number of command line arguments with print option
     */
    @Test
    public void testWithLessCommandLineArgumentsAndWithPrintOption() {
        MainMethodResult result = invokeMain("-print", "CS410J", "42", "lax", "01/22/2020 15:30", "pdx");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    /**
     * test that invokes with many number of command line arguments with print option
     */
    @Test
    public void testWithManyCommandLineArgumentsAndWithPrintOption() {
        MainMethodResult result = invokeMain("-print", "CS410J", "42", "lax", "01/22/2020 15:30", "pdx", "01/22/2020 17:30", "xxxx");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Additional command line arguments present please reenter"));
    }

    /**
     * test that invokes with many number of command line arguments with print and readMe option
     */
    @Test
    public void testWithCorrectCommandLineArgumentsAndWithPrintAndReadMeOption() {
        MainMethodResult result = invokeMain("-print", "-ReadMe", "CS410J", "42", "lax", "01/22/2020 15:30", "pdx", "01/22/2020 17:30");
        assertThat(result.getExitCode(), equalTo(1));
    }


}