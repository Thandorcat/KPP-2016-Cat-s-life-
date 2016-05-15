/** 
  * Quick Sort on Scala
  */
package scala

import saveloader.SaveInfo

class ScalaSort {
  def sort(data: Array[SaveInfo]) {

    def qSort(begin: Int, end: Int) {
      val temp = data((begin + end) / 2).getNum
      var i = begin
      var j = end
      while (i <= j) {
        while (data(i).getNum > temp) {
          i += 1
        }
        while (data(j).getNum < temp) {
          j -= 1
        }
        if (i <= j) {
          val temp = data(i);
          data(i) = data(j)
          data(j) = temp
          i += 1
          j -= 1
        }
      }
      if (begin < j) qSort(begin, j)
      if (j < end) qSort(i, end)
    }
    qSort(0, data.length - 1)
  }
}