import utils.FileManager

fun main() {
    val fileManager = FileManager(1)
    val lines = fileManager.readPuzzle1()
    val numbers = lines.map { line -> line.toInt() }

    fun solvePuzzle1() {
        val count = numbers.foldIndexed(0) { idx, acc, el ->
            if (idx > 0 && el > numbers[idx - 1]) acc + 1
            else acc
        }
        println(count)
    }

    fun solvePuzzle2() {
        val count = numbers.foldIndexed(0) { idx, acc, el ->
            if (idx > 2 && el > numbers[idx - 3]) acc + 1
            else acc
        }
        println(count)
    }

    solvePuzzle1()
    solvePuzzle2()
}
