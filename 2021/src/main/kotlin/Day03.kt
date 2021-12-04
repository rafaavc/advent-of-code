import utils.FileManager
import utils.Logger
import kotlin.math.abs
import kotlin.math.pow

enum class CommonBitType {
    MOST,
    LEAST
}

fun main() {
    val lines = FileManager.read(3)

    fun get1CountAtI(lines: List<String>, i: Int): Int
        = lines.fold(0) { acc, line ->
            if (line[i] == '1') acc + 1
            else acc
        }

    fun getMostLeastCommonBitAtI(lines: List<String>, i: Int, type: CommonBitType): Char {
        val oneCount = get1CountAtI(lines, i)
        val oneCountGreater = oneCount >= lines.size - oneCount
        return when (type) {
            CommonBitType.MOST -> if (oneCountGreater) '1' else '0'
            CommonBitType.LEAST -> if (oneCountGreater) '0' else '1'
        }
    }

    fun binaryToInt(binary: String): Int {
        var result = 0
        for (idx in binary.length - 1 downTo 0) {
            if (binary[idx] == '0') continue
            val value = 2.0.pow(abs(idx.toDouble() - binary.length + 1)).toInt()
            result += value
        }

        return result
    }

    fun solvePuzzle1(): Int {
        var gamma = 0
        var epsilon = 0
        val lineLength = lines[0].length - 1

        for (idx in lineLength downTo 0) {
            val value = 2.0.pow(abs(idx.toDouble() - lineLength)).toInt()

            if (getMostLeastCommonBitAtI(lines, idx, CommonBitType.MOST) == '1') gamma += value
            else epsilon += value
        }

        return gamma * epsilon
    }

    fun solvePuzzle2(): Int {
        var oxygenLines = lines
        var scrubberLines = lines
        var count = 0

        while (oxygenLines.size != 1 && scrubberLines.size != 1) {
            val mostCommon = getMostLeastCommonBitAtI(oxygenLines, count, CommonBitType.MOST)
            val leastCommon = getMostLeastCommonBitAtI(scrubberLines, count, CommonBitType.LEAST)
            oxygenLines = oxygenLines.filter { it[count] == mostCommon }
            scrubberLines = scrubberLines.filter { it[count] == leastCommon }
            count++
        }

        return binaryToInt(scrubberLines[0]) * binaryToInt(oxygenLines[0])
    }

    Logger.logResults(solvePuzzle1(), solvePuzzle2())
}
