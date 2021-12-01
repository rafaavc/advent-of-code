import utils.FileManager

fun main() {
    val fileManager = FileManager(1)
    val lines = fileManager.readPuzzle1()

    val numbers = mutableListOf(0)
    numbers.addAll(lines.map { line -> line.toInt() })

    val firstNumbersIdx = 1 // because we added the 0 for accumulation

    fun solvePuzzle1() {
        val count = numbers.reduceIndexed { idx, acc, el ->
            if (idx > firstNumbersIdx && el > numbers[idx - 1])
                acc + 1
            else acc
        }
        println(count)
    }

    fun solvePuzzle2() {
        val count = numbers.reduceIndexed { idx, acc, el ->
            if (idx > firstNumbersIdx + 2 && el > numbers[idx - 3])
                acc + 1
            else acc
        }
        println(count)
    }

    solvePuzzle1()
    solvePuzzle2()
}
