import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.image.*;
import java.io.*;
import java.util.*;

public class aboutWindow {

      public static void display() {
            Stage window = new Stage();
            window.setTitle("About");
            Text contents = new Text();
            contents.setText("About this program");
            contents.setTextAlignment(TextAlignment.CENTER);

            VBox layout = new VBox(contents);

            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double screenHeight = bounds.getMaxY();
		double screenWidth = bounds.getMaxX();
            Scene scene = new Scene(layout, screenWidth/2, screenHeight/2);

            window.setScene(scene);
            // Setting the window's icon
            window.getIcons().add(new Image("Icon.jpg"));
            window.show();
      }
}
