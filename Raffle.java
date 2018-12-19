// General to do:
// TODO: Splash screen
// TODO: Menu Bar
// TODO: Background picture
// TODO: Responsive to window resize
// TODO: Change to grid pane?
// TODO: User input of ticketNames file
// TODO: User input of prize info

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
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.util.*;
import java.io.*;

public class Raffle extends Application {

	public ArrayList<Integer> raffleList = new ArrayList<Integer>(255);
	int ticketsRemaining = 255;
	int ticketsDrawn = 0;
	int lastTicketDrawn = 0;
	public final Paint BACKGROUND_COLOR = Color.WHITE;
	public final Paint BORDER_COLOR = Color.BLACK;

	@Override
	public void init() throws Exception {
		super.init();
	}


	@Override
	public void start(Stage primaryStage) throws Exception {


		// Bringing in ticket names
		ArrayList<String> ticketNames = new ArrayList<String>();
		BufferedReader inTickets = new BufferedReader(new FileReader("ticketNames.txt"));
		while (inTickets.ready()) {
			ticketNames.add(inTickets.readLine());
		}
		inTickets.close();
		// TODO: Throw error when name is too long
		// TODO: Deal with apostrophes

		// Bringing in the prize information
		Hashtable<Integer, String> prizeInfo = new Hashtable<Integer, String>(25); // <prizeNumber, prizeDescription>
		BufferedReader inPrizes = new BufferedReader(new FileReader("prizeInfo.txt"));
		while (inPrizes.ready()) {
			String line = inPrizes.readLine();
			Integer prizeNumber = Integer.parseInt(line.substring(0, line.indexOf(" ")));
			String prizeDescription = line.substring(line.indexOf(" "));
			prizeInfo.put(prizeNumber, prizeDescription);
		}
		inPrizes.close();
		// TODO: Throw error when file is incorrect format

		// Finding screen dimensions
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double screenHeight = bounds.getMaxY();
		double screenWidth = bounds.getMaxX();
		// Creating Menu
		Menu fileMenu = new Menu("File");
		Menu viewMenu = new Menu("View");
		Menu helpMenu = new Menu("Help");
		// Creating menu items
		MenuItem about = new MenuItem("About");
		// Adding menu items
		helpMenu.getItems().add(about);
		// Creating Menu Bar
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu, viewMenu, helpMenu);
		// Creating layout for Menu
		BorderPane menuLayout = new BorderPane();
		menuLayout.setTop(menuBar);
		// Functionality for menuItems
		about.setOnAction(e -> aboutWindow.display());
		// Creating row of headers
		Rectangle ticketsRemainingRect = new Rectangle(screenWidth/3, screenHeight/17);
		Text ticketsRemainingText = new Text("Tickets Remaining: " + ticketsRemaining);
		// TextField for typing removal placed in the center of ticketRemainingPane
		TextField textField = new TextField();
		textField.setOpacity(0);
		StackPane ticketsRemainingPane = new StackPane(ticketsRemainingRect, ticketsRemainingText, textField);
		Rectangle ticketsDrawnRect = new Rectangle(screenWidth/3, screenHeight/17);
		Text ticketsDrawnText = new Text("Tickets Drawn: 0");
		StackPane ticketsDrawnPane = new StackPane(ticketsDrawnRect, ticketsDrawnText);
		Rectangle lastTicketDrawnRect = new Rectangle(screenWidth/3, screenHeight/17);
		Text lastTicketDrawnText = new Text("Last Ticket Drawn:  ");
		StackPane lastTicketDrawnPane = new StackPane(lastTicketDrawnRect, lastTicketDrawnText);
		HBox header = new HBox(ticketsRemainingPane, ticketsDrawnPane, lastTicketDrawnPane);
		VBox rows = new VBox(menuBar, header);
		// Styling row of headers
		ticketsRemainingRect.setFill(BACKGROUND_COLOR);
		ticketsRemainingRect.setStroke(BORDER_COLOR);
		ticketsDrawnRect.setFill(BACKGROUND_COLOR);
		ticketsDrawnRect.setStroke(BORDER_COLOR);
		lastTicketDrawnRect.setFill(BACKGROUND_COLOR);
		lastTicketDrawnRect.setStroke(BORDER_COLOR);
		// Initializing arrays of elements
		Rectangle[] tickets = new Rectangle[225];
		Text[] ticketText = new Text[225];
		StackPane[] ticketLayout = new StackPane[225];
		HBox[] ticketCols = new HBox[15];
		// Sets up ticketCols array
		for (int i = 0; i < 15; i++) {
			ticketCols[i] = new HBox();
		}
		// Creates table of tickets
		int counter = 0;
		for (int i = 0; i < 225; i++) {
			// Creating and styling rectangles
			tickets[i] = new Rectangle(screenWidth/15,screenHeight/17);
			tickets[i].setFill(BACKGROUND_COLOR);
			tickets[i].setStroke(BORDER_COLOR);
			// Creating and styling text
			ticketText[i] = new Text((i+1) + "\n" + ticketNames.get(i));
			ticketText[i].setTextAlignment(TextAlignment.CENTER);
			ticketText[i].setWrappingWidth(screenWidth/15);
			// Constructing StackPane
			ticketLayout[i] = new StackPane(tickets[i], ticketText[i]);
			ticketLayout[i].setId(""+(i+1));
			// Adding tickets to columns
			ticketCols[counter].getChildren().add(ticketLayout[i]);
			// Adding action listeners
			final int ticketNumber = i;
			ticketLayout[i].setOnMousePressed(e -> {
				removeTicket(ticketLayout[ticketNumber]);
				refreshHeader(ticketsRemainingText, ticketsDrawnText, lastTicketDrawnText);
				prizeCheck(prizeInfo);
			});
			// Changing rows
			if (ticketCols[counter].getChildren().size() == 15) {
				counter++;
			}
		}
		rows.getChildren().addAll(ticketCols);

