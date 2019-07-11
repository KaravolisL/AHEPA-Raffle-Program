import javafx.application.Application;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.input.*;
import javafx.scene.image.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.beans.*;
import java.util.*;
import java.io.*;
import java.lang.*;


public class Grid {

      private static Grid instance = null;

      private Rectangle[] tickets = new Rectangle[Raffle.NUMBER_OF_TICKETS];
      private Text[] ticketText = new Text[Raffle.NUMBER_OF_TICKETS];
      private HBox[] ticketCols = new HBox[15];
      private StackPane[] ticketLayout = new StackPane[Raffle.NUMBER_OF_TICKETS];
      private VBox mainTable = new VBox();

      public static Grid getInstance() {
            if (instance == null)
                  instance =  new Grid();
            return instance;
      }

      private Grid() {
            int counter = 0;
            for (int i = 0; i < Raffle.NUMBER_OF_TICKETS; i++) {
                  tickets[i] = new Rectangle();
                  tickets[i].setFill(Color.WHITE);
                  tickets[i].setStroke(Color.BLACK);

                  ticketText[i] = new Text((i+1) + "\n" + Raffle.ticketNames.get(i).getName());
			ticketText[i].setTextAlignment(TextAlignment.CENTER);
			ticketText[i].setWrappingWidth(ticketCols.getWidth()/15);

                  ticketLayout[i] = new StackPane(tickets[i], ticketText[i]);
			ticketLayout[i].setId(""+(i+1));

                  ticketCols[counter].getChildren().add(ticketLayout[i]);

                  final int ticketNumber = i;
			ticketLayout[i].setOnMousePressed(e -> {
                        ticketLayout[i].setVisible(false);
				Raffle.removeTicket(ticketLayout[ticketNumber]);
			});

                  if (ticketCols[counter].getChildren().size() == 15) {
				counter++;
			}
            }

            mainTable.getChildren().addAll(ticketCols);
            // Adding the background image
		mainTable.setStyle("-fx-background-image: url('Logo.jpg') no-repeat center center fixed;" +
					 "-fx-background-size: 100% 100%;");
      }

      public void getTopLevel() {
            return mainTable;
      }



}
