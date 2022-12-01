package utils

import java.io.BufferedReader
import java.io.InputStreamReader

class FileManager {
    companion object {
        fun read(day: Int): List<String> {
            val dayIdentifier = if (day < 10) "0$day" else "$day"
            val fileName = "Day$dayIdentifier-1.txt"

            val resource = FileManager::class.java.getResourceAsStream("/$fileName")
                ?: error("Couldn't find resource file '$fileName'")

            val reader = BufferedReader(InputStreamReader(resource))
            return reader.readLines()
        }
    }
}
