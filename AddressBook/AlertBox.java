package edu.uoregon.teamwon;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Popup an error message window when called with AlertBox.display("title", "message");
 *
 * @Author Yehui
 * Jan 24, 2017
 */
public class AlertBox {


    public static boolean confirmationBox(String title, String headerText, String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) return true;
        return false;
    }


    public static void display(String title, String message) {
        Stage window = new Stage();

        // Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button closeButton = new Button("No");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
