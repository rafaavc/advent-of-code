import java.io.FileInputStream
import scala.io.Source

private type Solver[T] = Iterator[String] => T

def day[T](day: Int)(file: Int, part: Int)(solve: Solver[T]): Unit = {
  val fileName = "%02d-".format(day)

  val input = Source.fromInputStream(new FileInputStream("./inputs/%02d/%s".format(day, file)))
  val result = solve(input.getLines())
  println(s"Day $day part $part result = $result")
}
