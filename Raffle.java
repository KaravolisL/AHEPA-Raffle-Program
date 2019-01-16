// General to do:
// TODO: Splash screen
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
import javafx.scene.image.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.beans.value.*;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import java.util.*;
import java.io.*;
import java.lang.*;

public class Raffle extends Application {

	public Scene scene;
	public static ArrayList<Integer> raffleList = new ArrayList<Integer>(255);
	public static ArrayList<String> ticketNames = new ArrayList<String>();
	public static Hashtable<Integer, String> prizeInfo = new Hashtable<Integer, String>(25); // <prizeNumber, prizeDescription>
	public Text ticketsRemainingText = new Text("Tickets Remaining: " + (225-raffleList.size()));
	public Text ticketsDrawnText = new Text("Tickets Drawn: 0");
	public Text lastTicketDrawnText = new Text("Last Ticket Drawn:  ");
	public Paint backgroundColor = Color.WHITE;
	public Paint borderColor = Color.BLACK;
	public Rectangle ticketsRemainingRect, ticketsDrawnRect, lastTicketDrawnRect;
	public Rectangle[] tickets = new Rectangle[225];
	VBox rows;
	public StackPane[] ticketLayout = new StackPane[225];
	public StackPane mainTableStack = new StackPane();
	public final int WAIT_TIME = 4; // How long the prize alert stays

	// TODO: Comment and organize all the above initializations

	@Override
	public void init() throws Exception {
		// Bringing in ticket names
		BufferedReader inTickets = new BufferedReader(new FileReader("ticketNames.txt"));
		while (inTickets.ready()) {
			ticketNames.add(inTickets.readLine());
		}
		inTickets.close();
		// TODO: Throw error when name is too long
		// TODO: Deal with apostrophes

		// Bringing in the prize information
		BufferedReader inPrizes = new BufferedReader(new FileReader("prizeInfo.txt"));
		while (inPrizes.ready()) {
			String line = inPrizes.readLine();
			Integer prizeNumber = Integer.parseInt(line.substring(0, line.indexOf(" ")));
			String prizeDescription = line.substring(line.indexOf(" "));
			prizeInfo.put(prizeNumber, prizeDescription);
		}
		inPrizes.close();
		// TODO: Throw error when file is incorrect format
	}


