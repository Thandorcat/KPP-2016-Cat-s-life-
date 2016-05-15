/** Main game timer, changing values and bot logic */

package timer;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

import java.util.Random;

import needs.Needs;
import emotion.Emotion;
import panes.MenuPane;
import save.Save;

public class Timer extends AnimationTimer {
  final int TICK_TIME = 30;
  final int HALF_TICK_TIME = 15;
  int stfood, times = 0;
  ImageView face;
  Needs food, care;
  Emotion emotion;
  MenuPane mPane;
  Random random;
  Save logs;
  String file_for_read = null;
  int speed;
  boolean auto;
  boolean replay;
  boolean emstart = true;

  @Override
  /**
   * Timer code, calls every 0.5 to 1.5 sec
   */
  public void handle(long now) {
    /**
     * 30 is ~0.5 sec
     */
    if (times > speed * TICK_TIME) {
      food.tick();
      care.tick();
      if (!replay) {
        logs.tick();
      }
      stfood = food.getState();
      /**
       * Checking if cat is dead
       */
      if (stfood == 0) {
        stop();
        if (!replay) {
          logs.write();
        }
        mPane.theEnd();
      }
      times = 0;
    }
    /**
     * Bot logic, just random press on buttons
     */
    if (times == speed * HALF_TICK_TIME && auto) {
      random = new Random();
      int x = random.nextInt(4);
      if (x == 1) {
        food.fire();
      }
      random = new Random();
      int y = random.nextInt(3);
      if (y == 1) {
        care.fire();
      }
      times++;
    }
    /**
     * Replay, saved values: 0 - pressed nothing 1 - pressed food 2 - pressed care 3 - pressed two
     * buttons -1 - end of game
     */
    if (times == speed * HALF_TICK_TIME && replay) {
      int val = logs.read();
      switch (val) {
        case 1: {
          food.fire();
          break;
        }
        case 2: {
          care.fire();
          break;
        }
        case 3: {
          food.fire();
          care.fire();
          break;
        }
        case -1: {
          emotion.setView(1);
          stop();
          mPane.theEnd();
        }
      }
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

  public void setEmotionImage(ImageView fc) {
    face = fc;
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

  public void setFile(String file) {
    file_for_read = file;
  }

  public void setSave(Save lg) {
    logs = lg;
  }

  public void setReplay(boolean rep) {
    replay = rep;
  }

  /**
   * Restart of timer and needs values in new game, and initialising files for read or write
   */
  public void restart() {
    food.restart();
    care.restart();
    times = 0;
    emotion = new Emotion();
    emotion.setImage(face);
    emotion.setNeeds(food, care);
    emotion.start();
    if (replay) {
      speed = logs.initread(file_for_read);
    } else {
      logs.init(auto, speed);
    }
    start();
  }
}
