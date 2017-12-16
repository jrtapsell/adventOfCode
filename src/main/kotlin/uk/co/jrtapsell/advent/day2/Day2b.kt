package uk.co.jrtapsell.advent.day2

import uk.co.jrtapsell.advent.MultilineFilePart

/**
 * @author James Tapsell
 */

object Day2b: MultilineFilePart<Int>("day2") {
    override fun calculate(input: List<String>): Int {
        return input.map {
            val numbers = it.split("\t")
                .map { it.toInt(10) }
            val pair = numbers.flatMap{ numbers.map { a -> it to a } }
                .first { (a,b) -> a > b && a % b == 0 }
            pair.first / pair.second
        }.sum()
    }
}
