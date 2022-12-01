import utils.FileManager
import utils.Logger

fun main() {
    val lines = FileManager.read(14)

    val text = lines[0]

    val correspondence = mutableMapOf<String, Map<String, Int>>()
    for (code in lines.subList(2, lines.size)) {
        val split = code.split(" -> ")
        val map = mutableMapOf<String, Int>()
        val encoding = split[0]
        correspondence[encoding] = map

        Integer.highestOneBit(1)

        val left = encoding[0] + split[1]
        val right = split[1] + encoding[1]

        map[left] = map[left]?.plus(1) ?: 1
        map[right] = map[right]?.plus(1) ?: 1
    }

    var counts = mutableMapOf<String, Long>()

    for (i in 0..text.length-2) {
        val key = text[i].toString() + text[i+1]
        counts[key] = counts[key]?.plus(1) ?: 1
    }

    fun solve(steps: Int): Long {
        val originalCounts = counts

        for (_i in 1..steps) {
            val nextCounts = counts.toMutableMap()
            for (key in counts.keys) {
                if (key !in correspondence) continue
                for (entry in correspondence[key]!!) {
                    val toAdd = entry.value * counts[key]!!
                    nextCounts[entry.key] = nextCounts[entry.key]?.plus(toAdd) ?: toAdd
                }
                nextCounts[key] = nextCounts[key]!!.minus(counts[key]!!)
            }
            counts = nextCounts
        }

        val letterCounts = mutableMapOf<Char, Long>()

        for (entry in counts) {
            for (c in entry.key) {
                val value = entry.value
                letterCounts[c] = (letterCounts[c]?.plus(value) ?: value).toLong()
            }
        }

        counts = originalCounts

        return (letterCounts.values.maxOrNull()!! - letterCounts.values.minOrNull()!!) / 2
    }

    fun solvePuzzle1(): Long = solve(10)

    fun solvePuzzle2(): Long = solve(40)

    Logger.logResults(solvePuzzle1(), solvePuzzle2())
}
