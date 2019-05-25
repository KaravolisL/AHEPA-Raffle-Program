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

public class failureWindow {

      private Stage window = new Stage();
      private Scene scene;
      private GridPane layout = new GridPane();

      public failureWindow(String text) {
            window.setTitle("Import Failed");
            Text contents = new Text(text);
            contents.setTextAlignment(TextAlignment.CENTER);

            layout.addRow(0, contents);
            layout.setAlignment(Pos.CENTER);

            scene = new Scene(layout, 300, 175);
            layout.prefWidthProperty().bind(scene.widthProperty());
            layout.prefHeightProperty().bind(scene.heightProperty());
            window.setScene(scene);
            // Setting the window's icon
            window.getIcons().add(new Image("Icon.jpg"));
            window.initModality(Modality.APPLICATION_MODAL);
      }

      public void display() {
            window.showAndWait();
      }
}
