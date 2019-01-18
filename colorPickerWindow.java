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

      static Paint color;

      public static Paint display() {
            Stage window = new Stage();
            window.setTitle("Background Color");

            ColorPicker myColorPicker = new ColorPicker();

            HBox layout = new HBox(myColorPicker);

            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double screenHeight = bounds.getMaxY();
		double screenWidth = bounds.getMaxX();
            Scene scene = new Scene(layout, screenWidth/2, screenHeight/2);

            window.setOnCloseRequest(e -> color = myColorPicker.getValue());

            window.setScene(scene);
            // Setting the window's icon
            window.getIcons().add(new Image("Icon.jpg"));
            window.showAndWait();
            return color;
      }
}
