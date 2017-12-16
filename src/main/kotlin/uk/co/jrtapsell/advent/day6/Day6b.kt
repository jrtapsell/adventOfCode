package uk.co.jrtapsell.advent.day6

import uk.co.jrtapsell.advent.SinglelineFilePart

/**
 * @author James Tapsell
 */

object Day6b : SinglelineFilePart<Int>("day6") {

    override fun calculate(input: String): Int {
        val input = input.split("\t").map { it.toInt(10) }
        val counts = input.toMutableList()
        val seen = mutableListOf<String>()
        var current = ""
        while (!seen.contains(counts.toString())) {
            current = counts.toString()
            seen.add(current)
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
            current = counts.toString()
        }
        seen.add(current)
        return seen.lastIndexOf(current) - seen.indexOf(current)
    }
}