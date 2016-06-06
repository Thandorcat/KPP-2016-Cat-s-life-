/** Searching, writing and reading from files */
package save;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import save.SaveParser;

import javafx.stage.FileChooser;

public class Save {

  final double FOOD_SUB = 0.1;
  final double CARE_SUB = 0.2;
  final String DIR_NAME = "Saves";
  int num_of_steps = 0;
  File dir ,file, read;
  File[] files;
  FileChannel save;
  FileChannel load;
  ByteBuffer buff;
  boolean fpressed, cpressed;
  SaveParser parser = new SaveParser();

  public void init(boolean auto, int speed) {
    fpressed = cpressed = false;
    buff = ByteBuffer.allocate(1024);
    num_of_steps = 0;
    buff.putInt(0);
    if(auto){
      buff.putInt(1);
    } else {
      buff.putInt(0);
    }
    buff.putInt(speed);
  }

  /**
   * Reading from file or choosing via filechooser 
   */
  @SuppressWarnings("resource")
  public int initread(String file) {
    try {
      if(file.equals("Open...")){
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new java.io.File("C:\\Users\\Thandor-Laptop\\workspace\\Cat's life\\Saves"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(" catsv", "*.catsv"));
        fileChooser.setTitle("Выбрать файл с сохранением");
        read = null;
        while(read==null){
          read = fileChooser.showOpenDialog(null);
        }
      } else {
        read =  new File(DIR_NAME+'\\'+file);
      }
      load = new FileInputStream(read).getChannel();
      long len = load.size();
      buff = ByteBuffer.allocate((int) (len));
      load.read(buff);
      buff.rewind();
      load.close();
      /**
       * Sending values to parser and displaying result in console
       */
      System.out.println(parser.parse(buff.getInt()));
      System.out.println(parser.parse(buff.getInt()==1));
      int speed = buff.getInt();
      System.out.println(parser.parse((double)speed));
      return speed;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
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
    num_of_steps++;
    fpressed = cpressed = false;
  }

  @SuppressWarnings("resource")
  /**
   * Writing game in file
   */
  public void write() {
    buff.putInt(-1);
    buff.limit(buff.position());
    buff.rewind();
    buff.putInt(num_of_steps);
    buff.rewind();
    try {
      dir = new File(DIR_NAME);
      files = dir.listFiles(); 
      int len = files.length;
      file = new File(DIR_NAME+'\\'+Integer.toString(len+1)+".catsv");
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
    System.out.println(parser.parse(val));
    return val;
  }
}
