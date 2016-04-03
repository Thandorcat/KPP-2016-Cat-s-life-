package emotion;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public class Emotion {
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
    emotion.setViewport(new Rectangle2D(570 + (75 * x), 25 + (60 * y), 75, 60));
  }
}
