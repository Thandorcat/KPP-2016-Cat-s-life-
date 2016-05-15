/** Contains sprite of faces, changing faces */

package emotion;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import needs.Needs;

public class Emotion extends Thread{
  final int FACE_WIDTH = 75;
  final int FACE_HEIGHT = 60;
  final int START_OF_FACES_X = 570;
  final int START_OF_FACES_Y = 25;
  ImageView emotion;
  Needs food, care;
  int stfood, stcare;

  public void setImage(ImageView em) {
    emotion = em;
  }

  public void setNeeds(Needs fd, Needs cr){
    food = fd;
    care = cr;
  }
  
  public void run() {
    while(true){
      stfood = food.getState();
      stcare = care.getState();
      /**
       * Sending number of face 0...3
       */
      if (stfood == 0) {
        setView(1);
        break;
      } else {
        if (stcare == 0) {
          setView(0);
        } else {
          if (stcare == 2 && stfood == 2) {
            setView(3);
          } else {
            setView(2);
          }
        }
      }
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
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
