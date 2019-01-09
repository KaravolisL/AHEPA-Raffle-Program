import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.input.*;
import javafx.animation.PauseTransition;
import java.io.*;
import java.util.*;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.event.ActionEvent; 

public class prizeAlert {
      public static int waitTime = 4;
      public static int FONT_SIZE = 20;
      public static BackgroundFill BACKGROUND_COLOR = new BackgroundFill(Color.VIOLET, new CornerRadii(0), new Insets(0));

      public static void display(String title, String prizeDescription) {
            Stage window = new Stage(StageStyle.UNDECORATED);
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(title);
            Text description = new Text(prizeDescription);
            // Styling of description
            description.setFont(new Font(FONT_SIZE));
            VBox layout = new VBox(description);
            // Styling of layout
            layout.setAlignment(Pos.CENTER);
            layout.setBackground(new Background(BACKGROUND_COLOR));
            layout.setOnMousePressed(e -> window.close());
            // Finding screen dimensions
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double screenHeight = bounds.getMaxY();
		double screenWidth = bounds.getMaxX();
            Scene scene = new Scene(layout, screenWidth/1.5, screenHeight/1.5);
            window.setScene(scene);
            window.centerOnScreen();

            // Setting up delay, showing window, then starting delay
            PauseTransition delay = new PauseTransition(Duration.seconds(waitTime));
            delay.setOnFinished(e -> window.close());
            window.show();
            // Allows user to close winodw by pressing any key
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                  public void handle(KeyEvent ke) {
                        window.close();
                  }
            });
            delay.play();
      }
}
