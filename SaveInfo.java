/** 
  * Contains info about save file
  */
package saveloader;

public class SaveInfo {
  String name;
  int num_of_steps;

  public SaveInfo(String nm, int num) {
    name = nm;
    num_of_steps = num;
  }

  public String getName() {
    return name;
  }

  public int getNum() {
    return num_of_steps;
  }

}
