package scala

class ScalaStat {

  def longest(data: Array[Int]) = data.max

  def mostCommon(data: Array[Integer]) = {
    val food = for(i <-  data if i==1) yield i
    val care = for(i <-  data if i==2) yield i
    if(food.length>care.length) 1 else 2
  }
  
  def players(data: Array[Int]) = {
    val player = for(i <-  data if i==0) yield i
    ((player.length*100)/data.length)+1
  }
}