package uk.co.jrtapsell.advent.day11

import uk.co.jrtapsell.advent.SinglelineFilePart

/**
 * @author James Tapsell
 */

typealias OtherDirection = uk.co.jrtapsell.advent.day11.Direction
object Day11b: SinglelineFilePart<Int>("day11") {

    enum class Direction(val dQ: Int, val dR: Int) {
        N(0,-1),
        NE(1,-1),
        SE(1,0),
        S(0,1),
        SW(-1,1),
        NW(-1,0);
    }

    data class Cube(val x: Int, val y: Int, val z: Int) {
        fun distance(): Int {
            return (Math.abs(x) + Math.abs(y) + Math.abs(z)) / 2
        }
    }

    override fun calculate(input: String): Int {
        val steps = input
            .split(",")
            .map { Direction.valueOf(it.toUpperCase()) }

        val index = steps.indices.map { steps.subList(0, it) }
            .map { it.sumBy { it.dQ } to it.sumBy { it.dR } }
            .map { (q,r) ->
                Cube(q,r, -q-r)
            }
            .mapIndexed { i, q -> i to q.distance() }
            .maxBy { it.second }!!.first
        return getShortestStepsTo(steps.subList(0, index).map {
            OtherDirection.valueOf(it.name)
        })
    }
}