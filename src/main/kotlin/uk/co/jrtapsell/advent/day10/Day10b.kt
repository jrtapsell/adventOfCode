package uk.co.jrtapsell.advent.day10

import uk.co.jrtapsell.advent.SingleLineFilePart

/**
 * @author James Tapsell
 */

object Day10b: SingleLineFilePart<String>("day10") {

    override fun calculate(input: String): String {
        val items = getHashInts(input)
        return items.joinToString("") {
            it.toString(16).padStart(2, '0')
        }

    }

    fun getHashInts(input: String): List<Int> {
        val numbers = input.map { it.toInt() }
        val working = numbers + listOf(17, 31, 73, 47, 23)
        var skip = 0
        var pointer = 0
        var list = (0 until stringLength).toMutableList()
        (0 until 64).forEach {
            working.forEach { length ->
                list = list.swap(pointer, length)
                pointer += length
                pointer += skip++
                pointer %= stringLength
            }
        }
        val items = MutableList(16, { 0 })
        list.forEachIndexed { index, value ->
            val chunk = index / 16
            items[chunk] = items[chunk] xor value
        }
        return items
    }
}