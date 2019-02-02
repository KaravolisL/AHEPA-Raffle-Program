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

      public void display() {
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

            // TODO: Add tips for user


            public RunningPane() {
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
