import util.fs
import scala.collection.mutable

// FIXME bad code

val problem = fs.day[Int](3) _

def pad(l: List[List[Char]]): List[List[Char]] = {
  val padding = List.fill(l.head.length)('.')
  (padding :: l) :+ padding
}

def isSymbol(c: Char): Boolean = !c.isDigit && c != '.'

case class PartsState(currentNumber: Int = 0, isPart: Boolean = false, result: Int = 0)

problem(1) { (lines: Iterator[String]) =>
  val linesList = lines.map(line => ('.' :: line.toCharArray.toList) :+ '.').toList
  pad(linesList).sliding(3).map {
    case List(previous, current, next) =>
      current.indices.foldLeft(PartsState()) { case (s@PartsState(n, part, result), i) =>
        if (current(i).isDigit) {
          val digit = current(i).asDigit
          val isPart = part || isSymbol(previous(i)) || isSymbol(next(i))
          s.copy(currentNumber = n * 10 + digit, isPart = isPart)
        } else {
          val isPart = isSymbol(previous(i)) || isSymbol(current(i)) || isSymbol(next(i))
          PartsState(
            isPart = isPart,
            result = if (part || isPart) result + n else result
          )
        }
      }.result
    case _ => 0
  }.sum
}

case class Number(n: Int = 0, startJ: Int = -1, endJ: Int = -1) {
  def ==(other: Number): Boolean = startJ == other.startJ
  def is(j: Int): Boolean = j >= startJ && j <= endJ
}

val lineNumbers = mutable.Map[Int, mutable.ArrayBuffer[Number]]()

def getNumber(i: Int, j: Int): Number = {
  val res = lineNumbers(i).filter(_.is(j))
  assert(res.length == 1)
  res.head
}

problem(2) { (lines: Iterator[String]) =>
  val linesList = pad(lines.map(line => ('.' :: line.toCharArray.toList) :+ '.').toList)
  linesList.zipWithIndex.foreach { case (current, i) =>
    lineNumbers(i) = mutable.ArrayBuffer[Number]()
    current.indices.foldLeft(Number()) { case (n@Number(curNum, curStartJ, curEndJ), j) =>
      if (current(j).isDigit) {
        val digit = current(j).asDigit
        n.copy(n = curNum * 10 + digit, if (curStartJ == -1) j else curStartJ)
      } else {
        if (curStartJ != -1)
          lineNumbers(i) += n.copy(endJ = j)
        Number()
      }
    }
  }

  linesList.zipWithIndex.map { case (line, i) =>
    line.zipWithIndex.map { case (c, j) =>
      if (c == '*') {
        val numbers = (for {
          diffX <- List(-1, 0, 1)
          diffY <- List(-1, 0, 1)
          if linesList(i + diffY)(j + diffX).isDigit
        } yield getNumber(i+diffY, j+diffX)).toSet
        if (numbers.size == 2) numbers.map(_.n).product
        else 0
      } else 0
    }.sum
  }.sum
}
