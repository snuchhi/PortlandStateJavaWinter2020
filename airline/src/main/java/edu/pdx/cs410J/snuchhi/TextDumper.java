package edu.pdx.cs410J.snuchhi;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirlineDumper;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class TextDumper implements AirlineDumper {

    private String fileName;

    /**
     * constructor
     * @param fileName - name of the file
     */
    public TextDumper(String fileName)  {

      //  if(!fileName.matches("([a-z]|[A-Z]|[0-9]|[.])*")){
        //    throw new IllegalArgumentException("File name is invalid, cannot dump");
      //  }
        this.fileName = fileName;
    }

    /**
     *
     * @param abstractAirline
     * @throws IOException
     */
    @Override
    public void dump(AbstractAirline abstractAirline) throws IOException {
        //get the name of the airline to put to dump into the file
        String airlineName = abstractAirline.getName();
        ArrayList<AbstractFlight> flightArrayList = (ArrayList<AbstractFlight>) abstractAirline.getFlights();
        try{

            File file = new File(this.fileName);
            if(!file.exists())
            {
                FileWriter writer = new FileWriter(file, true);
                BufferedWriter bfWriter = new BufferedWriter(writer);
                file.createNewFile();
                bufferWriter(airlineName, flightArrayList, writer, bfWriter);
            }
            else
            {
                FileWriter writer = new FileWriter(file);
                BufferedWriter bfWriter = new BufferedWriter(writer);
                bufferWriter(airlineName, flightArrayList, writer, bfWriter);
            }

        }catch(IOException e){
            File file = new File(this.fileName);
            FileWriter writer = new FileWriter(file, true);
            BufferedWriter bfWriter = new BufferedWriter(writer);
            file.createNewFile();
            bufferWriter(airlineName, flightArrayList, writer, bfWriter);
        }

    }

    /**
     *
     * @param airlineName - the name fo airline
     * @param flightArrayList - the flight details
     * @param writer - write to the text file
     * @param bfWriter - buffer writer to the text file
     * @throws IOException
     */
    private void bufferWriter(String airlineName, ArrayList<AbstractFlight> flightArrayList, FileWriter writer, BufferedWriter bfWriter) throws IOException {
        bfWriter.write(airlineName);
        bfWriter.newLine();
        for (AbstractFlight abstractFlight : flightArrayList) {
            bfWriter.write(abstractFlight.getNumber() + " " + abstractFlight.getSource() + " "
                    + abstractFlight.getDepartureString() + " " + abstractFlight.getDestination() + " "
                    + abstractFlight.getArrivalString() + " ");
            bfWriter.newLine();
        }
        bfWriter.close();
        writer.close();
        System.out.println("Airline contents dumped to file");
    }
}
