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


public class editTicketWindow {

      private Stage window = new Stage();
      private Scene scene;
      private GridPane layout;
      private Label ticketNumber = new Label("Ticket Number");
      private TextField ticketNumberInput = new TextField();
      private Label currentTicketNameLabel = new Label("Current Name: ");
      private Label currentTicketName = new Label();
      private Label newTicketName = new Label("New Name");
      private TextField newTicketNameInput = new TextField();
      private Button changeButton = new Button("Change");
      private Label failureMessage = new Label();
      private Integer input = 0;

      public editTicketWindow() {
            window.setTitle("Edit Ticket");
            // Creating layout and elements
            layout = new GridPane();
            layout.addRow(0, ticketNumber, ticketNumberInput);
            layout.addRow(1, currentTicketNameLabel, currentTicketName);
            layout.addRow(2, newTicketName, newTicketNameInput);
            layout.addRow(3, changeButton, failureMessage);
            layout.setAlignment(Pos.CENTER);
            layout.setVgap(10);
            // Adding Functionality
            // Updates currentTicketName while user inputs a ticket number
            ticketNumberInput.textProperty().addListener(new ChangeListener<String>() {
                  public void changed(ObservableValue ov, String s, String s1) {
                        try {
                              input = Integer.parseInt(s1);
                        } catch (Exception e) {
                              input = 0; // sentinal value for invalid number
                        }
                        if (s1.isEmpty()) {
                              currentTicketName.setText(" ");
                              input = 0;
                        } else if (input > 0 && input < 226) {
                              currentTicketName.setText(Raffle.ticketNames.get(input-1).getName());
                        } else {
                              currentTicketName.setText("Invalid Ticket Number");
                              input = 0;
                        }
                  }
            });
            // Changing ticket name when button is pressed
            changeButton.setOnAction(e -> {
                  String newName = newTicketNameInput.getText();
                  if (newName.isEmpty()) {
                        failureMessage.setText("Invalid Ticket Name");
                  } else {
                        if (input != 0) {
                              Raffle.ticketNames.get(input-1).setName(newName);
                              Raffle.ticketText[input-1].setText((input) + "\n" + newName);
                        }
                        // Work around to update the currentTicketName
                        Integer tempInput = input;
                        ticketNumberInput.setText("");
                        ticketNumberInput.setText(tempInput.toString());
                  }
            });
            // Removes failureMessage when new name is typed
            newTicketNameInput.textProperty().addListener(new ChangeListener<String>() {
                  public void changed(ObservableValue ov, String s, String s1) {
                        failureMessage.setText(" ");
                  }
            });

            scene = new Scene(layout, 300, 175);
            layout.prefWidthProperty().bind(scene.widthProperty());
            layout.prefHeightProperty().bind(scene.heightProperty());
            window.setScene(scene);
            window.setMinWidth(300);
            window.setMinHeight(175);
            // Setting the window's icon
            window.getIcons().add(new Image("Icon.jpg"));
      }

      public void display() {
            window.show();
      }
}
