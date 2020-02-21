package edu.pdx.cs410J.snuchhi;

import edu.pdx.cs410J.ParserException;

import java.io.IOException;

/**
 *
 */
public class Converter {
    private String textFile = null;
    private String xmlFile = null;

    public Converter(String textFile, String xmlFile) {
        String error = null;
        try {
            if (!textFile.contains(".txt")) {
                error = "first command should be .txt";
                throw new IllegalArgumentException();
            }
            if (!xmlFile.contains(".xml")) {
                error = "second command should be .xml";
                throw new IllegalArgumentException();
            }
            this.textFile = textFile;
            this.xmlFile = xmlFile;
        } catch (IllegalArgumentException e) {
            System.err.println(error);
            throw new IllegalArgumentException();
        }
    }

    public void convert() throws ParserException, IOException {
        try {
            TextParser textToConvert = new TextParser(this.textFile);
            Airline airline = (Airline) textToConvert.parse();
            XmlDumper airlineToDump = new XmlDumper(this.xmlFile);
            airlineToDump.dump(airline);
        }catch(NullPointerException e){
            System.err.println("file not found");
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) throws IOException, ParserException {
        //error if there are NO command line arguments
        if(args.length <= 0) {
            System.err.println("No command line args found.\n");

            System.exit(1);
        }
        if(args.length > 2) {
            System.err.println("Too many command line args.\n");

            System.exit(1);
        }
        try {
            Converter toConvert = new Converter(args[0], args[1]);
            toConvert.convert();
            System.exit(1);
        }catch(IllegalArgumentException e){
            System.err.println("Unable to convert");
            System.exit(1);
        }
    }
}