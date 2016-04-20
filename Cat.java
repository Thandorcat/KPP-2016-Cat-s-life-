/** Cat sprite, changing cats */

package cat;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Cat {
  ImageView cat;
  int numcat;
  final int CAT_WIDTH = 142;
  final int CAT_HEIGHT = 170;

  public Cat(int num) {/** initial setting of cat */
    numcat = num;
    try {
      cat = new ImageView("Sprites/cats.png");
    } catch (Throwable e) {
      System.err.println("error while setting up the image");
    }
    cat.setViewport(new Rectangle2D(CAT_WIDTH * numcat, 0, CAT_WIDTH, CAT_HEIGHT));
    AnchorPane.setLeftAnchor(cat, 230.0);
    AnchorPane.setTopAnchor(cat, 150.0);
  }

  public void chcat(int num) {/** Changing cat colour */
    numcat = num;
    cat.setViewport(new Rectangle2D(CAT_WIDTH * numcat, 0, CAT_WIDTH, CAT_HEIGHT));
  }

  public ImageView getImView() {
    return cat;
  }
}
