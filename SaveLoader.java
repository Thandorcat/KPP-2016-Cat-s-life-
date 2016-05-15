/** Searching, sorting and loading list of savefiles */
package saveloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import saveloader.SaveInfo;
import scala.ScalaSort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SaveLoader {
  File dir;
  File[] files;
  final String DIR_NAME = "Saves";
  ObservableList<String> list;
  ScalaSort scala = new ScalaSort();
  SaveInfo[] data;

  /** 
   * Searching for files and writing in data array 
   */
  @SuppressWarnings("resource")
  public void fillData() {
    dir = new File(DIR_NAME);
    files = dir.listFiles();
    data = new SaveInfo[files.length];
    for (int i = 0; i < files.length; i++) {
      try {
        File read = new File(DIR_NAME + '\\' + files[i].getName());
        FileChannel load;
        load = new FileInputStream(read).getChannel();
        long len = load.size();
        ByteBuffer buff = ByteBuffer.allocate((int) (len));
        load.read(buff);
        buff.rewind();
        load.close();
        data[i] = new SaveInfo(files[i].getName(), buff.getInt());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /** 
   * Creating list for ListView 
   */
  public ObservableList<String> getList() {
    list = FXCollections.observableArrayList("Open...");
    for (SaveInfo i : data) {
      list.add(i.getName());
    }
    return list;
  }

  /** 
   * Sorting array via Java 
   */
  public void sortJava() {
    qSort(data, 0, files.length - 1);
  }

  /** 
   * Sorting array via Scala
   */
  public void sortScala() {
    scala.sort(data);
  }

  /** 
   * Quick Sort on Java
   */
  public void qSort(SaveInfo[] array, int begin, int end) {
    int i = begin;
    int j = end;
    int x = array[(i + j) / 2].getNum();
    while (i <= j) {
      while (array[i].getNum() > x) {
        i++;
      }
      while (array[j].getNum() < x) {
        j--;
      }
      if (i <= j) {
        SaveInfo temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        i++;
        j--;
      }
    }
    if (begin < j) {
      qSort(array, begin, j);
    }
    if (i < end) {
      qSort(array, i, end);
    }
  }

}
