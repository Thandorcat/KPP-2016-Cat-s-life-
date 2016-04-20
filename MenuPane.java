/** Main and pause menus, restart logic and choosing game settings */

package panes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import timer.Timer;
import cat.Cat;

public class MenuPane extends StackPane {
  final int BTN_SIZE_X = 175;
  final int BTN_SIZE_Y = 50;
  Button cont;
  Button newgame;
  Button exitgame;
  Button pause;
  Button start;
  Label dead;
  ChoiceBox<String> catstyle;
  ChoiceBox<String> level;
  ChoiceBox<String> auto;
  Rectangle back;
  Cat catbody;
  Timer timer;
  AnchorPane gamePane;
  FlowPane newset;
  FlowPane btns;

  public void init(Timer tmr, AnchorPane gPane, Cat catb) {
    timer = tmr;
    gamePane = gPane;
    catbody = catb;
    cont = new Button("Continue");
    newgame = new Button("New Game");
    exitgame = new Button("Exit");
    start = new Button("Start");
    pause = new Button("Pause");
    dead = new Label("Your cat is dead!");
    dead.setVisible(false);

    /**
     * Choose boxes to setting the game
     */
    catstyle =
        new ChoiceBox<String>(FXCollections.observableArrayList("Orange", "Blue", "Grey", "Brown"));
    catstyle.getSelectionModel().selectFirst();
    catstyle.setPrefSize(BTN_SIZE_X, BTN_SIZE_Y);

    level = new ChoiceBox<String>(
        FXCollections.observableArrayList("Calm cat", "Funny cat", "Naughty cat"));
    level.getSelectionModel().selectFirst();
    level.setPrefSize(BTN_SIZE_X, 50);

    auto = new ChoiceBox<String>(FXCollections.observableArrayList("Manual play", "Auto play"));
    auto.getSelectionModel().selectFirst();
    auto.setPrefSize(BTN_SIZE_X, BTN_SIZE_Y);

    /**
     * All menu buttons handlers
     */
    cont.setVisible(false);
    cont.setPrefSize(BTN_SIZE_X, BTN_SIZE_Y);
    cont.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        setVisible(false);
        gamePane.setVisible(true);
        timer.start();
      }
    });

    newgame.setPrefSize(BTN_SIZE_X, BTN_SIZE_Y);
    newgame.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        btns.setVisible(false);
        newset.setVisible(true);
        dead.setVisible(false);
      }
    });

    start.setPrefSize(BTN_SIZE_X, BTN_SIZE_Y);
    start.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        setVisible(false);
        gamePane.setVisible(true);
        cont.setVisible(true);
        btns.setVisible(true);
        newset.setVisible(false);
        timer.restart();
      }
    });

    exitgame.setPrefSize(BTN_SIZE_X, BTN_SIZE_Y);
    exitgame.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        timer.stop();
        System.exit(0);
      }
    });
    pause.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        timer.stop();
        gamePane.setVisible(false);
        setVisible(true);
      }
    });
    
    /**
     * Placing button in left top corner
     */
    AnchorPane.setLeftAnchor(pause, 15.0);
    AnchorPane.setTopAnchor(pause, 13.0);

    catstyle.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
        if (new_val.equals("Orange")) {
          catbody.chcat(0);
        }
        if (new_val.equals("Blue")) {
          catbody.chcat(1);
        }
        if (new_val.equals("Grey")) {
          catbody.chcat(2);
        }
        if (new_val.equals("Brown")) {
          catbody.chcat(3);
        }
      }
    });
    level.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
        if (new_val.equals("Calm cat")) {
          timer.setSpeed(3);
        }
        if (new_val.equals("Funny cat")) {
          timer.setSpeed(2);
        }
        if (new_val.equals("Naughty cat")) {
          timer.setSpeed(1);
        }
      }
    });
    auto.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
        if (new_val.equals("Manual play")) {
          timer.setAuto(false);
        }
        if (new_val.equals("Auto play")) {
          timer.setAuto(true);
        }
      }
    });


    gamePane.getChildren().add(pause);
    back = new Rectangle(600, 370, Color.WHITE);// 600x370 - size of scene
    back.setOpacity(0.5);
    getChildren().add(back);
    setAlignment(Pos.CENTER);

    newset = new FlowPane();
    newset.setOrientation(Orientation.VERTICAL);
    newset.setAlignment(Pos.CENTER);
    newset.setVgap(10);
    newset.getChildren().addAll(catstyle, level, auto, start);
    newset.setVisible(false);

    btns = new FlowPane();
    btns.setOrientation(Orientation.VERTICAL);
    btns.setAlignment(Pos.CENTER);
    btns.setVgap(10);
    btns.getChildren().addAll(dead, cont, newgame, exitgame);

    getChildren().addAll(btns, newset);

  }

  public void theEnd() { /** End of game (cat is dead) */
    cont.setVisible(false);
    dead.setVisible(true);
    gamePane.setVisible(false);
    setVisible(true);
  }

}
