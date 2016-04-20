/** Contains sprite of faces, changing faces */

package emotion;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public class Emotion {
  final int FACE_WIDTH = 75;
  final int FACE_HEIGHT = 60;
  final int START_OF_FACES_X = 570;
  final int START_OF_FACES_Y = 25;
  ImageView emotion;

  public void setImage(ImageView em) {
    emotion = em;
  }

  /**
   * Changing emotions depends of current state
   */
  public void setView(int num) {
    int x, y;
    /**
     * Converting number in face
     */
    if (num < 2) {
      y = 0;
      x = num;
    } else {
      y = 1;
      x = num - 2;
    }
    emotion.setViewport(new Rectangle2D(START_OF_FACES_X + (FACE_WIDTH * x),
        START_OF_FACES_Y + (FACE_HEIGHT * y), FACE_WIDTH, FACE_HEIGHT));

  }
}
