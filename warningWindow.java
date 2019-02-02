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

public class warningWindow {

      private static boolean answer;
      public static GridPane layout = new GridPane();

      public static boolean display(String text) {
            Stage window = new Stage();
            window.setTitle("Warning");
            Text contents = new Text(text);
            contents.setTextAlignment(TextAlignment.CENTER);

            Button yesButton = new Button("Yes");
            Button noButton = new Button("No");

            layout.add(contents, 0, 0, 2, 1);
            layout.addRow(1, yesButton, noButton);
            layout.setAlignment(Pos.CENTER);
            GridPane.setHalignment(noButton, HPos.CENTER);
            GridPane.setHalignment(yesButton, HPos.CENTER);

            yesButton.setOnAction(e -> {
                  answer = true;
                  window.close();
            });

            noButton.setOnAction(e -> {
                  answer = false;
                  window.close();
            });

            Scene scene = new Scene(layout, 300, 175);
            layout.prefWidthProperty().bind(scene.widthProperty());
            layout.prefHeightProperty().bind(scene.heightProperty());
            contents.setWrappingWidth(scene.getWidth());
            window.setScene(scene);
            // Setting the window's icon
            window.getIcons().add(new Image("Icon.jpg"));
            window.initModality(Modality.APPLICATION_MODAL);
            window.showAndWait();
            return answer;
      }
}
