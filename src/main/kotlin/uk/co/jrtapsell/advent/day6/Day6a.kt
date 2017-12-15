package uk.co.jrtapsell.advent.day6

import uk.co.jrtapsell.advent.Day
import uk.co.jrtapsell.advent.SinglelineFileDay

/**
 * @author James Tapsell
 */

object Day6a: SinglelineFileDay<Int>("day6") {

    override fun calculate(input: String): Int {
        val input = input.split("\t").map { it.toInt(10) }
        val counts = input.toMutableList()
        val seen = mutableSetOf<String>()
        while (!seen.contains(counts.toString())) {
            seen.add(counts.toString())
            val max = counts.max()
            var index = counts.indexOf(max)
            var carry = counts[index]
            counts[index] = 0
            index++
            while (carry > 0) {
                index %= counts.size
                counts[index]++
                index++
                carry--
            }
        }
        return seen.size
    }
}