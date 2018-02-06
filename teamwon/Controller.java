package edu.uoregon.teamwon;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Stack;
import java.util.function.Predicate;


public class Controller {

    @FXML
    private ListView namesListView;
    @FXML
    private TableView namesTableView;
    @FXML
    private GridPane infoGridPane;
    @FXML
    private Button addContactButton;
    @FXML
    private Button removeContactButton;
    @FXML
    private Button editMenuItem;
    @FXML
    private TextField searchField;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label address1Label;
    @FXML
    private Label address2Label;
    @FXML
    private Label cityLabel;
    @FXML
    private Label stateLabel;
    @FXML
    private Label zipLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;

    //observable list will update table in gui when modified.
    private ObservableList<Person> namesList;
    //currently selected AddressBookEntry to be displayed
    private Person selectedEntry;

    private String searchText;

    private AddressBook currentAddressBook;

    private Stack<CheckPoint> checkpoints;

    public Controller() {
        checkpoints = new Stack<>();

        currentAddressBook = new AddressBook(null);
        namesList = currentAddressBook.getPeople();
    }

    /**
     * Gets called when object gets created, after objects are inserted into @FXML fields.
     */
    @FXML
    private void initialize() {
        //namesTableView uses sortedNamesList when search is empty, searchNamesList otherwise.
        FilteredList<Person> filteredNamesList = new FilteredList<>(namesList);
        SortedList<Person> searchedNamesList = new SortedList<>(filteredNamesList);

        SortedList<Person> sortedNamesList = new SortedList<>(namesList);
        sortedNamesList.comparatorProperty().bind(namesTableView.comparatorProperty());

        namesTableView.setItems(sortedNamesList);
        namesTableView.setSortPolicy(new Callback<TableView, Boolean>() {
            @Override
            public Boolean call(TableView table) {
                if(searchText == null || searchText.equals("")) {
                    return TableView.DEFAULT_SORT_POLICY.call(table);
                }
                System.out.println("sort \"" + searchText + "\"");
                return true;
            }
        });

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchText = newValue;
                if(searchText == null || searchText.equals("")) {
                    namesTableView.setItems(sortedNamesList);
                }
                else {
                    //these are set in here so they get updated when search text changes.
                    filteredNamesList.setPredicate(new Predicate<Person>() {
                        @Override
                        public boolean test(Person person) {
                            return person.matchesString(searchText) != 0;
                        }
                    });
                    searchedNamesList.setComparator(new Comparator<Person>() {
                        @Override
                        public int compare(Person o1, Person o2) {
                            return o2.matchesString(searchText) - o1.matchesString(searchText);
                        }
                    });
                    namesTableView.setItems(searchedNamesList);
                }
                namesTableView.sort();
            }
        });

        TableColumn<Person, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName"));
        TableColumn<Person, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName"));
        TableColumn<Person, String> zipcodeColumn = new TableColumn<>("Zip Code");
        zipcodeColumn.setCellValueFactory(new PropertyValueFactory("zip"));

        namesTableView.getColumns().addAll(firstNameColumn, lastNameColumn, zipcodeColumn);
        namesTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
            @Override
            public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
                selectedEntry = newValue;
                updateEntryView();
            }
        });

        editMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editPerson();
            }
        });


        addContactButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Person newPerson = new Person("","");
                boolean okClicked = showPersonPopup(newPerson);
                if(okClicked){
                    addContact(newPerson);
                }

            }
        });

        removeContactButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeContact();
            }
        });

        updateEntryView();

    }
    //==============================================================
    // end.
    //==============================================================
    //++++
    //==============================================================
    //Author: Sherry Ma
    // Description:
    //==============================================================
    private void refreshPersonTable() {
        int selectedIndex = namesTableView.getSelectionModel().getSelectedIndex();
        namesTableView.setItems(null);
        namesTableView.layout();
        namesTableView.setItems(currentAddressBook.getPeople());
        // Must set the selected index again (see http://javafx-jira.kenai.com/browse/RT-26291)
        namesTableView.getSelectionModel().select(selectedIndex);
    }
    //==============================================================
    // end.
    //==============================================================
    // ++++
    //==============================================================
    /**
     * Opens a dialog to edit or add information for specific person
     * @param person the person added or edited
     * @return true if the user click ok, false otherwise
     * Author: Sherry Ma
     */
    //==============================================================
    public boolean showPersonPopup(Person person){
        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add/Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(Main.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PersonPopupController personController = loader.getController();
            personController.setDialogStage(dialogStage);
            personController.setPerson(person);

            dialogStage.showAndWait();
            //Click ok then save, otherwise false
            return personController.isOkClicked();


        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    //==============================================================
    // end.
    //==============================================================
    //++++
    //==============================================================
    //Author: Sherry Ma
    //==============================================================

    //Author: Sherry Ma
    private void editPerson(){
        if(selectedEntry != null) {

            //Checkpoint starts
            Person p = new Person();
            p.copyValues(selectedEntry);
            CheckPoint cp = new CheckPoint(p,selectedEntry, CPStatus.Edit);
            //Checkpoint ends

                boolean okClicked = showPersonPopup(selectedEntry);

                if (okClicked) {
                    checkpoints.push(cp);

                    refreshPersonTable();
                    namesTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
                        @Override
                        public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
                            selectedEntry = newValue;
                            updateEntryView();
                        }
                    });

                } else {

                    //Dialogs.showWarningDialog(Main.getPrimaryStage(),
                    //        "Please select a person in the table.",
                    //        "No Person Selected", "No Selection");
                }
        }
    }
    //==============================================================
    // end.
    //==============================================================

    //==============================================================
    // Author: Logan??
    // Description:
    //==============================================================

    private void addContact(Person person) {
        checkpoints.push(new CheckPoint(person, CPStatus.Add));
        currentAddressBook.addPerson(person);
        namesTableView.getSelectionModel().selectLast();
    }

    private void removeContact() {
        if(selectedEntry != null) {
            int person_index = namesTableView.getSelectionModel().getSelectedIndex();
            if(namesTableView.getItems() instanceof SortedList) {
                person_index = ((SortedList)namesTableView.getItems()).getSourceIndex(person_index);
            }
            checkpoints.push(new CheckPoint(currentAddressBook.getPerson(person_index), CPStatus.Delete));
            currentAddressBook.removePerson(person_index);
        }
    }

    private void updateEntryView() {
        if(selectedEntry == null) {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            address1Label.setText("");
            address2Label.setText("");
            cityLabel.setText("");
            stateLabel.setText("");
            zipLabel.setText("");
            phoneLabel.setText("");
            emailLabel.setText("");
            infoGridPane.setVisible(false);
        }
        else {
            firstNameLabel.setText(selectedEntry.getFirstName());
            lastNameLabel.setText(selectedEntry.getLastName());
            address1Label.setText(selectedEntry.getStreetAddress1());
            address2Label.setText(selectedEntry.getStreetAddress2());
            cityLabel.setText(selectedEntry.getCity());
            stateLabel.setText(selectedEntry.getState());
            zipLabel.setText(selectedEntry.getZip());
            phoneLabel.setText(formatPhoneNumber(selectedEntry.getPhoneNumber()));
            emailLabel.setText(selectedEntry.getEmail());
            infoGridPane.setVisible(true);
        }
    }

    private String formatPhoneNumber(String input) {
        if(input == null) {
            return "";
        }
        if(input.length() == 7) {
            return input.substring(0, 3) + "-" + input.substring(3, 7);
        }
        if(input.length() == 10) {
            return "(" + input.substring(0, 3) + ")" + formatPhoneNumber(input.substring(3));
        }
        if(input.length() == 11) {
            return input.charAt(0) + formatPhoneNumber(input.substring(1));
        }
        return input;
    }
    //==============================================================
    // end.
    //==============================================================

    //==============================================================

    /**
     * @author Yehui
     * @date Jan 29
     * @param event
     */
    public void action_undo(ActionEvent event){

        System.out.println("The size of checkpoint is " + checkpoints.size());
        if(checkpoints.empty()){
            AlertBox.display("No more undos", "You have reached the initial state. ");
            return;
        }
        CheckPoint cp = checkpoints.pop();
        if(cp.getStatus().equals(CPStatus.Delete)){
            currentAddressBook.addPerson(cp.getPrev());
        }
        else if(cp.getStatus().equals(CPStatus.Add)){
            currentAddressBook.removePerson(currentAddressBook.size()-1);
        }
        else{
            int index = currentAddressBook.getIndex(cp.getCurr());
            if(index == -1){
                System.err.println("Error occur in action_undo function");
            }
            else{
                currentAddressBook.removePerson(index);
                currentAddressBook.addPerson(cp.getPrev());
            }
        }
    }

    public void action_About(ActionEvent event){
        AlertBox.display("About", "This application was developed by students from Team 1 of course CIS 422/522 in Winter 2018");
    }

    //==============================================================
    // end
    //==============================================================

    //++++
    //==============================================================
    // Author: Brett Merrill
    // Save features.
    //==============================================================
    // Creates a new Blank AddressBook
    public void newAddressBook(){
        checkpoints = new Stack<>();
        currentAddressBook = new AddressBook(null);
        namesList = currentAddressBook.getPeople();
        //namesTableView uses sortedNamesList when search is empty, searchNamesList otherwise.
        FilteredList<Person> filteredNamesList = new FilteredList<>(namesList);
        SortedList<Person> searchedNamesList = new SortedList<>(filteredNamesList);
        SortedList<Person> sortedNamesList = new SortedList<>(namesList);
        sortedNamesList.comparatorProperty().bind(namesTableView.comparatorProperty());
        namesTableView.setItems(sortedNamesList);
        refreshPersonTable();
        updateEntryView();
    }

    // Chooses the filename to be saved
    public void action_importCSV1(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        File dest = fileChooser.showSaveDialog(null);
        System.out.println(dest);
        currentAddressBook.setPath(dest.getPath().toString());
        saveAddressBook();

    }

    // Saves currentaddressbook
    public void saveAddressBook(){
        if(currentAddressBook.getPath() != null ) {
            CsvFileWriter.writeCsvFile(currentAddressBook.getPath(), currentAddressBook);
        }else{
            action_importCSV1();
        }
    }
    //==============================================================
    // end.
    //==============================================================

    private void addContactsFromFile(File file) {
        AddressBook newBook = new CsvFileReader().reader(file.getAbsolutePath());
        for(Person np: newBook.people){
            namesList.add(np);
        }
    }

    /**
     * Opens new window, and if inputFile is not null, loads all contacts from it.
     * @param inputFile
     */
    private void newWindow(File inputFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddressBookUI.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Address Book");
            stage.setScene(new Scene(root, 800, 600));
            if(inputFile != null) {
                Controller newController = loader.getController();
                newController.currentAddressBook.setPath(inputFile.getAbsolutePath());
                newController.addContactsFromFile(inputFile);
            }
            stage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    //==============================================================
    //==============================================================
    // Here is the beginning of functions for menu items.

    /**
     * "Address Book" => "Load"
     * @author Yehui
     * @date Jan 24
     * @revision Jan 30
     */
    @FXML
    public void handleLoad(){
        /*
         Ask user to get the path of the file they want to open.
         If the file is not a csv file, pop up an alert box.
         If the user did not select a file, return.
          */
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = fileChooser.showOpenDialog(null);
        if(file == null)
            return;
        file.getAbsolutePath();
        String[] fileNameArray = file.getName().split("\\.");
        if(!fileNameArray[fileNameArray.length -1].equals("csv")){
            AlertBox.display("File Type Error!", "The file you selected is invalid.\nPlease select a csv file.");
            return;
        }

        /*
        If currently the address book object had entry in it, then we should open a new window to display the loaded address book.
        Else, just load in the current window.
         */
        if(currentAddressBook.size() != 0){
            //TODO: if the current address book has entries, then we want the loaded address book to be displayed in a new window.
            newWindow(file);
        }
        else{
            currentAddressBook.setPath(file.getAbsolutePath());
            addContactsFromFile(file);
        }
    }

    @FXML
    private void handleNew() {
        newWindow(null);
    }

    /**
     * "Address Book" => "Import/Merge"
     * @author Yehui
     * @date Jan 30
     */
    @FXML
    private void handleMerge(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = fileChooser.showOpenDialog(null);

        if(file == null)
            return;
        file.getAbsolutePath();
        String[] fileNameArray = file.getName().split("\\.");
        if(!fileNameArray[fileNameArray.length -1].equals("csv")){
            AlertBox.display("File Type Error!", "The file you selected is invalid.\nPlease select a csv file.");
            return;
        }

        addContactsFromFile(file);
    }

    /**
     * "Address Book" => "Save"
     * @author Yehui
     */
    @FXML
    private void handleSave(){
        if(currentAddressBook.size() == 0 || checkpoints.size() == 0){
            return;
        }
        // The current address book has not been saves as a file
        System.out.println(currentAddressBook.getPath());
        if(currentAddressBook.getPath() == null){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");
            File dest = fileChooser.showSaveDialog(null);
            if(dest == null) return;
            System.out.println(dest);
            currentAddressBook.setPath(dest.getPath().toString());
            saveAddressBook();
        }
        else{
            saveAddressBook();
        }

    }


    /**
     * "Address Book" => "Save As"
     * @author Yehui
     */
    @FXML
    private void handleSaveAs(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        File dest = fileChooser.showSaveDialog(null);
        if(dest == null) return;
        System.out.println(dest);
        currentAddressBook.setPath(dest.getPath().toString());
        saveAddressBook();
    }

    /**
     * "Address Book" => "Quit"
     * @author Yehui
     */
    @FXML
    private void handleQuit(){
        // If no change was made, quit immediately. Else, let user to decide whether to save or not. Then quit.
        if (checkpoints.size() == 0) {
            System.exit(0);
            return;
        }
        boolean saveChanges = AlertBox.confirmationBox("Alert","You have made changes to the file.","Do you want to save changes to the file?");
        if(saveChanges)
            handleSave();
        System.exit(0);
    }

    //==============================================================
    // end.
    //==============================================================


}
