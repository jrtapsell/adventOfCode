package uk.co.jrtapsell.advent.day2

import uk.co.jrtapsell.advent.MultilineFilePart

/**
 * @author James Tapsell
 */

object Day2a: MultilineFilePart<Int>("day2") {
    override fun calculate(input: List<String>): Int {
        return input
            .map {
                it.split("\t")
                    .map { it.toInt(10) }
            }.map { it.max()!! - it.min()!! }
            .sum()
    }
}
