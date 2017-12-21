package uk.co.jrtapsell.advent.day6

import uk.co.jrtapsell.advent.SingleLineFilePart

/**
 * @author James Tapsell
 */

object Day6b : SingleLineFilePart<Int>("day6") {

    override fun calculate(input: String): Int {
        val input = input.split("\t").map { it.toInt(10) }
        val counts = input.toMutableList()
        val seen = mutableListOf<String>()
        val seenSet = mutableSetOf<String>()
        var current = ""
        while (!seenSet.contains(counts.toString())) {
            current = counts.toString()
            seen.add(current)
            seenSet.add(current)
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