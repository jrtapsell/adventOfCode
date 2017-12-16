package uk.co.jrtapsell.advent.day5

import uk.co.jrtapsell.advent.MultilineFilePart

/**
 * @author James Tapsell
 */

object Day5a: MultilineFilePart<Int>("day5") {
    override fun calculate(input: List<String>): Int {
        val values = input.map { it.toInt(10) }.toMutableList()
        var pointer = 0
        var steps = 0
        while (pointer in values.indices) {
            steps++
            val old = pointer
            pointer += values[pointer]
            values[old]++
        }
        return steps
    }
}