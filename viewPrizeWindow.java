// TODO: Redo bindings using .divide()

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.collections.*;
import javafx.scene.image.*;
import java.io.*;
import java.util.*;

public class viewPrizeWindow {

      public static Stage window = new Stage();
      public static Scene scene;
      public static TableView table = new TableView();

      public static void display() {
            window.setTitle("Prizes");

            ObservableList<Prize> data = FXCollections.observableArrayList(Raffle.prizeInfo);

            TableColumn prizeStatement = new TableColumn("Prize Description");
            prizeStatement.setCellValueFactory(new PropertyValueFactory<>("statement"));
            TableColumn prizeNumber = new TableColumn("Prize Number");
            prizeNumber.setCellValueFactory(new PropertyValueFactory<>("number"));

            table.getColumns().addAll(prizeNumber, prizeStatement);
            table.setEditable(false);

            table.setItems(data);

            StackPane layout = new StackPane(table);
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double screenHeight = bounds.getMaxY();
		double screenWidth = bounds.getMaxX();
            scene = new Scene(layout, screenWidth/2, screenHeight/2);
            prizeStatement.prefWidthProperty().bind(layout.widthProperty());
            prizeNumber.prefWidthProperty().bind(layout.widthProperty());
            prizeStatement.setMaxWidth(3*screenWidth/8);
            prizeNumber.setMaxWidth(screenWidth/8);
            window.setScene(scene);
            window.setResizable(false);
            // Setting the window's icon
            window.getIcons().add(new Image("Icon.jpg"));
            window.showAndWait();
      }
}
