/**
 * Functions to create file or console output.
 */
trait Writer {

  /**
   * Creates a line of values, separated by a given characters.
   *
   * @param values     Array of values to be formatted into a line
   * @param separator  Separator to be inserted between the values, space by default
   */
  def line[T](values: Array[T], separator: String = " "): String = {
    values.map(_.toString).mkString(separator)
  }

  /**
   * Creates a file containing the given lines. Overwrites existing file.
   *
   * @param filename  File to be created
   * @param lines     Sequence of lines
   */
  def writeFile(filename: String, lines: Seq[String]): Unit = {
    var file: java.io.PrintWriter = null
    try {
      file = new java.io.PrintWriter(filename)
      lines.foreach(file.println(_))
      file.flush
    } finally {
      if (file != null) file.close
    }
  }

  /**
   * Prints a two-dimensional array to the console.
   *
   * @param matrix     The two-dimensional array to be logged
   * @param separator  Separator character
   * @param pad        Length of individual cells, left-padded with separator
   * @tparam T         Type of the matrix elements
   */
  def writeLog[T](matrix: Array[Array[T]], separator: String = "", pad: Int = 0): Unit = {
    matrix.foreach(row => {
      row.foreach(cell => Console.print(padded(cell.toString, separator, pad)))
      Console.println("")
    })
  }

  /**
   * Return a left-padded value of the string, using the given separator.
   * If a value is longer than the size, it gets padded by a single-character separator.
   *
   * @param value      The value to be padded
   * @param separator  Separator string to be applied
   * @param pad        Size of the result
   * @return           Padded string
   */
  def padded(value: String, separator: String, pad: Int): String = {
    if (separator == "" || pad == 0) value
    else {
      val padSize = 1.max(pad - value.length)
      (separator * padSize).slice(0, padSize) + value
    }
  }

}
