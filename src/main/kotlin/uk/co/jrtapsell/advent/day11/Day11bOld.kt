package uk.co.jrtapsell.advent.day11

import uk.co.jrtapsell.advent.MultilineFileDay
import uk.co.jrtapsell.advent.SinglelineFileDay
import java.io.File

/**
 * @author James Tapsell
 */

object Day11bOLD: SinglelineFileDay<Int>("day11") {
    override fun calculate(input: String): Int {
        val steps = input
            .split(",")
            .map { Direction.valueOf(it.toUpperCase()) }

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