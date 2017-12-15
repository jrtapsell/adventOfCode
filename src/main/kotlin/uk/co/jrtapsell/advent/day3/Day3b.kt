package uk.co.jrtapsell.advent.day3

import uk.co.jrtapsell.advent.Day
import uk.co.jrtapsell.advent.SinglelineFileDay

/**
 * @author James Tapsell
 */

object Day3b: SinglelineFileDay<Int>("day3") {

    override fun calculate(input: String): Int {
        return makeMatrix(1001, input.toInt(10))
    }


    private fun makeMatrix(size: Int, input: Int): Int {
        if (size % 2 != 1) throw AssertionError()
        val mat = MutableList(
                size,
                { MutableList<Int?>(size, { null }) }
        )
        val start = (size / 2)
        var pointer = start to start
        mat[pointer] = 1
        while (true) {
            for ((go, look) in directions) {
                do {
                    pointer = go(pointer)
                    val sum = sumNeighbours(mat, pointer)
                    if (sum > input) return sum
                    mat[pointer] = sum
                } while (mat.hasPoint(go(pointer)) && mat[look(pointer)] != null)
            }
        }
    }

    private fun sumNeighbours(mat: MutableList<MutableList<Int?>>, pointer: Pair<Int, Int>): Int {
        return (-1..1).flatMap {a -> (-1..1).map { a to it }}
            .filter { (a,b) -> a != 0 || b != 0 }
            .mapNotNull { (a,b) -> mat[pointer.first + a to pointer.second + b] }
            .sum()
    }
}