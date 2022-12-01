import utils.FileManager
import utils.Logger

typealias Point = Pair<Int, Int>

fun main() {
    val lines = FileManager.read(9)

    val heightmap = lines.map { line -> line.toCharArray().map { c -> c.code - '0'.code } }.toTypedArray()

    fun getSurroundingPoints(point: Point): List<Point> {
        val x = point.first
        val y = point.second

        val points = mutableListOf<Point>()

        if (x - 1 >= 0) points.add(Point(x - 1, y))
        if (y - 1 >= 0) points.add(Point(x, y - 1))
        if (x + 1 < heightmap[0].size) points.add(Point(x + 1, y))
        if (y + 1 < heightmap.size) points.add(Point(x, y + 1))

        return points
    }

    fun getLowPoints(): List<Point> {
        val lowPoints = mutableListOf<Point>()
        for ((y, line) in heightmap.withIndex()) {
            for ((x, value) in line.withIndex()) {
                val surroundingPoints = getSurroundingPoints(Point(x, y))
                if (surroundingPoints.filter { p -> heightmap[p.second][p.first] > value }.size == surroundingPoints.size)
                    lowPoints += Point(x, y)
            }
        }
        return lowPoints
    }

    fun solvePuzzle1(): Int
        = getLowPoints().fold(0) { acc, coords ->
            acc + heightmap[coords.second][coords.first] + 1
        }

    fun dfs(point: Point, visitedPoints: MutableSet<Point>): Int {
        visitedPoints.add(point)
        var amount = 1

        val surroundingPoints = getSurroundingPoints(point)
        for (p in surroundingPoints)
            if (!visitedPoints.contains(p) && (heightmap[p.second][p.first] != 9)) amount += dfs(p, visitedPoints)

        return amount
    }

    fun solvePuzzle2(): Int {
        val lowPoints = getLowPoints()
        val amounts = mutableListOf<Int>()

        for (point in lowPoints) {
            val value = dfs(point, mutableSetOf())
            amounts.add(value)
        }

        var result = 1
        for (_i in 0..2) {
            val max = amounts.maxOrNull()!!
            result *= max
            amounts.remove(max)
        }

        return result
    }

    Logger.logResults(solvePuzzle1(), solvePuzzle2())
}
