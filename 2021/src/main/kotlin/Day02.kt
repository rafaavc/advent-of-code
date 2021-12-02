import utils.FileManager
import utils.Logger

fun main() {
    val fileManager = FileManager(2)
    val lines = fileManager.readPuzzle1()

    fun solvePuzzle1(): Int {
        var depth = 0
        var horizontal = 0

        for (line in lines) {
            val tokens = line.split(" ")
            val value = tokens[1].toInt()
            when (tokens[0]) {
                "forward" -> horizontal += value
                "down" -> depth += value
                "up" -> depth -= value
            }
        }

        return depth * horizontal
    }

    fun solvePuzzle2(): Int {
        var depth = 0
        var horizontal = 0
        var aim = 0

        for (line in lines) {
            val tokens = line.split(" ")
            val value = tokens[1].toInt()
            when (tokens[0]) {
                "forward" -> {
                    horizontal += value
                    depth += aim * value
                }
                "down" -> aim += value
                "up" -> aim -= value
            }
        }

        return depth * horizontal
    }

    Logger.logResults(solvePuzzle1(), solvePuzzle2())
}
