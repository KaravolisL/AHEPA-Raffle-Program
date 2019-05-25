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

public class raffleRecordWindow {

      private Stage window = new Stage();
      private Scene scene;
      private TableView table = new TableView();
      private StackPane layout = new StackPane(table);


      public raffleRecordWindow() {
            window.setTitle("Raffle Record");

            ArrayList<Ticket> dataInArrayList = new ArrayList();
            for (Integer i : Raffle.raffleList) {
                  Raffle.ticketNames.get(i-1).setNumberDrawn(Raffle.raffleList.indexOf(i)+1);
                  dataInArrayList.add(Raffle.ticketNames.get(i-1));
            }

            ObservableList<Ticket> data = FXCollections.observableArrayList(dataInArrayList);

            TableColumn numberDrawn = new TableColumn("Number Drawn");
            numberDrawn.setCellValueFactory(new PropertyValueFactory<>("numberDrawn"));
            TableColumn ticketNumber = new TableColumn("Ticket Number");
            ticketNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
            TableColumn ticketName = new TableColumn("Ticket Name");
            ticketName.setCellValueFactory(new PropertyValueFactory<>("name"));

            table.getColumns().addAll(numberDrawn, ticketNumber, ticketName);
            table.setEditable(false);

            table.setItems(data);

            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double screenHeight = bounds.getMaxY();
		double screenWidth = bounds.getMaxX();
            scene = new Scene(layout, screenWidth/2, screenHeight/2);
            layout.prefWidthProperty().bind(scene.widthProperty());
            layout.prefHeightProperty().bind(scene.heightProperty());
            numberDrawn.prefWidthProperty().bind(table.widthProperty().divide(3));
            ticketNumber.prefWidthProperty().bind(table.widthProperty().divide(3));
            ticketName.prefWidthProperty().bind(table.widthProperty().divide(3));
            window.setScene(scene);
            // Setting the window's icon
            window.getIcons().add(new Image("Icon.jpg"));
            window.initModality(Modality.APPLICATION_MODAL);;
      }

      public void display() {
            window.showAndWait();
      }
}
