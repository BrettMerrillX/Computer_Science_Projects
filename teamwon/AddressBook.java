package edu.uoregon.teamwon;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Brett
 * @date Jan 21
 */
public class AddressBook {
    private String path;
    ObservableList<Person> people;

    public AddressBook(String path) {
        this.path = path;
        people = FXCollections.observableList(new ArrayList<>());
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public void removePerson(int index) {
        people.remove(index);
    }

    public Person getPerson(int index){
        return people.get(index);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String filePath) {path = filePath; }

    /**
     * Return the number of persons in the Address Book
     * @return int
     */
    public int size(){
        return people.size();
    }

    public ObservableList<Person> getPeople() {
        return people;
    }


    public int getIndex(Person p){
        for(int i = 0; i < people.size(); i++){
            if(p == people.get(i)) return i;
        }
        return -1;
    }
}