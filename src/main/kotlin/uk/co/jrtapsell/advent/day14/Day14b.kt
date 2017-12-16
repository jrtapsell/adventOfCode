package uk.co.jrtapsell.advent.day14

import uk.co.jrtapsell.advent.SingleLineFilePart
import uk.co.jrtapsell.advent.day10.Day10b

/**
 * @author James Tapsell
 */
object Day14b: SingleLineFilePart<Int>("day14") {
    override fun calculate(input: String): Int {
        val data = (0..127).map {
            Day10b.getHashInts(input + "-" + it.toString())
        }.map { it.joinToString("") {
            it.toString(2).padStart(8, '0')
        }.toCharArray() }.toTypedArray()

        var index = firstOne(data)
        var count = 0
        while (index != null) {
            count++
            changeAndNeighbours(data, index.first, index.second)
            index = firstOne(data)
        }
        return count
    }

    private fun changeAndNeighbours(data: Array<CharArray>, first: Int, second: Int) {
        if (first in data.indices && second in data[first].indices && data[first][second] == '1') {
            data[first][second] = '0'
            for ((x, y) in listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)) {
                changeAndNeighbours(data, first + x, second + y)
            }
        }
    }

    private fun firstOne(data: Array<CharArray>): Pair<Int, Int>? {
        for (row in data.indices) {
            for (column in data[row].indices) {
                if (data[row][column] == '1') return row to column
            }
        }
        return null
    }
}