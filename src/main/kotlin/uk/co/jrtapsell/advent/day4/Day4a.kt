package uk.co.jrtapsell.advent.day4

import uk.co.jrtapsell.advent.MultilineFilePart

/**
 * @author James Tapsell
 */

object Day4a: MultilineFilePart<Int>("day4") {
    override fun calculate(input: List<String>): Int {
        return input.map { wordCounts(it) }
            .count { it.none { it.value > 1 } }
    }

    private fun wordCounts(it: String): Map<String, Int> {
        return it.split(" ")
            .filter { it.trim() != "" }
            .groupingBy { it }
            .eachCount()
    }
}