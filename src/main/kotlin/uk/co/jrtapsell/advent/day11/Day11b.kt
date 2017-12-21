package uk.co.jrtapsell.advent.day11

import uk.co.jrtapsell.advent.SingleLineFilePart

/**
 * @author James Tapsell
 */

object Day11b: SingleLineFilePart<Int>("day11") {

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

        var sumDq = 0
        var sumDr = 0
        val index = steps.indices
            .map {
                sumDq += steps[it].dQ
                sumDr += steps[it].dR
                sumDq to sumDr
            }
            .map { (q,r) ->
                Cube(q,r, -q-r)
            }
            .mapIndexed { i, q -> i to q.distance() }
            .maxBy { it.second }!!
        return index.second
        //return getShortestStepsTo(steps.subList(0, index.first).map {
        //    OtherDirection.valueOf(it.name)
        //})
    }
}