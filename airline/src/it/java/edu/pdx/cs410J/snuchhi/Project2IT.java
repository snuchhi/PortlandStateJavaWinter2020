package edu.pdx.cs410J.snuchhi;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.File;

public class Project2IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private InvokeMainTestCase.MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testNoCommandLineArguments() {
        InvokeMainTestCase.MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing command line arguments\n" +
                        "args are (in this order):\n" +
                        "\tAirline - name of airline\n" +
                        "\tflightNumber\n" +
                        "\tsrc - Three-letter code of departure\n" +
                        "\tdepart - Departure date and time (24-hour time)\n" +
                        "\tdest - Three-letter code of arrival airport\n" +
                        "\tarrive - Arrival date and time (24-hour time)\n" +
                        "\toptions are (options may appear in any order):\n" +
                        "\t-textFile fileName\n" +
                        "\t-print\n" +
                        "\t-README"));
    }
    /**
     * tests that invoking main method with less command line arguments
     */
    @Test
    public void testWithLessCommandLineArguments()
    {
        InvokeMainTestCase.MainMethodResult result = invokeMain("CS410J", "42");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing command line arguments ,too few command line arguments, please enter command line arguments\n" +
                        "args are (in this order):\n" +
                        "\tAirline - name of airline\n" +
                        "\tflightNumber\n" +
                        "\tsrc - Three-letter code of departure\n" +
                        "\tdepart - Departure date and time (24-hour time)\n" +
                        "\tdest - Three-letter code of arrival airport\n" +
                        "\tarrive - Arrival date and time (24-hour time)\n" +
                        "\toptions are (options may appear in any order):\n" +
                        "\t-textFile fileName\n" +
                        "\t-print\n" +
                        "\t-README"));

    }
    /**
     * tests that invoking main method with more command line arguments
     */
    @Test
    public void testWithManyCommandLineArguments()
    {
        InvokeMainTestCase.MainMethodResult result = invokeMain("CS410J", "42", "lax", "01/22/2020" , "10:30","AM" ,"pdx", "01/22/2020", "11:30","AM","mam");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Additional command line arguments present please reenter"));
    }

    /**
     * test that invokes with correct number of command line arguments
     */
    @Ignore
    @Test
    public void testWithCorrectCommandLineArgumentsAndPrintOption() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print", "CS410J", "42", "lax", "01/22/2020", "01:30" ,"AM", "pdx", "01/22/2020", "02:30", "AM");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 42 departs lax at 01/22/2020 01:30 AM arrives pdx at 01/22/2020 02:30 AM"));
    }

    /**
     * test that invokes with correct number of command line arguments and readme option
     */
    @Test
    public void testWithCorrectCommandLineArgumentsAndReadMeOption() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-README", "CS410J", "42", "lax", "01/22/2020", "10:30 AM", "pdx", "01/22/2020", "11:30 AM");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("" +
                "This is Project2 for CS510 Advanced Java Course which aims at designing an Airline Application" +
                "\tCreated/Developed by: Shruti Nuchhi" + "\n" +
                "The project aims at created classes namely TextDumper and TextParser extended from Abstract classes. \n The text dumper will dump the" +
                "contents of the airline details to text file. \n The parser is responsible for parsing the text file contents and creating the flight." +
                "If the -print option is provided then the details of the flight will be displayed. \n If -README is specified then README is displayed " +
                "and then program exit gracefully. "));
    }

    /**
     * test that invokes with correct number of command line arguments and no read me option
     */
    @Test
    public void testWithCorrectCommandLineArgumentsAndWithoutOption() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("CS410J", "42", "lax", "01/22/2020 15:30", "pdx", "01/22/2020 17:30");
        assertThat(result.getExitCode(), equalTo(1));
    }
    /**
     * test that invokes with less number of command line arguments with print option
     */
    @Test
    public void testWithLessCommandLineArgumentsAndWithPrintOption() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print", "CS410J", "42", "lax", "01/22/2020 15:30", "pdx");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing command line arguments ,too few command line arguments, please enter command line arguments\n" +
                        "args are (in this order):\n" +
                        "\tAirline - name of airline\n" +
                        "\tflightNumber\n" +
                        "\tsrc - Three-letter code of departure\n" +
                        "\tdepart - Departure date and time (24-hour time)\n" +
                        "\tdest - Three-letter code of arrival airport\n" +
                        "\tarrive - Arrival date and time (24-hour time)\n" +
                        "\toptions are (options may appear in any order):\n" +
                        "\t-textFile fileName\n" +
                        "\t-print\n" +
                        "\t-README"  ));
    }

    /**
     * test that invokes with many number of command line arguments with print option
     */
    @Test
    public void testWithManyCommandLineArgumentsAndWithPrintOption() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print", "CS410J", "42", "lax", "01/22/2020", "15:30", "pdx", "01/22/2020", "17:30", "xxxx");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Additional command line arguments present please reenter"));
    }

    /**
     * test that invokes with many number of command line arguments with print and readMe option
     */
    @Test
    public void testWithCorrectCommandLineArgumentsAndWithPrintAndReadMeOption() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-print", "-ReadMe", "CS410J", "42", "lax", "01/22/2020", "15:30", "pdx", "01/22/2020" , "17:30");
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void testTextFileOptionWithCorrectArguments(){
        InvokeMainTestCase.MainMethodResult result = invokeMain("-textFile", "test.txt", "CS410J", "42", "lax", "01/22/2020", "10:30","AM", "pdx", "01/22/2020" , "11:30", "PM");
        assertThat(result.getExitCode(), equalTo(1));
        File testFile = new File("test.txt");
        if(testFile.delete()){
            System.out.println("Created file for testing and deleting");
        }else{
            System.out.println("Testing for texFile with correct arguments failed");
        }
    }

    @Test
    public void testTextFileWithLessArguments(){
        MainMethodResult result = invokeMain("-textFile", "test.txt", "CS410J", "42", "pdx");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(
                "Missing command line arguments ,too few command line arguments, please enter command line arguments\n" +
                        "args are (in this order):\n" +
                        "\tAirline - name of airline\n" +
                        "\tflightNumber\n" +
                        "\tsrc - Three-letter code of departure\n" +
                        "\tdepart - Departure date and time (24-hour time)\n" +
                        "\tdest - Three-letter code of arrival airport\n" +
                        "\tarrive - Arrival date and time (24-hour time)\n" +
                        "\toptions are (options may appear in any order):\n" +
                        "\t-textFile fileName\n" +
                        "\t-print\n" +
                        "\t-README"));

    }
    @Test
    public void testTextFileWithManyCommandLineArgumentsAndWithPrintOption() {
        InvokeMainTestCase.MainMethodResult result = invokeMain("-textFile", "test.txt","print" ,"CS410J", "42", "lax", "01/22/2020", "15:30", "pdx", "01/22/2020", "17:30", "xxxx");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Additional command line arguments present please reenter"));
    }

}
