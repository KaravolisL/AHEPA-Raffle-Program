// TODO: Add constructor, modality and fix display method

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.beans.value.*;
import java.io.*;
import java.util.*;

public class editPrizeAlertWindow {

      private Stage window = new Stage();
      private Scene scene;
      private GridPane layout = new GridPane();
      private Label displayDuration = new Label("Display Duration (in seconds): ");
      private TextField durationInput = new TextField("" + Raffle.waitTime);
      private Text warning = new Text();

      public void display() {
            window.setTitle("Edit Prize Alerts");

            layout.addRow(0, displayDuration, durationInput);
            layout.add(warning, 0, 1, 2, 1);
            layout.setAlignment(Pos.CENTER);

            durationInput.textProperty().addListener(new ChangeListener<String>() {
                  public void changed(ObservableValue ov, String s, String s1) {
                        try {
                              Raffle.waitTime = Integer.parseInt(s1);
                              warning.setText("");
                        } catch (Exception e) {
                              warning.setText("Invalid input");
                        }
                  }
            });

            scene = new Scene(layout, 400, 175);
            layout.prefWidthProperty().bind(scene.widthProperty());
            layout.prefHeightProperty().bind(scene.heightProperty());
            window.setScene(scene);
            // Setting the window's icon
            window.getIcons().add(new Image("Icon.jpg"));
            window.showAndWait();
      }

}
