import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.input.*;
import java.io.*;
import java.util.*;

public class PrizeAlert {
      public Rectangle window = new Rectangle(200,200);
      public static final int WAIT_TIME = 4;
      public static final int FONT_SIZE = 20;
      public static Paint backgroundColor = Color.VIOLET;
      public Text text = new Text("Test");
      public Boolean visible = false;

      private StackPane pane = new StackPane(window, text);

      public PrizeAlert(String prizeDescription) {
            text.setText(prizeDescription);
            text.setTextAlignment(TextAlignment.CENTER);
            text.setFont(new Font(FONT_SIZE));
            window.setFill(backgroundColor);
      }

      public void setSize(double width, double height) {
            window.setWidth(width);
            window.setHeight(height);
      }

      public StackPane getPane() {
            visible = !visible;
            return pane;
      }

      public static void setColor(Paint newColor) {
            backgroundColor = newColor;
      }
}
