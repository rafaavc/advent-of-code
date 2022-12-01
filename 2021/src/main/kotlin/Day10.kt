import utils.FileManager
import utils.Logger
import kotlin.collections.ArrayDeque

fun main() {
    val lines = FileManager.read(10)

    val correspondingClose = mapOf(
        Pair('{', '}'),
        Pair('[', ']'),
        Pair('(', ')'),
        Pair('<', '>')
    )

    val syntaxCheckScores = mapOf(
        Pair(')', 3),
        Pair(']', 57),
        Pair('}', 1197),
        Pair('>', 25137)
    )

    val autocompleteScores = mapOf(
        Pair(')', 1),
        Pair(']', 2),
        Pair('}', 3),
        Pair('>', 4)
    )

    fun solve(): Pair<Int, Long> {
        val autoCompleteCheckScores = mutableListOf<Long>()
        var syntaxCheckScore = 0

        for (line in lines) {
            val contexts = ArrayDeque<Char>() // stack-like
            var currentSyntaxCheckScore = 0

            for (c in line) {
                if (correspondingClose.containsKey(c))
                    contexts.addFirst(correspondingClose[c]!!)
                else {
                    if (c != contexts.first()) {
                        currentSyntaxCheckScore += syntaxCheckScores[c]!!
                        break
                    }

                    contexts.removeFirst()
                }
            }

            syntaxCheckScore += currentSyntaxCheckScore
            if (currentSyntaxCheckScore != 0) continue

            val currentAutocompleteScore = contexts.fold(0L)
                { acc, c -> (acc * 5) + autocompleteScores[c]!! }

            autoCompleteCheckScores.add(currentAutocompleteScore)
        }

        autoCompleteCheckScores.sort()

        return Pair(syntaxCheckScore, autoCompleteCheckScores[autoCompleteCheckScores.size / 2])
    }

    fun solvePuzzle1(): Int = solve().first

    fun solvePuzzle2(): Long = solve().second

    Logger.logResults(solvePuzzle1(), solvePuzzle2())
}
