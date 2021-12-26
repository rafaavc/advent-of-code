import utils.FileManager
import utils.Logger
import kotlin.math.pow

data class ParsingResult(val versionSum: Int, val packageSize: Int, val result: Long)

fun main() {
    val lines = FileManager.read(16)
    var packet = ""

    for (i in lines[0]) {
        val bin = Integer.toBinaryString(Integer.parseInt(i.toString(), 16))
        packet += "0".repeat(4 - bin.length) + bin
    }

    fun binaryToNumber(binary: String): Long {
        var result = 0L
        var idx = binary.length - 1

        for (i in binary) {
            result +=  (i - '0') * (2.0.pow(idx.toDouble())).toLong()
            idx--
        }

        return result
    }

    fun parseLiteral(pck: String, version: Int): ParsingResult {
        var packageSize = -1
        var current = 0
        var number = ""

        while (packageSize == -1) {
            val idx = 6 + (current) * 5
            if (pck[6 + current * 5] == '0')
                packageSize = idx + 5
            number += pck.substring(idx + 1, idx + 5)
            current++
        }

        return ParsingResult(version, packageSize, binaryToNumber(number))
    }

    fun executeOp(id: Int, values: List<Long>): Long {
        return when (id) {
            0 -> values.fold(0L) { acc, v -> acc + v }
            1 -> values.fold(1L) { acc, v -> acc * v }
            2 -> values.minOrNull()!!
            3 -> values.maxOrNull()!!
            5 -> if (values[0] > values[1]) 1 else 0
            6 -> if (values[0] < values[1]) 1 else 0
            7 -> if (values[0] == values[1]) 1 else 0
            else -> error("id invalid")
        }
    }

    fun parsePacket(pck: String): ParsingResult {
        var versionSum = pck.substring(0, 3).toInt(2)
        val typeId = pck.substring(3, 6).toInt(2)

        if (typeId == 4) return parseLiteral(pck, versionSum)

        val lengthTypeId = pck[6]

        val offset = if (lengthTypeId == '0') 22 else 18
        val comparisonVal = pck.substring(7, offset).toInt(2)

        var totalLength = 0
        var totalSubPackets = 0

        val values = mutableListOf<Long>()

        while((lengthTypeId == '1' && totalSubPackets < comparisonVal)
                || (lengthTypeId == '0' && totalLength < comparisonVal)) {
            val result = parsePacket(pck.substring(offset + totalLength, pck.length))

            totalLength += result.packageSize
            versionSum += result.versionSum.toInt()
            totalSubPackets++

            values.add(result.result)
        }

        return ParsingResult(versionSum, totalLength + offset, executeOp(typeId, values))
    }

    fun solvePuzzle1(): Int = parsePacket(packet).versionSum

    fun solvePuzzle2(): Long = parsePacket(packet).result

    Logger.logResults(solvePuzzle1(), solvePuzzle2())
}
