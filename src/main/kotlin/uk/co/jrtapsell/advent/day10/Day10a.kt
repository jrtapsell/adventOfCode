package uk.co.jrtapsell.advent.day10

import uk.co.jrtapsell.advent.SingleLineFilePart

/**
 * @author James Tapsell
 */
val stringLength = 256
//val input = "3,4,1,5"
//val stringLength = 5

object Day10a: SingleLineFilePart<Int>("day10") {

    override fun calculate(input: String): Int {
        val input = input.split(",").map { it.toInt() }
        var skip = 0
        var pointer = 0
        var list = (0 until stringLength).toMutableList()
        input.forEach { length ->
            list = list.swap(pointer, length)
            pointer += length
            pointer += skip++
            pointer %= stringLength
        }
        return list[0] * list[1]
    }

}

fun MutableList<Int>.swap(pointer: Int, length: Int): MutableList<Int> {
    for (offset in (0 until length / 2)) {
        val lowOffset = (pointer + offset) % stringLength
        val highOffset = (pointer + (length - offset - 1)) % stringLength
        val low = get(lowOffset)
        val high = get(highOffset)
        set(highOffset, low)
        set(lowOffset, high)
    }
    return this
}
