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


public class Header {

      private TextField textField = new TextField();
      private StackPane ticketsRemainingPane = new StackPane();
      private StackPane ticketsDrawnPane = new StackPane();
      private StackPane lastTicketDrawnPane = new StackPane();
      private Text ticketsRemainingText = new Text("Tickets Remaining: " + (Raffle.NUMBER_OF_TICKETS-Raffle.raffleList.size()));
	private Text ticketsDrawnText = new Text("Tickets Drawn: 0");
	private Text lastTicketDrawnText = new Text("Last Ticket Drawn:  ");
      private int headerFontSize = 18; // default of 18
      private Font headerFont = new Font(headerFontSize);
      private HBox header = new HBox(ticketsRemainingPane, ticketsDrawnPane, lastTicketDrawnPane);
      private Rectangle ticketsRemainingRect = new Rectangle();
      private Rectangle ticketsDrawnRect = new Rectangle();;
      private Rectangle lastTicketDrawnRect = new Rectangle();;
      private Paint backgroundColor = Color.RED;
      private Paint borderColor = Color.BLACK;
      private Paint tableBackgroundColor = Color.WHITE;

      public Header() {
            textField.setOpacity(0);
            ticketsRemainingText.setFont(headerFont);
		ticketsDrawnText.setFont(headerFont);
		lastTicketDrawnText.setFont(headerFont);
            // Styling row of headers
            ticketsRemainingRect.setFill(tableBackgroundColor);
		ticketsRemainingRect.setStroke(borderColor);
		ticketsDrawnRect.setFill(tableBackgroundColor);
		ticketsDrawnRect.setStroke(borderColor);
		lastTicketDrawnRect.setFill(tableBackgroundColor);
		lastTicketDrawnRect.setStroke(borderColor);

            ticketsRemainingPane.getChildren().addAll(ticketsRemainingRect, ticketsRemainingText, textField);
            ticketsDrawnPane.getChildren().addAll(ticketsDrawnRect, ticketsDrawnText);
            lastTicketDrawnPane.getChildren().addAll(lastTicketDrawnRect, lastTicketDrawnText);

            ticketsRemainingRect.widthProperty().bind(ticketsRemainingPane.widthProperty());
            ticketsRemainingRect.heightProperty().bind(ticketsRemainingPane.heightProperty());
            ticketsDrawnRect.widthProperty().bind(ticketsDrawnPane.widthProperty());
            ticketsDrawnRect.heightProperty().bind(ticketsDrawnPane.heightProperty());
            lastTicketDrawnRect.widthProperty().bind(lastTicketDrawnPane.widthProperty());
            lastTicketDrawnRect.heightProperty().bind(lastTicketDrawnPane.heightProperty());
            
            ticketsRemainingPane.prefWidthProperty().bind(header.widthProperty().divide(3));
            ticketsRemainingPane.prefHeightProperty().bind(header.heightProperty());
            ticketsDrawnPane.prefWidthProperty().bind(header.widthProperty().divide(3));
            ticketsDrawnPane.prefHeightProperty().bind(header.heightProperty());
            lastTicketDrawnPane.prefWidthProperty().bind(header.widthProperty().divide(3));
            lastTicketDrawnPane.prefHeightProperty().bind(header.heightProperty());

            // Implementing textField onKeyPressed
            /*
		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ENTER) {
					// Getting the number that was typed into the text field
					Integer number = 0;
					try {
						number = Integer.parseInt(textField.getText());
					} catch (Exception e) {
						textField.clear();
					}
					if (number == 800) {
						for (int i = 1; i < 226; i++) {
							StackPane chosenTicket = ticketLayout[i-1];
							if (chosenTicket.isVisible()) {
								removeTicket(chosenTicket);
								refreshHeader();
								prizeCheck();
							}
						}
					}
					if (number > 0 && number < 226) {
						StackPane chosenTicket = ticketLayout[number-1];
						if (chosenTicket.isVisible()) {
							removeTicket(chosenTicket);
							refreshHeader();
							prizeCheck();
						}
					}
					textField.clear();
				}
			}
		});


            // Implementing undo button on lastTicketDrawnPane
		lastTicketDrawnPane.setOnMouseClicked(e -> {
			if (!raffleList.isEmpty()) {
				replaceTicket(ticketLayout[raffleList.get(raffleList.size()-1)-1]);
				refreshHeader();
				prizeCheck();
			}
		});*/

            // Implementing raffle record view on ticketsDrawnPane
		ticketsDrawnPane.setOnMouseClicked(e -> new raffleRecordWindow().display());
      }

      public HBox getTopLevel() {
            return header;
      }

      public void refreshHeader() {
            ticketsRemainingText.setText("Tickets Remaining: " + (Raffle.NUMBER_OF_TICKETS-Raffle.raffleList.size()));
		ticketsDrawnText.setText("Tickets Drawn: " + Raffle.raffleList.size());
		if (Raffle.raffleList.size() == 0) {
			lastTicketDrawnText.setText("Last Ticket Drawn: 0");
		} else {
			lastTicketDrawnText.setText("Last Ticket Drawn: " + Raffle.raffleList.get(Raffle.raffleList.size()-1));
		}
      }

}
