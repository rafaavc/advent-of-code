import util.fs
import scala.collection.mutable

val problem = fs.day[Int](2) _

case class Colors(red: Int = 0, green: Int = 0, blue: Int = 0) {
  def withColorMax(color: String, n: Int): Colors = color match {
    case "red" if n > red => this.copy(red = n)
    case "green" if n > green => this.copy(green = n)
    case "blue" if n > blue => this.copy(blue = n)
    case _ => this
  }

  lazy val pow: Int = red * green * blue

  def <=(other: Colors): Boolean = red <= other.red && green <= other.green && blue <= other.blue
}

def parseGames(lines: Iterator[String]) = lines.foldLeft(mutable.Map[Int, Colors]()) { (acc, line) =>
  line match {
    case s"Game $gameId: $rounds" =>
      rounds.split("; ").foreach { cubes =>
        cubes.split(", ").foreach {
          case s"$n $color" =>
            val id = gameId.toInt
            if (!acc.contains(id)) acc(id) = Colors()
            acc(id) = acc(id).withColorMax(color, n.toInt)
        }
      }
      acc
  }
}

problem(1) { lines =>
  val max = Colors(12, 13, 14)
  parseGames(lines)
    .map { case (id, colors) =>
      if (colors <= max) id else 0
    }
    .sum
}

problem(2) { lines =>
  parseGames(lines)
    .map { case (_, colors) => colors.pow }
    .sum
}
