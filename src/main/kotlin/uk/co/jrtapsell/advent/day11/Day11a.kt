package uk.co.jrtapsell.advent.day11

import uk.co.jrtapsell.advent.SinglelineFilePart

/**
 * @author James Tapsell
 */
enum class Direction {
    N, NE, SE, S, SW, NW;

    val degrees = 60.0 * ordinal
    val radians = Math.toRadians(degrees)
    val y = Math.cos(radians)
    val x = Math.sin(radians)
}

object Day11a: SinglelineFilePart<Int>("day11") {
    override fun calculate(input: String): Int {
        val steps = input
            .split(",")
            .map { Direction.valueOf(it.toUpperCase()) }
        val values = steps
            .groupingBy { it }
            .eachCount()

        val (x, y) = calculateMoves(steps)


        return getShortestStepsTo(steps)
    }

}

fun getShortestStepsTo(steps: List<Direction>): Int {
    val items = mutableListOf<Direction>()
    while (distance(items, steps) > 0.1) {
        val next = Direction.values().map { item ->
            val new = items.toMutableList()
            new.add(item)
            item to distance(new, steps)
        }.minBy { it.second }!!.first
        items.add(next)
    }

    calculateMoves(items)

    return items.size
}

fun distance(items: List<Direction>, steps: List<Direction>): Double {
    val (ix, iy) = calculateMoves(items)
    val (sx, sy) = calculateMoves(steps)

    return Math.sqrt(Math.pow(ix - sx, 2.0) + Math.pow(iy - sy, 2.0))
}

fun calculateMoves(steps: List<Direction>) =
    steps.map { it.x }.sum() to steps.map { it.y }.sum()