package utils

import java.io.BufferedReader
import java.io.InputStreamReader

class FileManager(private val day: Int) {
    private fun read(puzzle: Int): List<String> {
        val dayIdentifier = if (day < 10) "0$day" else "$day"
        val fileName = "Day$dayIdentifier-$puzzle.txt"

        val resource = FileManager::class.java.getResourceAsStream("/$fileName")
            ?: error("Couldn't find resource file '$fileName'")

        val reader = BufferedReader(InputStreamReader(resource))
        return reader.readLines()
    }

    fun readPuzzle1(): List<String> = read(1)
    fun readPuzzle2(): List<String> = read(2)
}
