import javafx.application.Application;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import java.util.*;
import java.io.*;

public class Raffle extends Application {

	@Override
	public void init() throws Exception {
		super.init();
	}


	@Override
	public void start(Stage primaryStage) throws Exception {


		// Bringing in ticket names
		ArrayList<String> ticketNames = new ArrayList<String>();
		BufferedReader in = new BufferedReader(new FileReader("ticketNames.txt"));
		while (in.ready()) {
			ticketNames.add(in.readLine());
		}
		in.close();
		//
		// TODO: Throw error when name is too long

		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double screenHeight = bounds.getMaxY();
		double screenWidth = bounds.getMaxX();


		VBox rows = new VBox();
		//rows.setPadding(new Insets(10));

		HBox header = new HBox();

		Label ticketsRemaining = new Label("Tickets Remaining: 255");
		Label ticketsDrawn = new Label("Tickets Drawn: 0");
		Label lastTicketDrawn = new Label("Last Ticket Drawn:  ");

		ticketsRemaining.setStyle("-fx-border-color: black;");

		header.getChildren().addAll(ticketsRemaining, ticketsDrawn, lastTicketDrawn);

		rows.getChildren().add(header);

		Rectangle[] tickets = new Rectangle[225];
		Text[] ticketText = new Text[225];
		StackPane[] ticketLayout = new StackPane[225];
		HBox[] ticketCols = new HBox[15];

		for (int i = 0; i < 15; i++) {
			ticketCols[i] = new HBox();
		}
		int counter = 0;

		for (int i = 0; i < 225; i++) {
			tickets[i] = new Rectangle(screenWidth/15,screenHeight/17);
			tickets[i].setFill(Color.WHITE);
			tickets[i].setStroke(Color.VIOLET);
			ticketText[i] = new Text((i+1) + "\n" + ticketNames.get(i));
			ticketText[i].setTextAlignment(TextAlignment.CENTER);
			ticketText[i].setWrappingWidth(screenWidth/15);
			ticketLayout[i] = new StackPane();
			ticketLayout[i].getChildren().addAll(tickets[i], ticketText[i]);
			ticketCols[counter].getChildren().add(ticketLayout[i]);
			if (ticketCols[counter].getChildren().size() == 15) {
				counter++;
			}
		}
		rows.getChildren().addAll(ticketCols);




		primaryStage.setMaximized(true);
		primaryStage.setScene(new Scene(rows));
		primaryStage.setTitle("Test");
		primaryStage.show();
	}


	@Override
	public void stop() throws Exception {
		super.stop();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
