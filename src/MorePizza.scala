import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object MorePizza extends Solution {

  var maxSlices: Long = 0
  var pizzas: Array[Int] = Array()
  var zipped: Seq[(Int, Int)] = Seq()
  var order: ArrayBuffer[Int] = new ArrayBuffer()

  override def files = Seq("a_example.in", "b_small.in", "c_medium.in", "d_quite_big.in", "e_also_big.in")

  override def resources: String = "resources/"

  override def settings = SolutionSettings(4, 5000)

  override def input(inputFile: InputFile): Unit = {
    maxSlices = inputFile.getInt(0, 0)
    pizzas = inputFile.getInts(1)
    zipped = pizzas.toSeq.zipWithIndex
    order = new ArrayBuffer()
  }

  // Dummy greedy implementation by taking pizzas as long as there is enough space for them
  // Note, this is a deterministic function, will always produce the same result, no point running it repeatedly
  // Total score: 1,504,818,713
  def computeDummy(): Long = {
    var space = maxSlices
    for (i <- 0 until pizzas.size) if (pizzas(i) <= space) {
      order += i
      space -= pizzas(i)
    }
    maxSlices - space
  }

  // Shuffling the pizzas to provide nondeterministic results
  // Total score: 1,505,004,614
  override def compute(): Long = {
    order.clear()
    var space = maxSlices
    val shuffledPizzas: Seq[(Int, Int)] = Random.shuffle(zipped)
    for (i <- 0 until shuffledPizzas.size) if (shuffledPizzas(i)._1 <= space) {
      order += shuffledPizzas(i)._2
      space -= shuffledPizzas(i)._1
    }
    maxSlices - space
  }

  override def output(): Seq[String] = {
    Seq(order.size.toString, order.mkString(" "))
  }

}