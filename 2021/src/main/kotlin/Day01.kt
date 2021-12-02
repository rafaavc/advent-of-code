import utils.FileManager
import utils.Logger

fun main() {
    val lines = FileManager.read(1)
    val numbers = lines.map { line -> line.toInt() }

    fun solvePuzzle1()
        = numbers.foldIndexed(0) { idx, acc, el ->
            if (idx > 0 && el > numbers[idx - 1]) acc + 1
            else acc
        }

    fun solvePuzzle2()
        = numbers.foldIndexed(0) { idx, acc, el ->
            if (idx > 2 && el > numbers[idx - 3]) acc + 1
            else acc
        }

    Logger.logResults(solvePuzzle1(), solvePuzzle2())
}
