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

  @Test
    public void testLessCommandLineArguments()
  {
      MainMethodResult result = invokeMain("Alaska", "42");
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments please enter command line arguments"));
  }

    @Test
    public void testManyCommandLineArguments()
    {
        MainMethodResult result = invokeMain("Alaska", "42", "asa", "00/00/000000:00" ,"pdx", "00/00/000000:00","mam");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Additional command line arguments present please reenter"));
    }

    @Test
    public void testCorrectCommandLineArgumentsWithPrintOption() {
        MainMethodResult result = invokeMain("-print", "Portland", "00", "pdx", "11/11/111111:11", "sfx", "22/22/222222:22");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 0 departs pdx at 11/11/111111:11 arrives sfx at 22/22/222222:22"));
    }

    @Test
    public void testCorrectCommandLineArgumentsWithReadMeOption() {
        MainMethodResult result = invokeMain("-ReadMe", "Portland", "00", "pdx", "11/11/111111:11", "sfx", "22/22/222222:22");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("" +
                "This is Project for CS510 Advanced Java Course which aims at designing an Airline Application\tCreated/Developed by user:Shruti Nuchhi\n" +
                "The project aims at created classes namely Flight and Airline from the extended from Abstract classes and creates a flight\n" +
                "for a given airline.The project also creates a project1.java class which implements the main function and accepts the \n" +
                "command line arguments. There are various checks performed to look for accurate command line arguments and display an error\n" +
                "message if not. The project also provides an optional ability to print the details for an airline or print the ReadMe and exit."));
    }

    @Test
    public void testCorrectCommandLineArgumentsWithoutOption() {
        MainMethodResult result = invokeMain("Portland", "00", "pdx", "11/11/111111:11", "sfx", "22/22/222222:22");
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void testLessCommandLineArgumentsWithPrintOption() {
        MainMethodResult result = invokeMain("-print", "Portland", "00", "pdx");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void testManyCommandLineArgumentsWithPrintOption() {
        MainMethodResult result = invokeMain("-print", "Portland", "00", "pdx", "11/11/1111 11:11", "sfx", "22/22/2222 22:22", "heyyy");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Additional command line arguments present please reenter"));
    }


}