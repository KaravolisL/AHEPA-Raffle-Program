// TODO: Fix sizing
// TODO: Remove window peripherals
// TODO: Timed exit 




import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class prizeAlert {
      public static void display(String title, String prizeDescription) {
            Stage window = new Stage();

            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(title);

            Text description = new Text(prizeDescription);

            VBox layout = new VBox(description);
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
      }
}
