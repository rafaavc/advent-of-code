import utils.FileManager
import utils.Logger
import java.util.*

data class Position(val x: Int, val y: Int, val totalRisk: Int): Comparable<Position> {
    override fun compareTo(other: Position) = totalRisk - other.totalRisk

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Position

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int = 31 * x + y
}

fun main() {
    val lines = FileManager.read(15)
    val directions = arrayOf(Pair(1, 0), Pair(0, 1), Pair(-1, 0), Pair(0, -1))

    var board = lines.map { line ->
        line.map { risk ->
            risk - '0'
        }.toTypedArray()
    }.toTypedArray()

    fun getPosition(x: Int, y: Int, currentRisk: Int = 0, entering: Boolean = true): Position? {
        if (x < 0 || y < 0 || y >= board.size || x >= board[0].size) return null
        return Position(x, y, currentRisk + (if (entering) board[y][x] else 0))
    }

    fun getNeighbours(position: Position): List<Position> = directions.mapNotNull { (x, y) ->
        getPosition(position.x + x, position.y + y, position.totalRisk)
    }

    fun getMinRisk(): Int {
        val visited = mutableSetOf<Position>()
        val q = PriorityQueue<Position>()

        val start = getPosition(0, 0, entering = false)!!

        q.add(start)
        visited.add(start)

        while(true) {
            val current = q.poll()

            if (current.x == board[0].size - 1 && current.y == board.size - 1) return current.totalRisk

            for (neighbour in getNeighbours(current))
                if (!visited.contains(neighbour)) {
                    q.add(neighbour)
                    visited.add(neighbour)
                }
        }
    }

    fun solvePuzzle1(): Int = getMinRisk()

    fun solvePuzzle2(): Int {
        board = board.map { line ->
            Array(line.size * 5) { i ->
                val value = line[i % line.size] + (i / line.size)
                if (value >= 10) value - 9 else value
            }
        }.toTypedArray()

        board = Array(board.size * 5) { i ->
            val line = board[i % board.size]

            Array(line.size) { j ->
                if (i == 0) line[j]
                else {
                    val value = line[j] + (i / board.size)
                    if (value >= 10) value - 9 else value
                }
            }
        }

        return getMinRisk()
    }

    Logger.logResults(solvePuzzle1(), solvePuzzle2())
}
