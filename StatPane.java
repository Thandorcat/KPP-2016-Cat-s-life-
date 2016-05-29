package panes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import saveloader.SaveLoader;
import saveloader.StatLoader;



public class StatPane extends AnchorPane {
  final int BTN_SIZE_X = 175;
  final int BTN_SIZE_Y = 50;

  AnchorPane svPane;
  Button back;
  Label longestGame;
  Label mostPressedButton;
  Label playerBot;
  StatLoader loader;
  SaveLoader saveload;

  public StatPane(AnchorPane pane, SaveLoader svload) {
    svPane = pane;
    saveload = svload;
    longestGame = new Label("Longest game: 285");
    longestGame.setTextAlignment(TextAlignment.CENTER);

    mostPressedButton = new Label("Most pressed button: Food");
    mostPressedButton.setTextAlignment(TextAlignment.CENTER);

    playerBot = new Label("Player - 1% Bot - 99%");
    playerBot.setTextAlignment(TextAlignment.CENTER);

    back = new Button("Back");
    back.setPrefSize(BTN_SIZE_X, BTN_SIZE_Y);
    back.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        svPane.setVisible(true);
        setVisible(false);
      }
    });

    AnchorPane.setLeftAnchor(back, 212.5);
    AnchorPane.setTopAnchor(back, 250.0);

    AnchorPane.setLeftAnchor(longestGame, 200.5);
    AnchorPane.setTopAnchor(longestGame, 60.0);
    AnchorPane.setLeftAnchor(mostPressedButton, 160.5);
    AnchorPane.setTopAnchor(mostPressedButton, 120.0);
    AnchorPane.setLeftAnchor(playerBot, 180.5);
    AnchorPane.setTopAnchor(playerBot, 180.0);
    getChildren().addAll(longestGame, mostPressedButton, playerBot, back);
  }

  public void getStat() {
    loader = new StatLoader(saveload);
    longestGame.setText("Longest game: " + loader.getLongest());
    playerBot
        .setText("Player - " + loader.getPlayer() + "% Bot - " + (100 - loader.getPlayer()) + "%");
    if (loader.getMostCommon() == 1) {
      mostPressedButton.setText("Most pressed button: Food");
    } else {
      mostPressedButton.setText("Most pressed button: Care");
    }
  }
}
