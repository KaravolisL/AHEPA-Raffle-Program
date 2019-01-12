import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import java.io.*;
import java.util.*;

public class warningWindow {

      static boolean answer;

      public static boolean display(String text) {
            Stage window = new Stage();
            window.setTitle("Warning");
            Text contents = new Text(text);
            contents.setTextAlignment(TextAlignment.CENTER);

            Button yesButton = new Button("Yes");
            Button noButton = new Button("No");

            HBox buttons = new HBox(yesButton, noButton);

            VBox layout = new VBox(contents, buttons);

            yesButton.setOnAction(e -> {
                  answer = true;
                  window.close();
            });

            noButton.setOnAction(e -> {
                  answer = false;
                  window.close();
            });

            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double screenHeight = bounds.getMaxY();
		double screenWidth = bounds.getMaxX();
            Scene scene = new Scene(layout, screenWidth/2, screenHeight/2);

            window.setScene(scene);
            window.showAndWait();
            return answer;
      }
}
