import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.image.*;
import javafx.beans.*;
import java.io.*;
import java.util.*;

public class aboutWindow {

      private Stage window = new Stage();
      private GridPane layout = new GridPane();
      private StackPane pageLayout = new StackPane();
      private Scene scene;
      private ToolBar toolBar = new ToolBar();
      private ToggleButton aboutButton = new ToggleButton("About");
      private ToggleButton importingButton = new ToggleButton("Importing");
      private ToggleButton runningButton = new ToggleButton("Running the Raffle");

      public aboutWindow() {
            window.setTitle("About");
            // Setting up initial window contents
            toolBar.getItems().addAll(aboutButton, importingButton, runningButton);
            for (int i = 0; i < 4; i += 2) {
                  toolBar.getItems().add(i+1, new Separator());
            }
            toolBar.prefWidthProperty().bind(layout.widthProperty());
            layout.add(toolBar, 0, 0);
            layout.add(pageLayout, 0, 1);
            pageLayout.prefWidthProperty().bind(layout.widthProperty());
            pageLayout.prefHeightProperty().bind(layout.heightProperty());
            pageLayout.getChildren().add(new AboutPane().getPane());
            aboutButton.setSelected(true);
            // Adding functionality
            aboutButton.setOnAction(e -> {
                  untoggle(aboutButton);
                  pageLayout.getChildren().clear();
                  if (aboutButton.isSelected()) {
                        pageLayout.getChildren().add(new AboutPane().getPane());
                  }
            });
            importingButton.setOnAction(e -> {
                  untoggle(importingButton);
                  pageLayout.getChildren().clear();
                  if (importingButton.isSelected()) {
                        pageLayout.getChildren().add(new ImportingPane().getPane());
                  }
            });
            runningButton.setOnAction(e -> {
                  untoggle(runningButton);
                  pageLayout.getChildren().clear();
                  if (runningButton.isSelected()) {
                        pageLayout.getChildren().add(new RunningPane().getPane());
                  }
            });

            scene = new Scene(layout, 800, 600);
            layout.prefWidthProperty().bind(scene.widthProperty());
            layout.prefHeightProperty().bind(scene.heightProperty());
            window.setScene(scene);
            window.setMinWidth(300);
            window.setMinHeight(175);
            // Setting the window's icon
            window.getIcons().add(new Image("Icon.jpg"));
      }

      public void display() {
            window.show();
      }

      /*
      */
      private void untoggle(ToggleButton selectedButton) {
            for (int i = 0; i < toolBar.getItems().size(); i += 2) {
                  ToggleButton currentButton = (ToggleButton)toolBar.getItems().get(i);
                  if (currentButton != selectedButton) {
                        currentButton.setSelected(false);
                  }
            }
      }

      private class AboutPane {
            private GridPane aboutLayout = new GridPane();
            private Text contents = new Text("This program was made by Luke Karavolis\n"
            + "For more information, visit https://github.com/KaravolisL/AHEPA-Raffle-Program or email karavolisl@gmail.com\n");
            private ImageView logo = new ImageView(new Image("Logo.jpg"));

            public AboutPane() {
                  // Setting up pane
                  aboutLayout.add(contents, 0, 0);
                  aboutLayout.add(logo, 0, 1);
                  contents.setTextAlignment(TextAlignment.CENTER);
                  GridPane.setHalignment(logo, HPos.CENTER);
                  aboutLayout.setAlignment(Pos.CENTER);
                  // Displaying pane to main window
                  aboutLayout.prefWidthProperty().bind(pageLayout.widthProperty());
                  aboutLayout.prefHeightProperty().bind(pageLayout.heightProperty());
            }

            public Pane getPane() {
                  return aboutLayout;
            }
      }

      private class ImportingPane {
            private GridPane importingLayout = new GridPane();
            private Text header = new Text("You can import ticket names and a list of prizes from text files.\n\n\n\n\n");
            private Text importingTicketsHeader = new Text("For importing ticket names, the names should be one per line. The ticket number is represented by the line number. An example is shown below.\n\n\n\n");
            private Text importingPrizesHeader = new Text("For importing a list of prizes, the description to be displayed should follow the ticket number associated with the prize separated by a space. An example is shown below.\n\n\n\n");
            private Text importingTicketsExample = new Text("Ron Swanson\nLeslie Knope\nBen Wyatt");
            private Text importingPrizesExample = new Text("25 Prize Description Here\n50 Prize Description Here\n75 Prize Description Here");

