package uk.co.jrtapsell.advent.day3

import uk.co.jrtapsell.advent.SingleLineFilePart

/**
 * @author James Tapsell
 */

object Day3a : SingleLineFilePart<Int>("day3") {
    override fun calculate(input: String): Int {
        val inputValue = input.toInt(10)
        val mat = makeMatrix(1001)
        val row = mat.mapIndexed { i, v -> i to v }
            .first { (_, v) -> v.contains(inputValue) }
            .first
        val column = mat[row].indexOf(inputValue)
        return Math.abs(500 - row) + Math.abs(500 - column)
    }
}

fun MutableList<MutableList<Int?>>.println() {
    for (i in this) {
        println(i)
    }
}

val directions = listOf(
        Pair<Int, Int>::right to Pair<Int, Int>::up,
        Pair<Int, Int>::up to Pair<Int, Int>::left,
        Pair<Int, Int>::left to Pair<Int, Int>::down,
        Pair<Int, Int>::down to Pair<Int, Int>::right
)

fun makeMatrix(size: Int): MutableList<MutableList<Int?>> {
    if (size % 2 != 1) throw AssertionError()
    val mat = MutableList(
            size,
            { a -> MutableList<Int?>(size, { null }) }
    )
    val start = (size / 2)
    var pointer = start to start
    var count = 1
    mat[pointer] = count++
    while (true) {
        for ((go, look) in directions) {
            do {
                pointer = go(pointer)
                if (mat[pointer] != null) {
                    return mat
                }
                mat[pointer] = count++
            } while (mat.hasPoint(go(pointer)) && mat[look(pointer)] != null)
        }
    }
}

fun <E> MutableList<MutableList<E>>.hasPoint(pointer: Pair<Int, Int>): Boolean {
    return pointer.first in this.indices && pointer.second in this[pointer.first].indices
}

fun Pair<Int, Int>.up() = first - 1 to second
fun Pair<Int, Int>.down() = first + 1 to second
fun Pair<Int, Int>.left() = first to second - 1
fun Pair<Int, Int>.right() = first to second + 1

operator fun <E> MutableList<MutableList<E>>.set(pointer: Pair<Int, Int>, value: E) {
    this[pointer.first][pointer.second] = value
}

operator fun <E> MutableList<MutableList<E>>.get(pointer: Pair<Int, Int>): E {
    return this[pointer.first][pointer.second]
}

