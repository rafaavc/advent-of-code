import utils.FileManager
import utils.Logger
import kotlin.math.max

enum class InstructionType {
    X,
    Y
}

data class Instruction(val type: InstructionType, val value: Int)

typealias AxisPoints = MutableMap<Int, MutableSet<Point>>

fun main() {
    val lines = FileManager.read(13)

    val points = mutableSetOf<Point>()
    var pointsY = mutableMapOf<Int, MutableSet<Point>>()
    var pointsX = mutableMapOf<Int, MutableSet<Point>>()

    val instructions = mutableListOf<Instruction>()

    var maxX = -1; var maxY = -1

    var parsingInstructions = false
    for (line in lines) {
        if (line == "") {
            parsingInstructions = true
            continue
        }
        if (!parsingInstructions) {
            val split = line.split(",")
            val x = split[0].toInt()
            val y = split[1].toInt()
            maxX = max(x, maxX)
            maxY = max(y, maxY)
            val point = Pair(x, y)
            points.add(point)

            if (!pointsY.containsKey(y)) pointsY[y] = mutableSetOf()
            pointsY[y]!!.add(point)

            if (!pointsX.containsKey(x)) pointsX[x] = mutableSetOf()
            pointsX[x]!!.add(point)

        } else {
            val split = line.split(" ")
            val number = split[2].split("=")[1].toInt()

            if (split[2][0] == 'x') instructions.add(Instruction(InstructionType.X, number))
            else instructions.add(Instruction(InstructionType.Y, number))
        }
    }

    fun fold1(instructionValue: Int, axisPoints: AxisPoints, maxCoord: Int, newPointMaker: (Point, Int) -> Point): Int {
        var pointsRemoved = 0
        for (coord in instructionValue..maxCoord) {
            val diff = coord - instructionValue
            if (diff == 0 || !axisPoints.containsKey(coord) || instructionValue - diff < 0) continue
            if (instructionValue - diff < 0) {
                pointsRemoved += axisPoints[coord]?.size ?: 0
                continue
            }

            for (point in axisPoints[coord]!!) {
                if (points.contains(newPointMaker(point, instructionValue - diff))) {
                    pointsRemoved++
                }
            }
        }
        return pointsRemoved
    }

    fun solvePuzzle1(): Int {
        val instruction = instructions[0]

        val pointsRemoved = if (instruction.type == InstructionType.Y)
            fold1(instruction.value, pointsY, maxY) { point, changedValue ->
                Pair(point.first, changedValue)
            }
        else
            fold1(instruction.value, pointsX, maxX) { point, changedValue ->
                Pair(changedValue, point.second)
            }

        return points.size - pointsRemoved
    }

    fun getNextPoints(originalPoints: AxisPoints): AxisPoints {
        val result = mutableMapOf<Int, MutableSet<Point>>()
        for (entry in originalPoints) result[entry.key] = entry.value.toMutableSet()
        return result
    }

    fun fold2(
        instructionValue: Int,
        axisPoints: AxisPoints,
        maxCoord: Int,
        setMaxCoord: (Int) -> Unit,
        newPointMaker: (Point, Int) -> Point
    ) {
        val nextPointsY = getNextPoints(pointsY)
        val nextPointsX = getNextPoints(pointsX)

        for (coord in instructionValue..maxCoord) {
            val diff = coord - instructionValue
            if (diff == 0 || !axisPoints.containsKey(coord) || instructionValue - diff < 0) continue

            for (point in axisPoints[coord]!!) {
                val newPoint = newPointMaker(point, instructionValue - diff)
                if (!points.contains(newPoint)) {
                    points.add(newPoint)

                    if (!nextPointsY.containsKey(newPoint.second)) nextPointsY[newPoint.second] = mutableSetOf()
                    nextPointsY[newPoint.second]!!.add(newPoint)

                    if (!nextPointsX.containsKey(newPoint.first)) nextPointsX[newPoint.first] = mutableSetOf()
                    nextPointsX[newPoint.first]!!.add(newPoint)
                }
                nextPointsY[point.second]?.remove(point)
                nextPointsX[point.first]?.remove(point)
                points.remove(point)
            }
        }

        setMaxCoord(instructionValue - 1)
        pointsY = nextPointsY
        pointsX = nextPointsX
    }

    fun solvePuzzle2(): Int {
        val setMaxY = { value: Int -> maxY = value }
        val setMaxX = { value: Int -> maxX = value }

        for (instruction in instructions) {
            if (instruction.type == InstructionType.Y)
                fold2(instruction.value, pointsY, maxY, setMaxY) { point, changedValue ->
                    Pair(point.first, changedValue)
                }
            else
                fold2(instruction.value, pointsX, maxX, setMaxX) { point, changedValue ->
                    Pair(changedValue, point.second)
                }
        }

        for (y in 0..maxY) {
            for (x in 0..maxX) {
                if (points.contains(Point(x, y))) print("X ")
                else print(". ")
            }
            println()
        }

        return -1
    }

    Logger.logResults(solvePuzzle1(), solvePuzzle2())
}