	@Override
	public void start(Stage primaryStage) throws Exception {

		// Finding initial screen dimensions
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double screenHeight = bounds.getMaxY();
		double screenWidth = bounds.getMaxX();
		// Creating Menu
		Menu fileMenu = new Menu("File");
		Menu viewMenu = new Menu("View");
		Menu helpMenu = new Menu("Help");
		// Creating fileMenu items
		MenuItem restart = new MenuItem("Restart");
		// Creating viewMenu items
		MenuItem viewFullScreen = new MenuItem("Full Screen");
		MenuItem viewMaximized = new MenuItem("Maximize");
		MenuItem changeBackgroundColor = new MenuItem("Change Background Color");
		MenuItem viewTicketNames = new MenuItem("Ticket Names");
		// Creating helpMenu items
		MenuItem about = new MenuItem("About");
		// Adding fileMenu items
		fileMenu.getItems().add(restart);
		// Adding viewMenu items
		viewMenu.getItems().addAll(viewFullScreen, viewMaximized, changeBackgroundColor, viewTicketNames);
		// Adding helpMenu items
		helpMenu.getItems().add(about);
		// Creating Menu Bar
		MenuBar menuBar = new MenuBar();
		menuBar.setMaxHeight(25.3333);
		menuBar.getMenus().addAll(fileMenu, viewMenu, helpMenu);
		// Functionality for menuItems
		about.setOnAction(e -> aboutWindow.display());
		viewFullScreen.setOnAction(e -> {
			primaryStage.setFullScreen(true);
			rows.getChildren().remove(menuBar);
			resize(true);
		});
		viewMaximized.setOnAction(e -> {
			primaryStage.setMaximized(true);
			resize(false);
		});
		changeBackgroundColor.setOnAction(e -> {
			backgroundColor = colorPickerWindow.display();
			restyle();
		});
		viewTicketNames.setOnAction(e -> viewTicketNamesWindow.display());
		restart.setOnAction(e -> {
			boolean answer = warningWindow.display("Restarting the raffle will cause all progress to be lost. Are you sure?");
			if (answer) restartRaffle();
		});
		// Creating row of headers
		ticketsRemainingRect = new Rectangle(screenWidth/3, screenHeight/18);
		// TextField for typing removal placed in the center of ticketRemainingPane
		TextField textField = new TextField();
		textField.setOpacity(0);
		StackPane ticketsRemainingPane = new StackPane(ticketsRemainingRect, ticketsRemainingText, textField);
		ticketsDrawnRect = new Rectangle(screenWidth/3, screenHeight/18);
		StackPane ticketsDrawnPane = new StackPane(ticketsDrawnRect, ticketsDrawnText);
		lastTicketDrawnRect = new Rectangle(screenWidth/3, screenHeight/18);
		StackPane lastTicketDrawnPane = new StackPane(lastTicketDrawnRect, lastTicketDrawnText);
		HBox header = new HBox(ticketsRemainingPane, ticketsDrawnPane, lastTicketDrawnPane);
		rows = new VBox(menuBar, header);
		header.prefHeightProperty().bind(rows.heightProperty());
		// Styling row of headers
		ticketsRemainingRect.setFill(backgroundColor);
		ticketsRemainingRect.setStroke(borderColor);
		ticketsDrawnRect.setFill(backgroundColor);
		ticketsDrawnRect.setStroke(borderColor);
		lastTicketDrawnRect.setFill(backgroundColor);
		lastTicketDrawnRect.setStroke(borderColor);
		// Initializing arrays of elements
		Text[] ticketText = new Text[225];
		HBox[] ticketCols = new HBox[15];
		// Sets up ticketCols array
		for (int i = 0; i < 15; i++) {
			ticketCols[i] = new HBox();
			ticketCols[i].prefHeightProperty().bind(rows.heightProperty());
		}
		// Creates table of tickets
		int counter = 0;
		for (int i = 0; i < 225; i++) {
			// Creating and styling rectangles
			tickets[i] = new Rectangle();
			tickets[i].setFill(backgroundColor);
			tickets[i].setStroke(borderColor);
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
				refreshHeader();
				prizeCheck(prizeInfo);
			});
			// Changing rows
			if (ticketCols[counter].getChildren().size() == 15) {
				counter++;
			}
		}
		// Adding rows into separate VBox
		VBox mainTable = new VBox();
		mainTable.getChildren().addAll(ticketCols);
		// Adding the background image
		mainTable.setStyle("-fx-background-image: url('Logo.jpg') no-repeat center center fixed;" +
					 "-fx-background-size: 100% 100%;");
		// Adding mainTable to mainTableStack so prize alerts can be added
		mainTableStack.getChildren().add(mainTable);
		rows.getChildren().add(mainTableStack);
		rows.setStyle("-fx-border-color: RED;");
		// Updating table from save file
		BufferedReader inRaffleList = new BufferedReader(new FileReader("raffleList.txt"));
		while (inRaffleList.ready()) {
			removeTicket(ticketLayout[Integer.parseInt(inRaffleList.readLine())]);
		}
		refreshHeader();
		inRaffleList.close();

		// Implementing textField onKeyPressed
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
								prizeCheck(prizeInfo);
							}
						}
					}
					if (number > 0 && number < 226) {
						StackPane chosenTicket = ticketLayout[number-1];
						if (chosenTicket.isVisible()) {
							removeTicket(chosenTicket);
							refreshHeader();
							prizeCheck(prizeInfo);
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
				prizeCheck(prizeInfo);
			}
		});

		// Undoes changes made when going into full screen
		rows.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ESCAPE && !primaryStage.isFullScreen()) {
					try {
						rows.getChildren().add(0, menuBar);
					} catch (Exception e) {} // Catches error of adding a node back in
					resize(false);
				}
			}
		});

		// Preparing scene for construction
		primaryStage.setMaximized(true);
		primaryStage.resizableProperty().setValue(Boolean.FALSE);
		scene = new Scene(rows);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Test");
		// Setting application icon
		primaryStage.getIcons().add(new Image("Icon.jpg"));
		primaryStage.show();
		resize(false);
	}


	@Override
	public void stop() throws Exception {
		// Writing raffleList to the save file
		PrintWriter outRaffleList = new PrintWriter(new File("raffleList.txt"));
		for (Integer i : raffleList) {
			outRaffleList.println(i-1);
		}
		outRaffleList.close();
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
		// Makes StackPane invisible
		ticket.setVisible(false);
		// add ticketNumber to raffleList
		raffleList.add(Integer.parseInt(ticket.getId()));
	}

	/* replaceTicket
	* This method recieves a StackPane and makes it visible. The ticketNumber
	* is removed from raffleList. It will also update the values for the header
	*
	* @param ticket the StackPane to be replaced
	*/
	public void replaceTicket(StackPane ticket) {
		// Remove last ticket from raffleList
		raffleList.remove(raffleList.size()-1);
		// Makes StackPane visible
		ticket.setVisible(true);
	}

	/* refreshHeader
	* This method will update the header in the GUI when a ticket is
	* removed or replaced
	*/
	public void refreshHeader() {
		ticketsRemainingText.setText("Tickets Remaining: " + (225-raffleList.size()));
		ticketsDrawnText.setText("Tickets Drawn: " + raffleList.size());
		if (raffleList.size() == 0) {
			lastTicketDrawnText.setText("Last Ticket Drawn: 0");
		} else {
			lastTicketDrawnText.setText("Last Ticket Drawn: " + raffleList.get(raffleList.size()-1));
		}
	}

	/* prizeCheck
	* This method will search prizeInfo for the next ticket number. If
	* The next ticket number is found, a new object will be created which
	* creates a styled rectangle and text that is displayed on top of
	* the mainTable. It will disappear after WAIT_TIME or a button is pressed
	*
	* @param prizeInfo Hashtable that contains the ticket number and prize
	* prizeDescription
	*/
	public void prizeCheck(Hashtable prizeInfo) {
		if (prizeInfo.containsKey(raffleList.size() + 1)) {
			double screenHeight = scene.getHeight();
			double screenWidth = scene.getWidth();
			PrizeAlert alert = new PrizeAlert((String)prizeInfo.get(raffleList.size()+1));
			alert.setSize(screenWidth/1.5, screenHeight/1.5);
			PauseTransition delay = new PauseTransition(Duration.seconds(WAIT_TIME));
	            delay.setOnFinished(e -> {
				if (alert.visible) {
					mainTableStack.getChildren().remove(mainTableStack.getChildren().size()-1);
				}
			});
			mainTableStack.getChildren().add(alert.getPane());
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	                  public void handle(KeyEvent ke) {
	                        if (mainTableStack.getChildren().size() > 1) {
						mainTableStack.getChildren().remove(alert.getPane());
					}
	                  }
	            });
			delay.play();
		}
	}

	/* resize
	* Finds the width and height of the screen. Sizes the elements
	* accordingly based on whether or not the app is full screen
	*
	* @param forFullScreen a boolean describing the state of the app
	*/
	public void resize(boolean forFullScreen) {
		double screenHeight = scene.getHeight();
		double screenWidth = scene.getWidth();
		ticketsRemainingRect.setWidth(screenWidth/3);
		ticketsDrawnRect.setWidth(screenWidth/3);
		lastTicketDrawnRect.setWidth(screenWidth/3);
		if (forFullScreen) {
			ticketsRemainingRect.setHeight(screenHeight/15);
			ticketsDrawnRect.setHeight(screenHeight/15);
			lastTicketDrawnRect.setHeight(screenHeight/15);
			for (int i = 0; i < 225; i++) {
				tickets[i].setWidth(screenWidth/15.2);
				tickets[i].setHeight(screenHeight/16.4);
			}
		} else {
			ticketsRemainingRect.setHeight(screenHeight/15);
			ticketsDrawnRect.setHeight(screenHeight/15);
			lastTicketDrawnRect.setHeight(screenHeight/15);
			for (int i = 0; i < 225; i++) {
				tickets[i].setWidth(screenWidth/15.2);
				tickets[i].setHeight(screenHeight/17);
			}
		}
	}

	/* restartRaffle
	* Occurs only after user selects yes in a warning window.
	* Makes all tickets visible and clears the raffleList
	*/
	public void restartRaffle() {
		// Making each ticket visible
		for (Integer i : raffleList) {
			ticketLayout[i-1].setVisible(true);
		}
		// Clearing raffleList
		raffleList.clear();
		refreshHeader();
	}

	/* restyle
	* Updates the background color and border color of all components
	*/
	public void restyle() {
		ticketsRemainingRect.setFill(backgroundColor);
		ticketsRemainingRect.setStroke(borderColor);
		ticketsDrawnRect.setFill(backgroundColor);
		ticketsDrawnRect.setStroke(borderColor);
		lastTicketDrawnRect.setFill(backgroundColor);
		lastTicketDrawnRect.setStroke(borderColor);
		for (Rectangle r : tickets) {
			r.setFill(backgroundColor);
			r.setStroke(borderColor);
		}
		PrizeAlert.setColor(backgroundColor);
	}
}
