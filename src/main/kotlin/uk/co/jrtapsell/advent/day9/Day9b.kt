package uk.co.jrtapsell.advent.day9

import uk.co.jrtapsell.advent.MultilineFilePart

/**
 * @author James Tapsell
 */

object Day9b: MultilineFilePart<Int>("day9") {
    override fun calculate(input: List<String>): Int {
        var reduced = input[0]
        while (reduced.contains("!!")) {
            reduced = reduced.replace("!!", "")
        }
        reduced = reduced.replace(Regex("!."), "")
        val garbage = Regex("<[^>]+>")
        return garbage.findAll(reduced)
            .map { it.value.length - 2 }
            .sum()
    }

}