            public ImportingPane() {
                  // Setting up pane
                  importingLayout.add(header, 0, 0, 2, 1);
                  importingLayout.addRow(1, importingTicketsHeader, importingPrizesHeader);
                  importingLayout.addRow(2, importingTicketsExample, importingPrizesExample);
                  header.setTextAlignment(TextAlignment.CENTER);
                  importingTicketsHeader.setTextAlignment(TextAlignment.CENTER);
                  importingPrizesHeader.setTextAlignment(TextAlignment.CENTER);
                  importingTicketsHeader.setWrappingWidth(375);
                  importingPrizesHeader.setWrappingWidth(375);
                  for (Node n : importingLayout.getChildren()) {
                        GridPane.setHalignment(n, HPos.CENTER);
                  }
                  importingLayout.setAlignment(Pos.CENTER);
                  // Displaying pane to main window
                  importingLayout.prefWidthProperty().bind(pageLayout.widthProperty());
                  importingLayout.prefHeightProperty().bind(pageLayout.heightProperty());
            }

            public Pane getPane() {
                  return importingLayout;
            }
      }

      private class RunningPane {
            private GridPane runningLayout = new GridPane();
            private Text sectionTitle1 = new Text("Removing a Ticket");
            private Text removingTicket = new Text("A ticket can be removed from the table simply by clicking on it. A text field is also hidden to the left " +
            "of where the number of tickets remaining is displayed. The ticket number can be entered here and pressing enter will remove the corresponding ticket. " +
            "If an invalid number is entered, the field will be cleared and nothing will happen. In the case that all tickets must be removed at once, 800 can be " +
            "entered.");
            private Text sectionTitle2 = new Text("Replacing a Ticket");
            private Text replacingTicket = new Text("In the case that a ticket is removed mistakenly, the user can click the box in which the last ticket drawn is " +
            "displayed. This will replace the last ticket that was removed.");
            private Text sectionTitle3 = new Text("Viewing Raffle Record");
            private Text viewingRaffleRecord = new Text("A record of the raffle can be seen by clicking the box where the number of tickets drawn is displayed.");
            private Text sectionTitle4 = new Text("Prize Alert");
            private Text prizeAlerts = new Text("A prize alert will appear one ticket before its corresponding ticket is drawn. For instance, if a prize is registered " +
            "for the 25th ticket, the alert will appear after the 24th ticket is drawn. The alert will remain for 4 seconds by default. This time can be changed in the " +
            "edit menu. Alternatively, the enter key can be pressed to remove it prematurely.");
            private Text[] texts = {sectionTitle1, removingTicket,
                                    sectionTitle2, replacingTicket,
                                    sectionTitle3, viewingRaffleRecord,
                                    sectionTitle4, prizeAlerts};

            public RunningPane() {
                  // Setting up pane
                  for (int i = 0; i < texts.length; i++) {
                        texts[i].setTextAlignment(TextAlignment.CENTER);
                        texts[i].setWrappingWidth(375);
                        if (i % 2 == 0) {
                              runningLayout.add(texts[i], 0, i);
                        } else {
                              runningLayout.add(texts[i], 1, i-1);
                        }
                  }
                  ColumnConstraints titleColumn = new ColumnConstraints();
                  ColumnConstraints textColumn = new ColumnConstraints();
                  titleColumn.setPercentWidth(10);
                  textColumn.setPercentWidth(90);
                  runningLayout.getColumnConstraints().addAll(titleColumn, textColumn);
                  runningLayout.setVgap(20);
                  runningLayout.setAlignment(Pos.CENTER);
                  // Displaying pane to main window
                  runningLayout.prefWidthProperty().bind(pageLayout.widthProperty());
                  runningLayout.prefHeightProperty().bind(pageLayout.heightProperty());
            }

            public Pane getPane() {
                  return runningLayout;
            }
      }
}
