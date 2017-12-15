package uk.co.jrtapsell.advent.day14

import uk.co.jrtapsell.advent.SinglelineFileDay
import uk.co.jrtapsell.advent.day10.Day10b

/**
 * @author James Tapsell
 */
object Day14a: SinglelineFileDay<Int>("day14") {
    override fun calculate(input: String): Int {
        return (0..127).map {
            Day10b.getHashInts(input + "-" + it.toString())
        }.map { it.map {
            it.toString(2).padStart(8, '0').count { it == '1' }
        }.sum() }.sum()
    }
}