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
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import timer.Timer;
import cat.Cat;
import saveloader.SaveLoader;

public class MenuPane extends StackPane {
  final int BTN_SIZE_X = 175;
  final int BTN_SIZE_Y = 50;
  final int TABLE_SIZE_X = 250;
  final int TABLE_SIZE_Y = 250;
  final int SCENE_SIZE_X = 600;
  final int SCENE_SIZE_Y = 370;
  String choosenfile;
  Button cont;
  Button newgame;
  Button exitgame;
  Button pause;
  Button start;
  Button startsave;
  Button sortjava;
  Button sortscala;
  Button resetsave;
  Label dead;
  ChoiceBox<String> catstyle;
  ChoiceBox<String> level;
  ChoiceBox<String> auto;
  ListView<String> saves;
  Rectangle back;
  Cat catbody;
  Timer timer;
  AnchorPane gamePane;
  AnchorPane savePane;
  FlowPane newset;
  FlowPane btns;
  SaveLoader loader;
  boolean replay = false;

  public void init(Timer tmr, AnchorPane gPane, Cat catb) {
    timer = tmr;
    gamePane = gPane;
    catbody = catb;
    cont = new Button("Continue");
    newgame = new Button("New Game");
    exitgame = new Button("Exit");
    start = new Button("Start");
    startsave = new Button("Start");
    sortjava = new Button("Sort in Java");
    sortscala = new Button("Sort in Scala");
    resetsave = new Button("Reset");
    pause = new Button("Pause");
    loader = new SaveLoader();

    dead = new Label("Your ñat is dead!");
    dead.setTextAlignment(TextAlignment.CENTER);
    dead.setPrefSize(BTN_SIZE_X, BTN_SIZE_Y);
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
    level.setPrefSize(BTN_SIZE_X, BTN_SIZE_Y);

    auto = new ChoiceBox<String>(
        FXCollections.observableArrayList("Manual play", "Auto play", "Replay"));
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
        if (!replay) {
          setVisible(false);
          timer.restart();
          gamePane.setVisible(true);
          cont.setVisible(true);
          btns.setVisible(true);
          newset.setVisible(false);
        } else {
          loader.fillData();
          saves.setItems(loader.getList());
          sortjava.setText("Sort in Java");
          sortscala.setText("Sort in Scala");
          newset.setVisible(false);
          savePane.setVisible(true);
        }
      }
    });

    startsave.setPrefSize(BTN_SIZE_X, BTN_SIZE_Y);
    startsave.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        setVisible(false);
        timer.setFile(choosenfile);
        timer.restart();
        gamePane.setVisible(true);
        cont.setVisible(true);
        btns.setVisible(true);
        savePane.setVisible(false);
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

    sortjava.setPrefSize(BTN_SIZE_X, BTN_SIZE_Y);
    sortjava.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        long time = System.currentTimeMillis();
        loader.sortJava();
        time = System.currentTimeMillis() - time;
        saves.setItems(loader.getList());
        sortjava.setText("Java: " + time + "ms");
      }
    });

    sortscala.setPrefSize(BTN_SIZE_X, BTN_SIZE_Y);
    sortscala.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        long time = System.currentTimeMillis();
        loader.sortScala();
        time = System.currentTimeMillis() - time;
        saves.setItems(loader.getList());
        sortscala.setText("Scala: " + time + "ms");
      }
    });

    resetsave.setPrefSize(BTN_SIZE_X, BTN_SIZE_Y);
    resetsave.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        loader.fillData();
        saves.setItems(loader.getList());
      }
    });

    /**
     * Placing button in left top corner
     */
    AnchorPane.setLeftAnchor(pause, 15.0);
    AnchorPane.setTopAnchor(pause, 13.0);

    catstyle.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
        /**
         * Selecting number of cat
         */
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
        /**
         * Selecting speed
         */
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
          timer.setReplay(false);
          catstyle.setDisable(false);
          level.setDisable(false);
          start.setText("Start");
          replay = false;
        }
        if (new_val.equals("Auto play")) {
          timer.setAuto(true);
          timer.setReplay(false);
          catstyle.setDisable(false);
          level.setDisable(false);
          start.setText("Start");
          replay = false;
        }
        if (new_val.equals("Replay")) {
          timer.setAuto(false);
          catstyle.setDisable(true);
          level.setDisable(true);
          timer.setReplay(true);
          start.setText("Next");
          replay = true;
        }
      }
    });
    
    /**
     * Creating listview for saves
     */
    saves = new ListView<String>();
    saves.setPrefSize(TABLE_SIZE_X, TABLE_SIZE_Y);
    saves.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
        choosenfile = new_val;
      }
    });
    saves.getSelectionModel().selectFirst();


    gamePane.getChildren().add(pause);
    back = new Rectangle(SCENE_SIZE_X, SCENE_SIZE_Y, Color.WHITE);
    back.setOpacity(0.5);
    getChildren().add(back);
    setAlignment(Pos.CENTER);

    /**
     * Setting menu panes
     */
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

    /**
     * Positioning on pane save list and buttons
     */
    savePane = new AnchorPane();
    AnchorPane.setLeftAnchor(saves, 50.0);
    AnchorPane.setTopAnchor(saves, 30.0);

    AnchorPane.setLeftAnchor(startsave, 220.0);
    AnchorPane.setTopAnchor(startsave, 300.0);

    AnchorPane.setLeftAnchor(sortjava, 350.0);
    AnchorPane.setTopAnchor(sortjava, 60.0);
    AnchorPane.setLeftAnchor(sortscala, 350.0);
    AnchorPane.setTopAnchor(sortscala, 120.0);
    AnchorPane.setLeftAnchor(resetsave, 350.0);
    AnchorPane.setTopAnchor(resetsave, 180.0);

    savePane.getChildren().addAll(saves, startsave, sortjava, sortscala, resetsave);
    savePane.setVisible(false);


    getChildren().addAll(btns, newset, savePane);

  }

  /**
   *  End of game (cat is dead) 
   */
  public void theEnd() { 
    cont.setVisible(false);
    dead.setVisible(true);
    gamePane.setVisible(false);
    setVisible(true);
  }

}
