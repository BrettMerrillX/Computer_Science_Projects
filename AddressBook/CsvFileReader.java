package edu.uoregon.teamwon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Import csv file from local machine to back end.
 * @author Brett
 * @date Jan 21
 * @revision Yehui: Changed constructor and some instance variables. Jan-24
 *
 */
public class CsvFileReader {

    public AddressBook reader(String filePath){
        AddressBook book = new AddressBook(filePath);
        String line = "";

        AmericanStates stateChecker = new AmericanStates();

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String header = br.readLine();
            while((line = br.readLine()) != null){
                String[] p = line.split(",", 10);
                String state = p[5];
                if(stateChecker.lower_to_formal.containsKey(state.toLowerCase()))
                    state = stateChecker.lower_to_formal.get(state.toLowerCase());
                else if(stateChecker.abbre_to_full.containsKey(state))
                    state = stateChecker.abbre_to_full.get(state);

                book.people.add(new Person(p[0], p[1], p[2], p[3], p[4], state, p[6], p[7], p[8]));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return book;
    }
}