import utils.FileManager
import utils.Logger

class Puzzle(board: Array<Array<Int>>) {
    private val numbersIndexes: MutableMap<Int, Pair<Int, Int>> = mutableMapOf()
    private val indexesNumbers: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
    var isOver = false
        private set

    init {
        for ((y, line) in board.withIndex()) {
            for ((x, number) in line.withIndex()) {
                numbersIndexes[number] = Pair(x, y)
                indexesNumbers[Pair(x, y)] = number
            }
        }
    }

    fun setNumber(number: Int): Boolean {
        if (isOver) return false
        val position = numbersIndexes[number] ?: return false

        numbersIndexes.remove(number)
        indexesNumbers[position] = -1

        var horizontal = true
        var vertical = true

        for (i in 0..4) {
            if (indexesNumbers[Pair(i, position.second)]!! >= 0) horizontal = false
            if (indexesNumbers[Pair(position.first, i)]!! >= 0) vertical = false
        }

        isOver = horizontal || vertical
        return isOver
    }

    fun getUnsetSum(): Int = numbersIndexes.keys.sum()
}

fun main() {
    val lines = FileManager.read(4)
    val numbers = lines[0].split(",").map { line -> line.toInt() }
    val boards: MutableList<Array<Array<Int>>> = mutableListOf()

    var linePointer = 0
    for (i in 1 until lines.size) {
        if (lines[i] == "") {
            boards.add(Array(5) { Array(5) { 0 } })
            linePointer = 0
            continue
        }

        boards.last()[linePointer] = lines[i]
            .split(" ")
            .filter { line -> line != "" }
            .map { line -> line.toInt() }
            .toTypedArray()
        linePointer++
    }

    val puzzles = boards.map { board -> Puzzle(board) }

    fun solvePuzzle1(): Int {
        for (number in numbers)
            for (puzzle in puzzles)
                if (puzzle.setNumber(number))
                    return number * puzzle.getUnsetSum()
        return -1
    }

    fun solvePuzzle2(): Int {
        var last = -1
        for (number in numbers)
            for (puzzle in puzzles)
                if (puzzle.setNumber(number))
                    last = number * puzzle.getUnsetSum()
        return last
    }

    Logger.logResults(solvePuzzle1(), solvePuzzle2())
}
