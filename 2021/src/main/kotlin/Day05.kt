import utils.FileManager
import utils.Logger
import kotlin.math.abs

typealias Coordinate = Pair<Int, Int>

fun main() {
    val lines = FileManager.read(5)
    val coordinates = lines.map { line ->
        val coords = line.split(" -> ")
        val convertedCoords = coords.map { pair ->
            val pairSplit = pair.split(",")
            Coordinate(pairSplit[0].toInt(), pairSplit[1].toInt())
        }
        Pair(convertedCoords[0], convertedCoords[1])
    }

    fun calculateStep(leftValue: Int, rightValue: Int): Int {
        val diff = rightValue - leftValue
        return if (diff == 0) diff else diff / abs(diff)
    }

    fun count(onlyOrthogonal: Boolean): Int {
        val coordinateMap = mutableMapOf<Coordinate, Boolean>()
        for (coordinatePair in coordinates) {
            var left = coordinatePair.first; val right = coordinatePair.second

            val xStep = calculateStep(left.first, right.first)
            val yStep = calculateStep(left.second, right.second)

            if (onlyOrthogonal && !(xStep == 0 || yStep == 0)) continue

            while (true) {
                coordinateMap[left] = coordinateMap[left]?.run { true } ?: false

                if (left == right) break
                left = Pair(left.first + xStep, left.second + yStep)
            }
        }
        return coordinateMap.values.count { value -> value }
    }

    fun solvePuzzle1(): Int = count(true)

    fun solvePuzzle2(): Int = count(false)

    Logger.logResults(solvePuzzle1(), solvePuzzle2())
}
