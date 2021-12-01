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
        var currentWindow = 0
        var lastWindow: Int

        var count = 0
        for (i in numbers.indices) {
            if (i < 3) {
                currentWindow += numbers[i]
                continue
            }
            lastWindow = currentWindow
            currentWindow = lastWindow + numbers[i] - numbers[i - 3]
            if (currentWindow > lastWindow) count++
        }
        println(count)
    }

    solvePuzzle1()
    solvePuzzle2()
}
