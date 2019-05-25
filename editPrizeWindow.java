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

      private Stage window = new Stage();
      private Scene scene;
      private GridPane layout;
      private Label prizeNumber = new Label("Prize Number ");
      private TextField prizeNumberInput = new TextField();
      private Label currentPrizeDescriptionLabel = new Label("Current Description: ");
      private Label currentPrizeDescription = new Label();
      private Label newPrizeDescription = new Label("New Description ");
      private TextField newPrizeDescriptionInput = new TextField();
      private Button changeButton = new Button("Add/Change");
      private Button deleteButton = new Button("Delete");
      private Label failureMessage = new Label();
      private Integer input = 0;

      public editPrizeWindow() {
            window.setTitle("Edit Prize");
            // Creating layout and elements
            layout = new GridPane();
            layout.addRow(0, prizeNumber);
            layout.addRow(1, currentPrizeDescriptionLabel);
            layout.addRow(2, newPrizeDescription);
            layout.addRow(3, changeButton, deleteButton, failureMessage);
            layout.add(prizeNumberInput, 1, 0, 2, 1);
            layout.add(currentPrizeDescription, 1, 1, 2, 1);
            layout.add(newPrizeDescriptionInput, 1, 2, 2, 1);
            layout.setAlignment(Pos.CENTER);
            layout.setVgap(10);
            currentPrizeDescription.setWrapText(true);
            GridPane.setMargin(deleteButton, new Insets(0, 10, 0 , 0));
            // Adding Functionality
            // Updates currentPrizeDescription while user inputs a ticket number
            prizeNumberInput.textProperty().addListener(new ChangeListener<String>() {
                  public void changed(ObservableValue ov, String s, String s1) {
                        try {
                              input = Integer.parseInt(s1);
                        } catch (Exception e) {
                              input = 0; // sentinal value for invalid number
                        }
                        if (s1.isEmpty()) {
                              currentPrizeDescription.setText(" ");
                              input = 0;
                        } else if (input > 0 && input < 226) {
                              currentPrizeDescription.setText("No prize associated with this number");
                              for (Prize p : Raffle.prizeInfo) {
                                    if (p.getNumber() == input) {
                                          currentPrizeDescription.setText(p.getStatement());
                                    }
                              }
                        } else {
                              currentPrizeDescription.setText("Invalid Prize Number");
                              input = 0;
                        }
                  }
            });
            // Changing ticket name when button is pressed
            changeButton.setOnAction(e -> {
                  String newDescription = newPrizeDescriptionInput.getText();
                  if (newDescription.isEmpty()) {
                        failureMessage.setText("Invalid prize description");
                  } else {
                        if (currentPrizeDescription.getText().charAt(0) == 'N') {
                              // Add Functionality
                              Raffle.prizeInfo.add(new Prize(input, newDescription));
                        } else {
                              // Edit Functionality
                              for (Prize p : Raffle.prizeInfo) {
                                    if (p.getNumber() == input) {
                                          p.setStatement(newDescription);
                                    }
                              }
                        }
                        // Work around to update the currentPrizeDescription
                        Integer tempInput = input;
                        prizeNumberInput.setText("");
                        prizeNumberInput.setText(tempInput.toString());
                  }
                  // Clearing newPrizeDescriptionInput
                  newPrizeDescriptionInput.clear();
            });
            // deleting prize when button is pressed
            deleteButton.setOnAction(e -> {
                  for (Prize p : Raffle.prizeInfo) {
                        if (p.getNumber() == input) {
                              Raffle.prizeInfo.remove(p);
                              break;
                        }
                  }
                  // Work around to update the currentPrizeDescription
                  Integer tempInput = input;
                  prizeNumberInput.setText("");
                  prizeNumberInput.setText(tempInput.toString());
                  // Clearing newPrizeDescriptionInput
                  newPrizeDescriptionInput.clear();
            });
            // Removes failureMessage when new description is typed
            newPrizeDescriptionInput.textProperty().addListener(new ChangeListener<String>() {
                  public void changed(ObservableValue ov, String s, String s1) {
                        failureMessage.setText(" ");
                  }
            });

            scene = new Scene(layout, 525, 275);
            layout.prefWidthProperty().bind(scene.widthProperty());
            layout.prefHeightProperty().bind(scene.heightProperty());
            window.setScene(scene);
            window.setMinWidth(300);
            window.setMinHeight(175);
            currentPrizeDescription.setPrefWidth(200);
            // Setting the window's icon
            window.getIcons().add(new Image("Icon.jpg"));
      }

      public void display() {
            window.show();
      }
}
