package timer;

import javafx.animation.AnimationTimer;
import needs.Needs;
import emotion.Emotion;

public class Timer extends AnimationTimer {
  int stfood, stcare, times = 0;
  Needs food, care;
  Emotion emotion;

  @Override
  public void handle(long now) {
    if (times > 110) {
      food.tick();
      care.tick();
      stfood = food.getState();
      stcare = care.getState();
      if (stfood == 0)
        emotion.setView(1);
      else {
        if (stcare == 0)
          emotion.setView(0);
        else {
          if (stcare == 2 && stfood == 2)
            emotion.setView(3);
          else
            emotion.setView(2);
        }
      }
      times = 0;
    } else
      times++;
  }

  public void setNeeds(Needs fd, Needs cr) {
    food = fd;
    care = cr;
  }

  public void setEmotion(Emotion em) {
    emotion = em;
  }

  public void restart() {
    food.restart();
    care.restart();
    times = 0;
    start();
  }
}
