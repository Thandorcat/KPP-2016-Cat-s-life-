/** Main game timer, changing values and bot logic */

package timer;

import javafx.animation.AnimationTimer;
import java.util.Random;

import needs.Needs;
import emotion.Emotion;
import panes.MenuPane;
import save.Save;

public class Timer extends AnimationTimer {
  int stfood, stcare, times = 0;
  Needs food, care;
  Emotion emotion;
  MenuPane mPane;
  Random random;
  Save logs;
  int speed;
  boolean auto;
  boolean replay;

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
      if (!replay) {
        logs.tick();
      }
      stfood = food.getState();
      stcare = care.getState();
      /**
       * Sending number of face 0...3
       */
      if (stfood == 0) {
        emotion.setView(1);
        stop();
        if (!replay) {
          logs.write();
        }
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
    /**
     * Bot logic, just random press on buttons
     */
    if (times == speed * 15 && auto) {
      random = new Random();
      int x = random.nextInt(5);
      if (x == 3) {
        food.fire();
      }
      random = new Random();
      int y = random.nextInt(4);
      if (y == 3) {
        care.fire();
      }
      times++;
    }
    /**
     * Replay, saved values: 0 - pressed nothing 1 - pressed food 2 - pressed care 3 - pressed two
     * buttons -1 - end of game
     */
    if (times == speed * 15 && replay) {
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

  public void setSave(Save lg) {
    logs = lg;
  }

  public void setReplay(boolean rep) {
    replay = rep;
  }

  /**
   * Restart of timer and needs values in new game,
   * and initialising files for read or write 
   */
  public void restart() {
    food.restart();
    care.restart();
    times = 0;
    emotion.setView(2);
    if (replay) {
      logs.initread();
    } else {
      logs.init();
    }
    start();
  }
}
