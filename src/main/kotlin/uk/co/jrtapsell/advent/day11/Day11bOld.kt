package uk.co.jrtapsell.advent.day11

import uk.co.jrtapsell.advent.SingleLineFilePart

/**
 * @author James Tapsell
 */

object Day11bOLD: SingleLineFilePart<Int>("day11") {
    override fun calculate(input: String): Int {
        val steps = input
            .split(",")
            .map { DirectionOld.valueOf(it.toUpperCase()) }

        var max = Int.MIN_VALUE

        for (i in steps.indices) {
            val workingSet = steps.subList(0, i)
            val distance = getShortestStepsTo(workingSet)
            if (distance > max) {
                max = distance
                println("$i ${steps.size}")
            }
        }
        return max
    }
}