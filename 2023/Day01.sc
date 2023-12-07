//> using scala 2.13

import util.fs

val problem = fs.day[Int](1) _

problem(1) {
  _
    .map { (line: String) =>
      val first = line.find(_.isDigit).get.asDigit
      val second = line.findLast(_.isDigit).get.asDigit
      first * 10 + second
    }
    .sum
}

val spellingToDigit = Map(
  "one" -> 1,
  "two" -> 2,
  "three" -> 3,
  "four" -> 4,
  "five" -> 5,
  "six" -> 6,
  "seven" -> 7,
  "eight" -> 8,
  "nine" -> 9
)

implicit val ordering: Ordering[(String, Int)] = Ordering.by[(String, Int), Int](_._2)

def getValue(
  line: String,
  getIndex: (String, String) => Int,
  find: (String, Char => Boolean) => Option[Char],
  optimize: Map[String, Int] => (String, Int),
  compare: (Int, Int) => Boolean
): Int = {
  val spellingCandidates = spellingToDigit
    .map { case (spelling, digit) => spelling -> getIndex(line, spelling) }
    .filter { case (spelling, index) => index != -1 }
  val spelling = Option.when(spellingCandidates.nonEmpty)(optimize(spellingCandidates))
  val digit = find(line, _.isDigit).get.asDigit
  spelling.fold(digit) { case (spelling, index) =>
    if (compare(getIndex(line, digit.toString), index)) digit
    else spellingToDigit(spelling)
  }
}

problem(2) {
  _
    .map { (line: String) =>
      val left = getValue(line, _.indexOf(_), _.find(_), _.min, _ < _)
      val right = getValue(line, _.lastIndexOf(_), _.findLast(_), _.max, _ > _)
      left * 10 + right
    }
    .sum
}
