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

      public static Stage window = new Stage();
      public static Scene scene;
      public static GridPane layout;
      public static Label ticketNumber = new Label("Ticket Number");
      public static TextField ticketNumberInput = new TextField();
      public static Label currentTicketNameLabel = new Label("Current Name: ");
      public static Label currentTicketName = new Label();
      public static Label newTicketName = new Label("New Name");
      public static TextField newTicketNameInput = new TextField();
      public static Button changeButton = new Button("Change");
      public static Label failureMessage = new Label();

      public static void display() {
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
                        Integer input;
                        try {
                              input = Integer.parseInt(s1);
                        } catch (Exception e) {
                              input = 0; // sentinal value for invalid number
                        }
                        if (s1.isEmpty()) {
                              currentTicketName.setText(" ");
                        } else if (input > 0 && input < 226) {
                              currentTicketName.setText(Raffle.ticketNames.get(input-1).getName());
                        } else {
                              currentTicketName.setText("Invalid Ticket Number");
                        }
                  }
            });
            //


            scene = new Scene(layout, 300, 175);
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
