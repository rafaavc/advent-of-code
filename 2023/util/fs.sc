import java.io.FileInputStream
import scala.io.Source

private type Solver = Iterator[String] => String

def day(day: Int)(problem: Int)(solve: Solver): Unit = {
  val fileName = "%02d-".format(day)

  val input = Source.fromInputStream(new FileInputStream("./inputs/%02d/%s".format(day, problem)))
  val result = solve(input.getLines())
  println(s"Problem $problem result = $result")
}

