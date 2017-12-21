package uk.co.jrtapsell.advent.day11

import uk.co.jrtapsell.advent.SingleLineFilePart

/**
 * @author James Tapsell
 */

object Day11a: SingleLineFilePart<Int>("day11") {
    override fun calculate(input: String): Int {
        val steps = input
            .split(",")
            .map { Day11b.Direction.valueOf(it.toUpperCase()) }

        val dr = steps.sumBy { it.dR }
        val dq = steps.sumBy { it.dQ }
        val cube = Day11b.Cube(dq, dr, -dq-dr)
        return cube.distance()
    }

}