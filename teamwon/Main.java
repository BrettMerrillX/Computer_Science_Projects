package edu.uoregon.teamwon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage pStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        setPrimaryStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("AddressBookUI.fxml"));
        primaryStage.setTitle("Address Book");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }

    public void setPrimaryStage(Stage primaryStage){
        pStage = primaryStage;
    }

    public static Stage getPrimaryStage(){
        return pStage;
    }
}