		// Implementing textField onKeyPressed
		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ENTER) {
					// Getting the number that was typed into the text field
					Integer number = Integer.parseInt(textField.getText());
					if (number > 0 && number < 256) {
						StackPane chosenTicket = ticketLayout[number-1];
						if (chosenTicket.isVisible()) {
							removeTicket(chosenTicket);
							refreshHeader(ticketsRemainingText, ticketsDrawnText, lastTicketDrawnText);
							prizeCheck(prizeInfo);
						}
					}
					textField.clear();
				}
			}
		});

		// Preparing scene for construction
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

	// Event methods
	/* removeTicket
	* This method receives a StackPane when it is clicked and
	* "deletes" its contents. The ticketNumber is then added to raffleList.
	* It will also update the values for the header
	*
	* @param ticket the StackPane to be removed
	*/
	public void removeTicket(StackPane ticket) {
		// Update header values;
		ticketsRemaining--;
		ticketsDrawn++;
		lastTicketDrawn = Integer.parseInt(ticket.getId());
		// Makes StackPane invisible
		ticket.setVisible(false);
		// Push ticketNumber to raffleStack
		raffleList.add(lastTicketDrawn);
	}

	/* refreshHeader
	* This method will update the header in the GUI when a ticket is
	* removed or replaced
	*
	* @param tr Text for ticketsRemaining
	* @param td Text for ticketsDrawn
	* @param ltd Text for lastTicketDrawn
	*/
	public void refreshHeader(Text tr, Text td, Text ltd) {
		tr.setText("Tickets Remaining: " + ticketsRemaining);
		td.setText("Tickets Drawn: " + ticketsDrawn);
		ltd.setText("Last Ticket Drawn: " + lastTicketDrawn);
	}

	/* prizeCheck
	* This method will search prizeInfo for the next ticket number. If
	* The next ticket number is found, a new window will be created that
	* displays the prizeDescription
	*
	* @param prizeInfo Hashtable to contains the ticket number and prize
	* prizeDescription
	*/
	public void prizeCheck(Hashtable prizeInfo) {
		if (prizeInfo.containsKey(ticketsDrawn + 1)) {
			prizeAlert.display(Integer.toString(ticketsDrawn+1), (String)prizeInfo.get(ticketsDrawn+1));
		}
	}
}
