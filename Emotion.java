package emotion;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public class Emotion {
  final int FACE_WIDTH = 75;
  final int FACE_HEIGHT = 60;
  ImageView emotion;

  public void setImage(ImageView em) {
    emotion = em;
  }

  public void setView(int num) {
    int x, y;
    if (num < 2) {
      y = 0;
      x = num;
    } else {
      y = 1;
      x = num - 2;
    }
    emotion.setViewport(new Rectangle2D(570 + (FACE_WIDTH * x), 25 + (FACE_HEIGHT * y), FACE_WIDTH, FACE_HEIGHT));//570x25 - start of faces on sprite
  }
}
