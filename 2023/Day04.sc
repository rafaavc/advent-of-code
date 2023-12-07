import util.fs
import scala.collection.mutable

val problem = fs.day[Int](4) _

case class Card(id: Int, nWinners: Int)

def parseCard(line: String): Card = line match {
  case s"Card $n: $winners | $owned" =>
    val winnerSet = winners.split(' ').filter(!_.isBlank).toSet
    val count = owned.split(' ').filter(!_.isBlank).count(winnerSet.contains)
    Card(n.dropWhile(_.isSpaceChar).toInt, count)
}

problem(1) { (lines: Iterator[String]) =>
  lines.map(parseCard).map { card =>
    if (card.nWinners > 0) 1 << card.nWinners - 1 else 0
  }.sum
}

problem(2) { (lines: Iterator[String]) =>
  val copies = mutable.Map[Int, Long]()
  lines.toSeq
    .map(parseCard)
    .map { card =>
      copies(card.id) = 0
      card
    }
    .foreach { card =>
      copies(card.id) += 1
      (1 to card.nWinners).foreach { diff =>
        copies(card.id + diff) += copies(card.id)
      }
    }
  copies.values.sum.toInt
}
