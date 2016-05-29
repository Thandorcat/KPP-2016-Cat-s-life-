package saveloader;

import java.io.File;

import scala.ScalaStat;

public class StatLoader {
  final String DIR_NAME = "Saves";
  File dir;
  File[] files;
  
  int longest;
  int mostcommon;
  int players;
  int[] length;
  Integer[] press;
  int[] played;
  ScalaStat statistic = new ScalaStat();
  

  public StatLoader(SaveLoader saveload){
    length = saveload.getLength();
    played = saveload.getPlayed();
    press = saveload.getPress();
    longest = statistic.longest(length);
    players = statistic.players(played);
    mostcommon = statistic.mostCommon(press);  
  }
  
  public int getMostCommon(){
    return mostcommon;
  }
  
  public int getLongest(){
    return longest;
  }
  
  public int getPlayer(){
    return players;
  }
}
