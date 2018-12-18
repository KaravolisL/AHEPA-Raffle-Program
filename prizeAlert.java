// TODO: Remove window peripherals


import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.animation.PauseTransition;
import java.io.*;
import java.util.*;
import javafx.util.Duration;

public class prizeAlert {
      public static void display(String title, String prizeDescription) {
            int waitTime = 4;
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(title);
            Text description = new Text(prizeDescription);
            VBox layout = new VBox(description);
            layout.setAlignment(Pos.CENTER);

            // Finding screen dimensions
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double screenHeight = bounds.getMaxY();
		double screenWidth = bounds.getMaxX();
            Scene scene = new Scene(layout, screenWidth/1.5, screenHeight/1.5);
            window.setScene(scene);

            // Setting up delay, showing window, then starting delay
            PauseTransition delay = new PauseTransition(Duration.seconds(waitTime));
            delay.setOnFinished(e -> window.close());
            window.show();
            delay.play();
      }
}
