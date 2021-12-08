import utils.FileManager
import utils.Logger
import kotlin.math.pow

fun main() {
    val lines = FileManager.read(8)
    val splitLines = lines.map { line -> line.split(" | ") }
    val signals = splitLines.map { line -> line[0].split(" ") }
    val outputs = splitLines.map { line -> line[1].split(" ") }

    /**
     * {
     *      representation length : corresponding number
     * }
     */
    val knownLengths = mapOf(
        Pair(2, 1),
        Pair(3, 7),
        Pair(4, 4),
        Pair(7, 8),
    )

    fun solvePuzzle1(): Int
        = outputs.fold(0) { acc, el ->
            acc + el.fold(0) { ac, e ->
                if (e.length in knownLengths) ac + 1
                else ac
            }
        }

    fun fill(
        digitSets: MutableSet<Set<Char>>,
        encodings: MutableMap<Int, Set<Char>>,
        rules: Map<Int, (s: Set<Char>) -> Boolean>,
        numbers: Array<Int>,
        lastNumber: Int
    ) {
        for (n in numbers) {
            for (digitSet in digitSets) {
                if (rules[n]!!(digitSet)) {
                    encodings[n] = digitSet
                    digitSets.remove(digitSet)
                    break
                }
            }
        }

        encodings[lastNumber] = digitSets.first()
    }

    fun fillLength6(digitSets: MutableSet<Set<Char>>, encodings: MutableMap<Int, Set<Char>>) {
        // assumes that the received set only contains representations with length 6

        val rules = mapOf<Int, (s: Set<Char>) -> Boolean>(
            // identifying 6 => only length 6 representation that when intersected with 1 results in only 1 letter
            Pair(6) { digitSet -> digitSet.intersect(encodings[1]!!).size == 1 },
            // identifying 0 => only length 6 representation, after removing 6, that when united with 4 results in 7 letters
            Pair(0) { digitSet -> digitSet.union(encodings[4]!!).size == 7 }
        )

        fill(digitSets, encodings, rules, arrayOf(6, 0), 9)
    }


    fun fillLength5(digitSets: MutableSet<Set<Char>>, encodings: MutableMap<Int, Set<Char>>) {
        // assumes that the received set only contains representations with length 5

        val rules = mapOf<Int, (s: Set<Char>) -> Boolean>(
            // identifying 5 => only length 5 representation that is contained by 6
            Pair(5) { digitSet -> encodings[6]!!.containsAll(digitSet) },
            // identifying 2 => only length 5 representation, after removing 5, that when united with 5 results in 7 letters
            Pair(2) { digitSet -> encodings[5]!!.union(digitSet).size == 7 }
        )

        fill(digitSets, encodings, rules, arrayOf(5, 2), 3)
    }

    fun solvePuzzle2(): Int {
        var result = 0

        for (i in signals.indices) {
            val digitSets = signals[i].map { s -> s.toSet() }
            val outputDigitSets = outputs[i].map { s -> s.toSet() }

            val encodings = mutableMapOf<Int, Set<Char>>()

            // fill the known
            for (digitSet in digitSets) {
                if (digitSet.size !in knownLengths) continue
                encodings[knownLengths[digitSet.size]!!] = digitSet
            }

            fillLength6(digitSets.filter { s -> s.size == 6 }.toMutableSet(), encodings)
            fillLength5(digitSets.filter { s -> s.size == 5 }.toMutableSet(), encodings)

            var count = 3
            for (digitSet in outputDigitSets) {
                for ((n, encoding) in encodings) {
                    if (encoding.size == digitSet.size && encoding.containsAll(digitSet)) {
                        result += n * 10.0.pow(count.toDouble()).toInt()
                        break
                    }
                }
                count--
            }
        }
        return result
    }

    Logger.logResults(solvePuzzle1(), solvePuzzle2())
}
