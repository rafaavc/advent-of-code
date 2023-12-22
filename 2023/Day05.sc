import util.fs
import scala.collection.mutable

val problem = fs.day[Long](5) _

case class Map(sourceName: String, destinationName: String, mappers: mutable.ArrayBuffer[Mapper] = mutable.ArrayBuffer.empty) {
  def newMapper(mapper: Mapper) = mappers.addOne(mapper)

  def map(source: Long): Long = {
    var value: Option[Long] = Option.empty
    val iter = mappers.iterator
    while (value.isEmpty && iter.hasNext)
      value = iter.next().map(source)
    value.getOrElse(source)
  }
}

case class Mapper(sourceStart: Long, destinationStart: Long, length: Long) {
  def map(source: Long): Option[Long] = {
    val diff = source - sourceStart
    Option.when(diff > 0 && diff < length)(destinationStart + diff)
  }
}

def parse(lines: Iterator[String]): (List[Long], List[Map]) = {
  val seeds = lines.next().drop(7).split(' ').map(_.toLong)
  val maps = mutable.ArrayBuffer[Map]()
  lines.foreach {
    case s"$source-to-$dest map:" =>
      maps.addOne(Map(source, dest))
    case s"$destStart $srcStart $length" =>
      maps.last.newMapper(Mapper(srcStart.toLong, destStart.toLong, length.toLong))
    case "" =>
  }
  (seeds.toList, maps.toList)
}

def getLocation(maps: List[Map], seed: Long): Long = maps.foldLeft(seed) { (result, map) => map.map(result) }

problem(1) { (lines: Iterator[String]) =>
  val (seeds, maps) = parse(lines)
  seeds.map(seed => getLocation(maps, seed)).min
}
