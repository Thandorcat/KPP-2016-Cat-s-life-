/** Searching, writing and reading from files */
package save;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javafx.stage.FileChooser;

public class Save {

  final double FOOD_SUB = 0.1;
  final double CARE_SUB = 0.2;
  File file, read;
  File[] files;
  FileChannel save;
  FileChannel load;
  ByteBuffer buff;
  boolean fpressed, cpressed;

  public Save() {
    file = new File("Save.txt");
  }

  public void init() {
    fpressed = cpressed = false;
    buff = ByteBuffer.allocate(1024);
  }

  /**
   * Choosing saved game via FileChooser
   */
  @SuppressWarnings("resource")
  public void initread() {
    try {
      final FileChooser fileChooser = new FileChooser();
      fileChooser.setInitialDirectory(new java.io.File("C:/"));
      fileChooser.setTitle("Выбрать файл с сохранением");
      read = fileChooser.showOpenDialog(null);
      load = new FileInputStream(read).getChannel();
      long len = load.size();
      buff = ByteBuffer.allocate((int) (len));
      load.read(buff);
      buff.rewind();
      buff.rewind();
      buff.getInt();
      load.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Distinguish what button was pressed by sub value which is different for buttons
   * 
   * @param val
   */
  public void pressed(double val) {
    if (val == FOOD_SUB) {
      fpressed = true;
    }
    if (val == CARE_SUB) {
      cpressed = true;
    }
  }

  /**
   * saving values: 0 - pressed nothing 1 - pressed food 2 - pressed care 3 - pressed two buttons -1
   * - end of game
   */
  public void tick() {
    if (fpressed && cpressed) {
      buff.putInt(3);
    } else {
      if (fpressed) {
        buff.putInt(1);
      }
      if (cpressed) {
        buff.putInt(2);
      } else {
        buff.putInt(0);
      }
    }
    fpressed = cpressed = false;
  }

  @SuppressWarnings("resource")
  /**
   * writing game in file
   */
  public void write() {
    buff.putInt(-1);
    buff.limit(buff.position());
    buff.rewind();
    try {
      save = new FileOutputStream(file).getChannel();
      save.write(buff);
      save.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * reading every value
   * 
   * @return
   */
  public int read() {
    int val = buff.getInt();
    return val;
  }
}
