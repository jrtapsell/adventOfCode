package uk.co.jrtapsell.advent.day4

import uk.co.jrtapsell.advent.MultilineFileDay

/**
 * @author James Tapsell
 */

object Day4b: MultilineFileDay<Int>("day4") {
    override fun calculate(input: List<String>): Int {
        return input.map { wordCounts(it) }
            .count { it.none { it.value > 1 } }
    }
}

fun wordCounts(it: String): Map<String, Int> {
    return it.split(" ")
        .map { it.toCharArray().sorted().joinToString("") }
        .filter { it.trim() != "" }
        .groupingBy { it }
        .eachCount()
}