import utils.FileManager
import utils.Logger

fun main() {
    val lines = FileManager.read(11)

    val board = lines.map { line ->
        line.map { value -> value - '0' }.toTypedArray()
    }.toTypedArray()

    val directions = arrayOf(
        Pair(0, 1),
        Pair(1, 0),
        Pair(0, -1),
        Pair(-1, 0),
        Pair(1, 1),
        Pair(-1, -1),
        Pair(-1, 1),
        Pair(1, -1)
    )

    fun increment() {
        for (y in board.indices)
            for (x in board[y].indices) board[y][x]++
    }

    fun flash(x: Int, y: Int, originalX: Int, originalY: Int) {
        for ((xDelta, yDelta) in directions) {
            val currentX = x + xDelta; val currentY = y + yDelta
            if (currentX in board[0].indices && currentY in board.indices) {
                board[currentY][currentX]++
                if (board[currentY][currentX] == 10 && (currentY < originalY || (currentX < originalX && currentY == originalY)))
                    flash(currentX, currentY, originalX, originalY)
            }
        }
    }

    fun flashes() {
        for (y in board.indices)
            for (x in board[y].indices)
                if (board[y][x] > 9) flash(x, y, x, y)
    }

    fun reset(): Int {
        var count = 0
        for (y in board.indices)
            for (x in board[y].indices)
                if (board[y][x] > 9) {
                    board[y][x] = 0
                    count++
                }
        return count
    }

    fun solvePuzzle1(): Int {
        var result = 0
        for (i in 1..100) {
            increment()
            flashes()
            result += reset()
        }
        return result
    }

    fun check(): Boolean {
        for (y in board.indices)
            for (x in board[y].indices)
                if (board[y][x] != 0) return false
        return true
    }

    fun solvePuzzle2(): Int {
        var i = 1
        while (true) {
            increment()
            flashes()
            reset()
            if (check()) return i + 100
            i++
        }
    }

    Logger.logResults(solvePuzzle1(), solvePuzzle2())
}
