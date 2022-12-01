import utils.FileManager
import utils.Logger

fun main() {
    val lines = FileManager.read(6)
    val numbers = lines[0].split(",").map { n -> n.toInt() }

    fun solve(days: Int): Long {
        var memo = Array(9) { 0L }
        for (number in numbers) memo[number] = memo[number] + 1

        for (day in 1..days) {
            val nextMemo = Array(9) { 0L }
            for (n in memo.indices) {
                if (n == 0) {
                    nextMemo[8] = memo[n]
                    nextMemo[6] = memo[n]
                } else nextMemo[n - 1] = nextMemo[n - 1] + memo[n]
            }
            memo = nextMemo
        }

        return memo.fold(0L) { acc, value -> acc + value }
    }

    fun solvePuzzle1(): Long = solve(80)

    fun solvePuzzle2(): Long = solve(256)

    Logger.logResults(solvePuzzle1(), solvePuzzle2())
}
