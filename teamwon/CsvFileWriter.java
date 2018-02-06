package edu.uoregon.teamwon;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Brett
 * @date Jan 21
 */
public class CsvFileWriter {

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    //private static final String FILE_HEADER = "id,firstName,lastName";
    public static void writeCsvFile(String fileName, AddressBook currentAddressBook) {

        //Create a new list of Person objects
        List<Person> people = new ArrayList(currentAddressBook.people);

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName, false);
            //fileWriter.append(FILE_HEADER.toString());
            //Add a new line separator after the header
            //fileWriter.append(NEW_LINE_SEPARATOR);

            StringBuilder sb = new StringBuilder();
            for(Person person: people){
                if(person.getFirstName() != null){
                    sb.append(person.getFirstName());
                }else{
                    sb.append("");
                }
                sb.append(COMMA_DELIMITER);
                if(person.getLastName() != null){
                    sb.append(person.getLastName());
                }else {
                    sb.append("");
                }
                sb.append(COMMA_DELIMITER);
                if(person.getStreetAddress1() != null){
                    sb.append(person.getStreetAddress1());
                }else {
                    sb.append("");
                }
                sb.append(COMMA_DELIMITER);
                if(person.getStreetAddress2() != null){
                    sb.append(person.getStreetAddress2());
                }else {
                    sb.append("");
                }
                sb.append(COMMA_DELIMITER);
                if(person.getCity() != null){
                    sb.append(person.getCity());
                }else {
                    sb.append("");
                }
                sb.append(COMMA_DELIMITER);
                if(person.getState() != null){
                    sb.append(person.getState());
                }else {
                    sb.append("");
                }
                sb.append(COMMA_DELIMITER);
                if(person.getZip() != null){
                    sb.append(person.getZip());
                }else {
                    sb.append("");
                }
                sb.append(COMMA_DELIMITER);
                if(person.getPhoneNumber() != null){
                    sb.append(person.getPhoneNumber());
                }else {
                    sb.append("");
                }
                sb.append(COMMA_DELIMITER);
                if(person.getEmail() != null){
                    sb.append(person.getEmail());
                }else {
                    sb.append("");
                }
                sb.append(NEW_LINE_SEPARATOR);
            }
            fileWriter.append(sb.toString());
            System.out.println("CSV file was created");
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter");
                e.printStackTrace();
            }
        }
    }

    /**
     * Testing
     * @param args
     */
    public static void main(String[] args) {
        String inputPath = "data/book1.csv";
        String outputPath = "data/bookOut1.csv";
        AddressBook book1 = new CsvFileReader().reader(inputPath);
        CsvFileWriter.writeCsvFile(outputPath, book1);
    }
}