package panes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import timer.Timer;

public class MenuPane extends StackPane {
  Button cont;
  Button newgame;
  Button exitgame;
  Button pause;
  Rectangle back;
  Timer timer;
  AnchorPane gamePane;
  FlowPane btns;

  public void init(Timer tmr, AnchorPane gPane) {
    timer = tmr;
    gamePane = gPane;
    cont = new Button("Continue");
    newgame = new Button("New Game");
    exitgame = new Button("Exit");
    pause = new Button("Pause");
    cont.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        setVisible(false);
        gamePane.setVisible(true);
        timer.start();
      }
    });
    newgame.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        setVisible(false);
        gamePane.setVisible(true);
        timer.restart();
      }
    });
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
    gamePane.getChildren().add(pause);
    back = new Rectangle(600, 370, Color.WHITE);// 600x370 - size of scene
    back.setOpacity(0.5);
    getChildren().add(back);
    setAlignment(Pos.CENTER);
    btns = new FlowPane();
    btns.setOrientation(Orientation.VERTICAL);
    btns.setAlignment(Pos.CENTER);
    btns.setVgap(10);
    btns.getChildren().addAll(cont, newgame, exitgame);
    getChildren().add(btns);

  }
}
