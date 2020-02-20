import java.io.{BufferedReader, File, FileReader}
import scala.collection.mutable.ListBuffer

/**
 * Helper class to retrieve data from an input file.
 *
 * @param filename   File name
 * @param separator  Separator character, whitespace by default
 */
case class InputFile(filename: String, separator: String = " ") {

  val lines: List[String] = InputFile.load(filename)

  def line(row: Int): Array[String] = InputFile.cells(lines(row), separator)

  def get(row: Int, col: Int): String = line(row)(col)
  def getInt(row: Int, col: Int): Int = get(row, col).toInt
  def getInts(row: Int): Array[Int] = line(row).map(_.toInt)

  /**
   * Lines mirroring
   * @return lines mirrored
   */
  def mirror: List[String] = lines match {
    case h :: t => h :: mirror(t)
    case _ => List()
  }

  private def mirror(list: List[String]): List[String] = list.map(_.reverse)

  /**
   * Lines clockwise rotation
   * @param times number of times the list must be clockwise rotated
   * @return rotated list
   */
  def rotate(times: Int): List[String] = {
    require(times >= 1 && times <= 3, "Rotation can be 1 to 3 only")
    var l = lines
    1 to times foreach { _ => l = rotate(l) }
    l
  }

  private def rotate(list: List[String]): List[String] = list match {
    case h :: t => firstLineRotate(h) :: otherLinesRotate(t)
    case _ => List()
  }

  private def firstLineRotate(line: String): String = {
    val l = line.split(separator)
    l(1) + separator + l(0) + separator + l(2) + separator + l(3)
  }

  private def otherLinesRotate(lines: List[String]): List[String] = lines.transpose.map(_.reverse).map(_.mkString)

}

/**
 * Helper object to read lines from an input file.
 */
object InputFile {

  /**
   * Returns the lines of a file as a list of strings.
   *
   * @param filename  File name
   * @return          Lines of the file
   */
  def load(filename: String): List[String] = {
    var file: BufferedReader = null
    try {
      val buffer = new ListBuffer[String]
      val file = new BufferedReader(new FileReader(filename))
      var line = file.readLine
      while (line != null) {
        buffer.append(line)
        line = file.readLine
      }
      buffer.toList
    }
    finally {
      if (file != null) file.close
    }
  }

  /**
   * Returns the cells of a line, separated by the given separator.
   *
   * @param line       A single line
   * @param separator  Separator
   * @return           Array of strings, representing the values in the line
   */
  def cells(line: String, separator: String = " "): Array[String] = line.split(separator)

  /**
   * Returns the characters of a line.
   *
   * @param line       A single line
   * @return           Array of strings, representing the characters in the line
   */
  def chars(line: String): Array[String] = line.toArray.map(_.toString)

  def filesInDir(dir: String): List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) d.listFiles.filter(_.isFile).toList else List()
  }
}