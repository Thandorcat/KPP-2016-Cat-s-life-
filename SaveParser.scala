/** Parsing info from save files */
package save

class SaveParser {
  def parse(obj: Any): String = {
    obj match {
      case number: Int if (number > 4) => "Length of this game is: " + number.toString()
       case number: Int if (number == -1) => "Cat is dead."
      case number: Int => witchButton(number)
      case speed: Double => "Speed is on " + speed.toString()
      case mode: Boolean => "Auto play: " + " " + mode.toString()
      case _ => "Error. Sample not found."
    }
  }
  /**
   * Which button was pressed 
   */
  def witchButton(obj: Any): String = {
    obj match {
      case 0 => "Nothing pressed."
      case 1 => "Food pressed."
      case 2 => "Care pressed."
      case 3 => "Both buttons pressed."
      case _ => "Wrong parameter."
    }
  }
}