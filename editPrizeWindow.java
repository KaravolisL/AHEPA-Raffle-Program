import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.collections.*;
import javafx.scene.image.*;
import javafx.beans.value.*;
import java.io.*;
import java.util.*;

public class editPrizeWindow {

      public static Stage window = new Stage();
      public static Scene scene;
      public static GridPane layout;
      public static Label prizeNumber = new Label("Prize Number ");
      public static TextField prizeNumberInput = new TextField();
      public static Label currentPrizeDescriptionLabel = new Label("Current Description: ");
      public static Label currentPrizeDescription = new Label();
      public static Label newPrizeDescription = new Label("New Description ");
      public static TextField newPrizeDescriptionInput = new TextField();
      public static Button changeButton = new Button("Add/Change");
      public static Label failureMessage = new Label();
      public static Integer input = 0;

      public static void display() {
            window.setTitle("Edit Prize");
            // Creating layout and elements
            layout = new GridPane();
            layout.addRow(0, prizeNumber, prizeNumberInput);
            layout.addRow(1, currentPrizeDescriptionLabel, currentPrizeDescription);
            layout.addRow(2, newPrizeDescription, newPrizeDescriptionInput);
            layout.addRow(3, changeButton, failureMessage);
            layout.setAlignment(Pos.CENTER);
            layout.setVgap(10);
            // Adding Functionality
            // Updates currentPrizeDescription while user inputs a ticket number
            prizeNumberInput.textProperty().addListener(new ChangeListener<String>() {
                  public void changed(ObservableValue ov, String s, String s1) {

                  }
            });
            // Changing ticket name when button is pressed
            changeButton.setOnAction(e -> {
                  String newDescription = newPrizeDescriptionInput.getText();
            });
            // Removes failureMessage when new description is typed
            newPrizeDescriptionInput.textProperty().addListener(new ChangeListener<String>() {
                  public void changed(ObservableValue ov, String s, String s1) {
                        failureMessage.setText(" ");
                  }
            });

            scene = new Scene(layout, 325, 175);
            layout.prefWidthProperty().bind(scene.widthProperty());
            layout.prefHeightProperty().bind(scene.heightProperty());
            window.setScene(scene);
            window.setMinWidth(300);
            window.setMinHeight(175);
            // Setting the window's icon
            window.getIcons().add(new Image("Icon.jpg"));
            window.show();
      }



}
