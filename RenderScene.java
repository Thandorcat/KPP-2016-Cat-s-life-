/**
 * Creating and showing main stage, scene, and layout of game interface
 */
package render;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import needs.Needs;
import panes.MenuPane;
import emotion.Emotion;
import timer.Timer;
import cat.Cat;


public class RenderScene extends Application {
  public Stage roomStage = null;

  @Override
  public void start(Stage primaryStage) {
    roomStage = new Stage(StageStyle.TRANSPARENT);
    StackPane root = new StackPane();
    AnchorPane anPane = new AnchorPane();
    ImageView back = null;
    ImageView table = null;
    ImageView emotion = null;
    Cat cat = new Cat(0);/** Creating classes with sprites */
    try {
      back = new ImageView("Sprites/back.jpg");

      table = new ImageView("Sprites/table.png");

      emotion = new ImageView("Sprites/cats.png");
    } catch (Throwable e) {
      System.err.println("error while setting up the image");
    }


    emotion.setViewport(new Rectangle2D(570, 25 + 60, 75, 60));// 570x25 - start of faces on sprite,
                                                               // 75x60 size of face

    Emotion face = new Emotion();
    face.setImage(emotion);

    Needs food = new Needs("Food!", 0.3, 0.1); /** Creating needs classes */
    Button btnfood = food.getButton();
    btnfood.setPrefSize(130, 80);
    ProgressBar foodbar = food.getBar();
    ImageView fsymb = new ImageView("Sprites/foodsymb.png");

    Needs care = new Needs("Care!", 0.3, 0.2);
    Button btncare = care.getButton();
    btncare.setPrefSize(130, 80);
    ProgressBar carebar = care.getBar();
    ImageView csymb = new ImageView("Sprites/care.png");

    Timer timer = new Timer();
    timer.setNeeds(food, care);
    timer.setEmotion(face);
    timer.setSpeed(2);

    AnchorPane.setLeftAnchor(table, 200.0); /** Positioning on pane all elements */
    AnchorPane.setTopAnchor(table, 250.0);

    AnchorPane.setLeftAnchor(emotion, 240.0);
    AnchorPane.setTopAnchor(emotion, 195.0);

    AnchorPane.setLeftAnchor(btncare, 438.0);
    AnchorPane.setTopAnchor(btncare, 270.0);
    AnchorPane.setLeftAnchor(btnfood, 30.0);
    AnchorPane.setTopAnchor(btnfood, 270.0);

    AnchorPane.setLeftAnchor(foodbar, 150.0);
    AnchorPane.setTopAnchor(foodbar, 13.0);
    AnchorPane.setLeftAnchor(fsymb, 113.0);
    AnchorPane.setTopAnchor(fsymb, 9.0);

    AnchorPane.setLeftAnchor(carebar, 350.0);
    AnchorPane.setTopAnchor(carebar, 13.0);
    AnchorPane.setLeftAnchor(csymb, 313.0);
    AnchorPane.setTopAnchor(csymb, 8.0);

    root.getChildren().add(back); /** Add them to pane */
    anPane.getChildren().add(cat.getImView());
    anPane.getChildren().add(table);
    anPane.getChildren().add(emotion);
    anPane.getChildren().add(btnfood);
    anPane.getChildren().add(btncare);
    anPane.getChildren().add(foodbar);
    anPane.getChildren().add(fsymb);
    anPane.getChildren().add(carebar);
    anPane.getChildren().add(csymb);
    anPane.setVisible(false);

    MenuPane mPane = new MenuPane();/** Menu class */
    mPane.init(timer, anPane, cat);
    timer.setPane(mPane);

    root.getChildren().add(anPane);
    root.getChildren().add(mPane);


    Scene scene = new Scene(root, 600, 370, null);// 600x370 - size of scene
    scene.getStylesheets().add((getClass().getResource("/CSS/style.css")).toExternalForm());
    roomStage.setScene(scene);
    roomStage.setResizable(false);
    roomStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
