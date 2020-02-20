import scala.reflect.ClassTag

object Transformer {

  val transformations: Seq[Transformation] = Seq(
    NoTransformation,
    Rotate90,
    Rotate180,
    Rotate270,
    MirrorH,
    MirrorV,
    Transpose,
    TransposeInv
  )

}

object NoTransformation extends Transformation {
  def name = "No transformation"
  def newPos(pos: (Int, Int), size: (Int, Int)): (Int, Int) = pos
  def oldPos(pos: (Int, Int), size: (Int, Int)): (Int, Int) = pos
}

object Rotate90 extends Transformation {
  def name = "Rotate 90' clockwise"
  override def sameDimensions = false
  def newPos(pos: (Int, Int), size: (Int, Int)): (Int, Int) = (pos._2, size._1 - pos._1 - 1)
  def oldPos(pos: (Int, Int), size: (Int, Int)): (Int, Int) = (size._2 - pos._2 - 1, pos._1)
}

object Rotate180 extends Transformation {
  def name = "Rotate 180' clockwise"
  def newPos(pos: (Int, Int), size: (Int, Int)): (Int, Int) = (size._1 - pos._1 - 1, size._2 - pos._2 - 1)
  def oldPos(pos: (Int, Int), size: (Int, Int)): (Int, Int) = (size._1 - pos._1 - 1, size._2 - pos._2 - 1)
}

object Rotate270 extends Transformation {
  def name = "Rotate 270' clockwise"
  override def sameDimensions = false
  def newPos(pos: (Int, Int), size: (Int, Int)): (Int, Int) = (size._2 - pos._2 - 1, pos._1)
  def oldPos(pos: (Int, Int), size: (Int, Int)): (Int, Int) = (pos._2, size._1 - pos._1 - 1)
}

object MirrorH extends Transformation {
  def name = "Mirror horizontally"
  def newPos(pos: (Int, Int), size: (Int, Int)): (Int, Int) = (size._1 - pos._1 - 1, pos._2)
  def oldPos(pos: (Int, Int), size: (Int, Int)): (Int, Int) = (size._1 - pos._1 - 1, pos._2)
}

object MirrorV extends Transformation {
  def name = "Mirror vertically"
  def newPos(pos: (Int, Int), size: (Int, Int)): (Int, Int) = (pos._1, size._2 - pos._2 - 1)
  def oldPos(pos: (Int, Int), size: (Int, Int)): (Int, Int) = (pos._1, size._2 - pos._2 - 1)
}

object Transpose extends Transformation {
  def name = "Transpose"
  override def sameDimensions = false
  def newPos(pos: (Int, Int), size: (Int, Int)): (Int, Int) = (pos._2, pos._1)
  def oldPos(pos: (Int, Int), size: (Int, Int)): (Int, Int) = (pos._2, pos._1)
}

object TransposeInv extends Transformation {
  def name = "Inverted Transpose"
  override def sameDimensions = false
  def newPos(pos: (Int, Int), size: (Int, Int)): (Int, Int) = (size._2 - pos._2 - 1, size._1 - pos._1 - 1)
  def oldPos(pos: (Int, Int), size: (Int, Int)): (Int, Int) = (size._2 - pos._2 - 1, size._1 - pos._1 - 1)
}

trait Transformation {

  def name: String
  def sameDimensions: Boolean = true
  def newPos(pos: (Int, Int), size: (Int, Int)): (Int, Int)
  def oldPos(pos: (Int, Int), size: (Int, Int)): (Int, Int)

  def transform[T: ClassTag](m: Array[Array[T]], reverse: Boolean = false): Array[Array[T]] = {
    val result: Array[Array[T]] = if (sameDimensions) Array.ofDim[T](m.size, m.head.size) else Array.ofDim[T](m.head.size, m.size)
    for (r <- 0 until m.size; c <- 0 until m.head.size) {
      val (r2, c2) = if (reverse) oldPos((r, c), (m.size, m.head.size)) else newPos((r, c), (m.size, m.head.size))
      result(r2)(c2) = m(r)(c)
    }
    result
  }

}