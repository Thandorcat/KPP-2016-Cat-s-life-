/** Main game timer, changing values and bot logic */

package timer;

import javafx.animation.AnimationTimer;
import needs.Needs;
import emotion.Emotion;
import panes.MenuPane;

public class Timer extends AnimationTimer {
  int stfood, stcare, times = 0;
  Needs food, care;
  Emotion emotion;
  MenuPane mPane;
  int speed;
  boolean auto;

  @Override
  /**
   * Timer code, calls every 0.5 to 1.5 sec
   */
  public void handle(long now) {
    /**
     * 30 is ~0.5 sec
     */
    if (times > speed * 30) {
      food.tick();
      care.tick();
      stfood = food.getState();
      stcare = care.getState();
      /**
       * Sending number of face 0...3
       */
      if (stfood == 0) {
        emotion.setView(1);
        stop();
        mPane.theEnd();
      } else {
        if (stcare == 0) {
          emotion.setView(0);
        } else {
          if (stcare == 2 && stfood == 2) {
            emotion.setView(3);
          } else {
            emotion.setView(2);
          }
        }
      }
      times = 0;
    }
    if (times > speed * 15 && auto) {
      food.fire();
      care.fire();
      times++;
    } else {
      times++;
    }
  }

  /**
   * Add all classes to work with
   */
  public void setNeeds(Needs fd, Needs cr) {
    food = fd;
    care = cr;
  }

  public void setEmotion(Emotion em) {
    emotion = em;
  }

  public void setSpeed(int spd) {
    speed = spd;
  }

  public void setPane(MenuPane pane) {
    mPane = pane;
  }

  public void setAuto(boolean aut) {
    auto = aut;
  }

  /**
   * Restart of timer and needs values in new game
   */
  public void restart() {
    food.restart();
    care.restart();
    times = 0;
    emotion.setView(2);
    start();
  }
}
