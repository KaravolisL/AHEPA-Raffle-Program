import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.io.*;
import java.util.*;

public class colorPickerWindow {

      private Stage window = new Stage();
      private Scene scene;
      private Paint color;
      private Label tableColor = new Label("Table Color: ");
      private Label alertColor = new Label("Prize Alert Color: ");
      private ColorPicker tableColorPicker = new ColorPicker();
      private ColorPicker alertColorPicker = new ColorPicker();
      private Button tableColorChange = new Button("Change");
      private Button alertColorChange = new Button("Change");
      private GridPane layout;

      public colorPickerWindow() {
            window.setTitle("Background Color");
            // Creating and styling layout and elements
            layout = new GridPane();
            layout.addRow(0, tableColor, tableColorPicker, tableColorChange);
            layout.addRow(1, alertColor, alertColorPicker, alertColorChange);
            layout.setAlignment(Pos.CENTER);
            layout.setVgap(10);
            // Adding Functionality
            // Changes main table color
            tableColorChange.setOnAction(e -> {
                  Raffle.tableBackgroundColor = tableColorPicker.getValue();
                  Raffle.restyle();
            });

            alertColorChange.setOnAction(e -> {
                  Raffle.alertBackgroundColor = alertColorPicker.getValue();
                  Raffle.restyle();
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
            window.showAndWait();
      }
}
