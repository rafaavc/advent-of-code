import util.fs
import scala.collection.mutable

val problem = fs.day[Long](22) _

case class Coordinates(x: Int, y: Int, z: Int)
object Coordinates {
  def apply(x: String, y: String, z: String): Coordinates = Coordinates(x.toInt, y.toInt, z.toInt)
}

case class Brick(start: Coordinates, end: Coordinates) {
  val supporting: mutable.Set[Brick] = mutable.Set.empty
  val supportedBy: mutable.Set[Brick] = mutable.Set.empty
  val height = math.abs(end.z - start.z) + 1

  def mapXY[T](doThis: (Int, Int) => T): Seq[T] = for {
    x <- start.x to end.x
    y <- start.y to end.y
  } yield doThis(x, y)
}

def getProcessedBricks(lines: Iterator[String]): List[Brick] = {
  val heightMap = mutable.ArrayBuffer.fill(10, 10)(0)
  val brickMap = mutable.ArrayBuffer.fill(10, 10)(Option.empty[Brick])
  lines
    .map {
      case s"$x1,$y1,$z1~$x2,$y2,$z2" =>
        Brick(Coordinates(x1, y1, z1), Coordinates(x2, y2, z2))
    }
    .toList
    .sortBy(brick => brick.start.z min brick.end.z)
    .map { brick =>
      val currentMaxZ = brick.mapXY { (x, y) =>
        heightMap(x)(y)
      }.max

      val supportedBy = brick.mapXY { (x, y) =>
        if (heightMap(x)(y) == currentMaxZ) brickMap(x)(y)
        else None
      }.flatten

      brick.mapXY { (x, y) =>
        heightMap(x)(y) = currentMaxZ + brick.height
        brickMap(x)(y) = Some(brick)
      }
      brick.supportedBy.addAll(supportedBy)
      supportedBy.foreach(_.supporting.addOne(brick))
      brick
    }
}

problem(1) { (lines: Iterator[String]) =>
  getProcessedBricks(lines).foldLeft(0) { (result, brick) =>
    result + brick.supporting.forall(_.supportedBy.size > 1).compare(false)
  }
}

def simulateRemoval(brick: Brick): Int = brick.supporting.foldLeft(0) { (result, supported) =>
  supported.supportedBy.remove(brick)
  if (supported.supportedBy.isEmpty)
    result + 1 + simulateRemoval(supported)
  else result
}

def restoreTree(brick: Brick): Unit = brick.supporting.foreach { supported =>
  if (supported.supportedBy.isEmpty)
    restoreTree(supported)
  supported.supportedBy.addOne(brick)
}

problem(2) { (lines: Iterator[String]) =>
  getProcessedBricks(lines).foldLeft(0) { (result, brick) =>
    val count = simulateRemoval(brick)
    restoreTree(brick)
    result + count
  }
}
