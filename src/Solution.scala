trait Solution extends App with Writer {

  // Override this to define input files (expected to be in the resources folder)
  def files: Seq[String]

  // Path to resources folder
  def resources: String

  // Define execution parameters
  def settings: SolutionSettings = SolutionSettings()

  // Process the input to initialize internal data
  def input(inputFile: InputFile): Unit

  // Compute the solution, and return the score
  def compute(): Long

  // Produce output according to specification
  def output(): Seq[String]

  def read(fileIndex: Int): Long = {
    val filename = resources + files(fileIndex)
    input(InputFile(filename))
    // check existing top score
    val name = files(fileIndex).dropRight(3)
    val filenames = InputFile.filesInDir(resources).map(_.getName)
    val scores = filenames.filter(f => f.startsWith(name) && f.endsWith(".out"))
      .map(f => f.drop(name.size + 1).dropRight(4)).map(_.toLong)
    if (scores.isEmpty) 0L else scores.max
  }

  def filename(fileIndex: Int, score: Long) = s"$resources${files(fileIndex).dropRight(2)}${score}.out"

  def write(fileIndex: Int, score: Long): Unit = {
    Console.println(s"File #$fileIndex new top score: $score")
    writeFile(filename(fileIndex, score), output)
  }

  val setting = settings
  val fileIndex = setting.fileIndex
  require(fileIndex >= 0 && fileIndex < files.size)
  var best: Long = read(fileIndex)
  Console.println("best: "+best)
  for (r <- 1 to setting.repeat) {
    val score = compute
    if (score > best) {
      write(fileIndex, score)
      best = score
    }
  }
}

case class SolutionSettings(fileIndex: Int = 0, repeat: Int = 1)

