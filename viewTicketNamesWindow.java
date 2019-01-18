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


public class viewTicketNamesWindow {

      public static Stage window = new Stage();
      public static Scene scene;
      public static TableView table = new TableView();

      public static void display() {
            window.setTitle("View Ticket Names");

            ObservableList<Ticket> data = FXCollections.observableArrayList(Raffle.ticketNames);

            TableColumn ticketName = new TableColumn("Ticket Name");
            ticketName.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn ticketNumber = new TableColumn("Ticket Number");
            ticketNumber.setCellValueFactory(new PropertyValueFactory<>("number"));

            table.getColumns().addAll(ticketName, ticketNumber);
            table.setEditable(false);

            table.setItems(data);

            StackPane layout = new StackPane(table);
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double screenHeight = bounds.getMaxY();
		double screenWidth = bounds.getMaxX();
            scene = new Scene(layout, screenWidth/2, screenHeight/2);
            ticketName.prefWidthProperty().bind(layout.widthProperty());
            ticketNumber.prefWidthProperty().bind(layout.widthProperty());
            ticketName.setMaxWidth(screenWidth/4);
            ticketNumber.setMaxWidth(screenWidth/4);
            window.setScene(scene);
            window.setResizable(false);
            // Setting the window's icon
            window.getIcons().add(new Image("Icon.jpg"));
            window.showAndWait();
      }
}
