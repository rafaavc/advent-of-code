import utils.FileManager

fun main() {
    val fileManager = FileManager(1)
    val lines = fileManager.readPuzzle1()
    val numbers = lines.map { line -> line.toInt() }

    fun solvePuzzle1() {
        var count = 0
        for (i in numbers.indices) {
            if (i > 0 && numbers[i] > numbers[i - 1]) count++
        }
        println(count)
    }

    fun solvePuzzle2() {
        var count = 0
        for (i in 3 until numbers.size) {
            if (numbers[i] > numbers[i - 3]) count++
        }
        println(count)
    }

    solvePuzzle1()
    solvePuzzle2()
}
