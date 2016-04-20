/** Needs logic, contains bars and buttons of needs */

package needs;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

public class Needs {
  Button btn;
  ProgressBar bar;
  DoubleProperty prog;
  double plus, minus;

  public Needs(String name, double pl, double min) {
    plus = pl;
    minus = min;
    bar = new ProgressBar();
    prog = new SimpleDoubleProperty(0.7);
    bar.progressProperty().bind(prog);
    btn = new Button(name);
    btn.setDisable(true);
    btn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        if (prog.getValue() < 1.0) {
          if (prog.getValue() > 1 - plus) {
            prog.setValue(1.0);
          } else if (prog.getValue() < 0) {
            prog.setValue(plus);
          } else {
            prog.setValue(prog.getValue() + plus);
          }
          btn.setDisable(true);
        }
      }
    });
  }

  public Button getButton() {
    return btn;
  }

  public ProgressBar getBar() {
    return bar;
  }

  public void tick() {/** Subtract value in every timer's tick */
    if (prog.getValue() > minus) {
      prog.setValue(prog.getValue() - minus);
    } else {
      prog.setValue(-0.1);
    }
    btn.setDisable(false);
  }

  public int getState() {/** Returns current state of needs */
    if (prog.getValue() == -0.1)
      return 0;
    if (prog.getValue() >= 0.8)
      return 2;
    else
      return 1;
  }

  public void restart() {
    prog.setValue(0.8);
  }

  public void fire() {/** Self press of button for bot */
    btn.fire();
  }
}
