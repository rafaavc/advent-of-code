package utils

class Logger {
    companion object {
        private fun logResult(puzzle: Int, result: Int) {
            println("Puzzle $puzzle: $result")
        }

        fun logResults(puzzle1: Int, puzzle2: Int) {
            logResult(1, puzzle1)
            logResult(2, puzzle2)
        }
    }
}