package utils

class Logger {
    companion object {
        private fun logResult(puzzle: Int, result: Any) {
            println("Puzzle $puzzle: $result")
        }

        fun logResults(puzzle1: Any, puzzle2: Any) {
            logResult(1, puzzle1)
            logResult(2, puzzle2)
        }
    }
}