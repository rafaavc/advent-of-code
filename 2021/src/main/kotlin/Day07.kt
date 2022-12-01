import utils.FileManager
import utils.Logger
import kotlin.math.abs
import kotlin.math.min

fun main() {
    val lines = FileManager.read(7)
    val numbers = lines[0].split(",").map { n -> n.toInt() }

    val amountOfCrabsPerPosition = mutableMapOf<Int, Int>()
    for (n in numbers) amountOfCrabsPerPosition[n] = (amountOfCrabsPerPosition[n] ?: 0) + 1

    val minPosition = amountOfCrabsPerPosition.keys.minOrNull()!!
    val maxPosition = amountOfCrabsPerPosition.keys.maxOrNull()!!

    fun solvePuzzle1(): Int {
        val sortedPositions = amountOfCrabsPerPosition.keys.toTypedArray()
        sortedPositions.sort()

        val amountOfPositionsToCheck = maxPosition - minPosition + 1
        val rightSumAmounts = Array(amountOfPositionsToCheck) { 0 }

        for (i in amountOfPositionsToCheck - 2 downTo 0)
            rightSumAmounts[i] = rightSumAmounts[i+1] + (amountOfCrabsPerPosition[i+1] ?: 0)

        val totalAmount = amountOfCrabsPerPosition.values.sum()

        val dp = Array(amountOfPositionsToCheck) { 0 }

        // filling dp[0]
        for (i in 1 until amountOfPositionsToCheck) {
            val position = i + minPosition
            dp[0] += (position - sortedPositions[0]) * (amountOfCrabsPerPosition[position] ?: continue)
        }

        // filling the rest of dp
        for (i in 1 until amountOfPositionsToCheck)
            dp[i] = dp[i - 1] - rightSumAmounts[i - 1] + (totalAmount - rightSumAmounts[i - 1])

        return dp.minOrNull()!!
    }

    fun solvePuzzle2(): Int {
        var result = Integer.MAX_VALUE
        for (position in minPosition..maxPosition) {
            var fuelUsage = 0
            for ((pos, amount) in amountOfCrabsPerPosition.entries) {
                val distance = abs(position - pos)
                for (i in 1..distance) fuelUsage += i * amount
            }
            result = min(result, fuelUsage)
        }
        return result
    }

    Logger.logResults(solvePuzzle1(), solvePuzzle2())
}
