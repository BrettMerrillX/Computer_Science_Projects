package edu.uoregon.teamwon;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Sherry Ma
 * @revision Yehui Yehui Jan 28
 *      > Changed State input box to a choice box. Now this application only accepts U.S. States.
 *      > Implemented inputValidation method. For now, if any fields has invalid data, an alert will pop up when user is trying to save the change.
 */
public class PersonPopupController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField firstAddressField;
    @FXML
    private TextField secondAddressField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField stateField;
    @FXML
    private TextField zipCodeField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;



    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        firstAddressField.setText(person.getStreetAddress1());
        secondAddressField.setText(person.getStreetAddress2());
        cityField.setText(person.getCity());

        stateField.setText(person.getState());
        zipCodeField.setText(person.getZip());
        phoneField.setText(person.getPhoneNumber());
        emailField.setText(person.getEmail());
    }

     /**
     * Returns true if the user clicked OK, false otherwise.
     * @return
     */
      public boolean isOkClicked(){
             return okClicked;
      }

    public PersonPopupController() {
    }

    /**
      * Called when the user clicks ok.
      */
      @FXML
      private void handleOk() {
          Person p = new Person();
          p.setFirstName(firstNameField.getText());
          p.setLastName(lastNameField.getText());
          p.setStreetAddress1(firstAddressField.getText());
          p.setStreetAddress2(secondAddressField.getText());
          p.setCity(cityField.getText());

          String state_reform = reformatState(stateField.getText());
          p.setState(state_reform);
          p.setZip(zipCodeField.getText());

          p.setPhoneNumber(phoneField.getText());
          p.setEmail(emailField.getText());

          String validationOutput = p.validationStatus();
          if(validationOutput.length() == 0){
              person.copyValues(p);
              okClicked = true;
              dialogStage.close();
          }
          else{
              Boolean saveAnyway = AlertBox.confirmationBox("Invalid Input values", "We have detected that following fields are " +
                      "either missing or do not follow the our input standards.\n" +
                      "Please refer to the user's manual for details. \n\n\n"
                      + validationOutput, "Do you want to proceed and save?");
              if(saveAnyway){
                  //this.person = p;
                  person.copyValues(p);
                  okClicked = true;
                  dialogStage.close();
              }
          }
      }

    /**
     *  Called when user clicks cancel.
     */
    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    /**
     * Return the person created or edited.
     */
    private Person getPerson(){
        return person;
    }


    //TODO: Reformat State
    private String reformatState(String state){
        return state;
    }
}
