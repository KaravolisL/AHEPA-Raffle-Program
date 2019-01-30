// General to do:
// TODO: Exception catching for files
// TODO: Change WAIT_TIME
// TODO: Help section
// TODO: Pretty up windows
// TODO: Splash screen
// TODO: Fix sizing once and for all
// TODO: Eliminate statics
// TODO: Package up files
// TODO: Replace refreshHeader with listener

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
	public final int WAIT_TIME = 4; // How long the prize alert stays
	public static final int NUMBER_OF_TICKETS = 225; // Number of tickets
	public static ArrayList<Integer> raffleList = new ArrayList<Integer>(226);
	public static ArrayList<Ticket> ticketNames = new ArrayList<Ticket>(226);
	public static ArrayList<Prize> prizeInfo = new ArrayList<Prize>(30);
	public Text ticketsRemainingText = new Text("Tickets Remaining: " + (NUMBER_OF_TICKETS-raffleList.size()));
	public Text ticketsDrawnText = new Text("Tickets Drawn: 0");
	public Text lastTicketDrawnText = new Text("Last Ticket Drawn:  ");
	public static Paint tableBackgroundColor = Color.WHITE;
	public static Paint alertBackgroundColor = Color.WHITE;
	public static Paint borderColor = Color.BLACK;
	public static Rectangle ticketsRemainingRect, ticketsDrawnRect, lastTicketDrawnRect;
	public static Rectangle[] tickets = new Rectangle[NUMBER_OF_TICKETS];
	public VBox rows;
	// Initializing arrays of elements
	public static Text[] ticketText = new Text[NUMBER_OF_TICKETS];
	public HBox[] ticketCols = new HBox[15];
	public StackPane[] ticketLayout = new StackPane[NUMBER_OF_TICKETS];
	public StackPane mainTableStack = new StackPane();

	// TODO: Comment and organize all the above initializations

	@Override
	public void init() throws Exception {
		try {
			// Bringing in ticket names
			readTickets("ticketNames.txt");
			// Bringing in the prize information
			readPrizes("prizeInfo.txt");
		} catch (Exception e) {
			failureWindow.display("Save file was corrupted.\nPlease check ticket names and prize information.");
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Finding initial screen dimensions
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double screenHeight = bounds.getMaxY();
		double screenWidth = bounds.getMaxX();
		// Creating Menu
		Menu fileMenu = new Menu("File");
		Menu editMenu = new Menu("Edit");
		Menu viewMenu = new Menu("View");
		Menu helpMenu = new Menu("Help");
		// Creating fileMenu items
		MenuItem restart = new MenuItem("Restart");
		MenuItem importTickets = new MenuItem("Import Ticket Names");
		MenuItem importPrizes = new MenuItem("Import Prizes");
		// Creating editMenu items
		MenuItem editTicket = new MenuItem("Edit Ticket");
		MenuItem editPrize = new MenuItem("Edit Prize");
		MenuItem changeBackgroundColor = new MenuItem("Change Background Color");
		// Creating viewMenu items
		MenuItem viewFullScreen = new MenuItem("Full Screen");
		MenuItem viewMaximized = new MenuItem("Maximize");
		MenuItem viewTicketNames = new MenuItem("Ticket Names");
		MenuItem viewPrizes = new MenuItem("Prizes");
		// Creating helpMenu items
		MenuItem about = new MenuItem("About");
		// Adding fileMenu items
		fileMenu.getItems().addAll(importTickets, importPrizes, restart);
		// Adding editMenu items
		editMenu.getItems().addAll(editTicket, editPrize, changeBackgroundColor);
		// Adding viewMenu items
		viewMenu.getItems().addAll(viewFullScreen, viewMaximized, new SeparatorMenuItem(), viewTicketNames, viewPrizes);
		// Adding helpMenu items
		helpMenu.getItems().add(about);
		// Creating Menu Bar
		MenuBar menuBar = new MenuBar();
		menuBar.setMaxHeight(25.3333);
		menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu, helpMenu);
		// Functionality for menuItems
		importTickets.setOnAction(e -> {
			FileChooser ticketFileChooser = new FileChooser();
			ticketFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
			File importTicketFile = ticketFileChooser.showOpenDialog(new Stage());
			importTicketNames(importTicketFile);
		});
		importPrizes.setOnAction(e -> {
			FileChooser prizeFileChooser = new FileChooser();
			prizeFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
			File importPrizeFile = prizeFileChooser.showOpenDialog(new Stage());
			importPrizeList(importPrizeFile);
		});
		restart.setOnAction(e -> {
			boolean answer = warningWindow.display("Restarting the raffle will cause all progress to be lost. Are you sure?");
			if (answer) restartRaffle();
		});
		about.setOnAction(e -> aboutWindow.display());
		editTicket.setOnAction(e -> editTicketWindow.display());
		editPrize.setOnAction(e -> editPrizeWindow.display());
		changeBackgroundColor.setOnAction(e -> {
			colorPickerWindow.display();
		});
		viewFullScreen.setOnAction(e -> {
			primaryStage.setFullScreen(true);
			rows.getChildren().remove(menuBar);
			resize(true);
		});
		viewMaximized.setOnAction(e -> {
			primaryStage.setMaximized(true);
			resize(false);
		});
		viewTicketNames.setOnAction(e -> viewTicketNamesWindow.display());
		viewPrizes.setOnAction(e -> viewPrizeWindow.display());
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
		ticketsRemainingRect.setFill(tableBackgroundColor);
		ticketsRemainingRect.setStroke(borderColor);
		ticketsDrawnRect.setFill(tableBackgroundColor);
		ticketsDrawnRect.setStroke(borderColor);
		lastTicketDrawnRect.setFill(tableBackgroundColor);
		lastTicketDrawnRect.setStroke(borderColor);
		// Sets up ticketCols array
		for (int i = 0; i < 15; i++) {
			ticketCols[i] = new HBox();
			ticketCols[i].prefHeightProperty().bind(rows.heightProperty());
		}
		// Creates table of tickets
		int counter = 0;
		for (int i = 0; i < NUMBER_OF_TICKETS; i++) {
			// Creating and styling rectangles
			tickets[i] = new Rectangle();
			tickets[i].setFill(tableBackgroundColor);
			tickets[i].setStroke(borderColor);
			// Creating and styling text
			ticketText[i] = new Text((i+1) + "\n" + ticketNames.get(i).getName());
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
				prizeCheck();
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
		// Updating table from save file
		try {
			BufferedReader inRaffleList = new BufferedReader(new FileReader("raffleList.txt"));
			while (inRaffleList.ready()) {
				removeTicket(ticketLayout[Integer.parseInt(inRaffleList.readLine())]);
			}
			refreshHeader();
			inRaffleList.close();
		} catch (Exception e) {
			failureWindow.display("Save file was corrupted.\nRaffle was restarted");
		}
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
		primaryStage.setTitle("AHEPA Raffle 2019");
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

		// Updating ticket names
		PrintWriter outTicketNames = new PrintWriter(new File("ticketNames.txt"));
		for (Ticket t : ticketNames) {
			outTicketNames.println(t);
		}
		outTicketNames.close();

		// Writing prizeInfo to the save file
		PrintWriter outPrizeInfo = new PrintWriter(new File("prizeInfo.txt"));
		for (Prize p : prizeInfo) {
			outPrizeInfo.println(p);
		}
		outPrizeInfo.close();
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
		ticketsRemainingText.setText("Tickets Remaining: " + (NUMBER_OF_TICKETS-raffleList.size()));
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
	*/
	public void prizeCheck() {
		for (Prize p : Raffle.prizeInfo) {
			if (p.getNumber() == raffleList.size() + 1) {
				double screenHeight = scene.getHeight();
				double screenWidth = scene.getWidth();
				PrizeAlert alert = new PrizeAlert(p.getStatement());
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
				return;
			}
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
			for (int i = 0; i < NUMBER_OF_TICKETS; i++) {
				tickets[i].setWidth(screenWidth/15.2);
				tickets[i].setHeight(screenHeight/16.4);
			}
		} else {
			ticketsRemainingRect.setHeight(screenHeight/15);
			ticketsDrawnRect.setHeight(screenHeight/15);
			lastTicketDrawnRect.setHeight(screenHeight/15);
			for (int i = 0; i < NUMBER_OF_TICKETS; i++) {
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
	public static void restyle() {
		ticketsRemainingRect.setFill(tableBackgroundColor);
		ticketsRemainingRect.setStroke(borderColor);
		ticketsDrawnRect.setFill(tableBackgroundColor);
		ticketsDrawnRect.setStroke(borderColor);
		lastTicketDrawnRect.setFill(tableBackgroundColor);
		lastTicketDrawnRect.setStroke(borderColor);
		for (Rectangle r : tickets) {
			r.setFill(tableBackgroundColor);
			r.setStroke(borderColor);
		}
		PrizeAlert.setColor(alertBackgroundColor);
	}

	/* importTicketNames
	* Takes a file and passes it to readTickets(). Updates the mainTable and
	* restarts the raffle
	*
	* @param file file chosen by user from which the ticket names will
	* be extracted
	*/
	public void importTicketNames(File file) {
		try {
			// Reading tickets from new file and updating arrayList
			readTickets(file.toString());
			// Updating ticket names in the mainTable
			for (int i = 0; i < NUMBER_OF_TICKETS; i++) {
				ticketText[i] = new Text((i+1) + "\n" + ticketNames.get(i).getName());
			}
			// Restarting raffle
			restartRaffle();
		} catch (Exception e) {
			failureWindow.display("Failed to import ticket names.");
		}
	}

	/* importPrizeList
	* Takes a file and passes it to readPrizes(). Handles possible errors
	*
	* @param file file chosen by user from which prizes will be extracted
	*/
	public void importPrizeList(File file) {
		try {
			// Reading prizes from new file and updating prizeInfo
			readPrizes(file.toString());
		} catch (Exception e) {
			failureWindow.display("Failed to import prize list");
		}
	}

	/* readTickets
	* First, clears the ticketNames arrayList, then reads in tickets from the given
	* file, creates respective Ticket objects and adds them to the arrayList
	*
	* @param file?Name file from which to read the tickets
	*/
	public void readTickets(String fileName) throws FileNotFoundException, IOException {
		ticketNames.clear();
		BufferedReader inTickets = new BufferedReader(new FileReader(fileName));
		for (int i = 1; inTickets.ready(); i++) {
			ticketNames.add(new Ticket(inTickets.readLine(), i));
		}
		inTickets.close();
		// TODO: Throw error when name is too long
		// TODO: Deal with apostrophes
	}

	/* readPrizes
	* First, clears the prizeInfo arraylist, then reads in prizes from the given
	* file, creates respective Prize objects and adds them to the arrayList
	*
	* @param fileName file from which to read the prizes
	*/
	public void readPrizes(String fileName) throws FileNotFoundException, IOException {
		prizeInfo.clear();
		BufferedReader inPrizes = new BufferedReader(new FileReader(fileName));
		while (inPrizes.ready()) {
			String line = inPrizes.readLine();
			Integer prizeNumber = Integer.parseInt(line.substring(0, line.indexOf(" ")));
			String prizeDescription = line.substring(line.indexOf(" ")+1);
			prizeInfo.add(new Prize(prizeNumber, prizeDescription));
		}
		inPrizes.close();
		// TODO: Throw error when file is incorrect format
	}
}
