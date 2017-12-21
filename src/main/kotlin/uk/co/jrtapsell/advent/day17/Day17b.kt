package uk.co.jrtapsell.advent.day17

import uk.co.jrtapsell.advent.SingleLineFilePart

/**
 * @author James Tapsell
 */
object Day17b: SingleLineFilePart<Int>("day17") {
    override fun calculate(input: String): Int {
        val offset = input.toInt(10)
        var place = 0
        return (1..50000000).last {
            val x = place == 0
            place += 1
            place += offset
            place %= it + 1
            x
        }
    }
}

private fun <E> MutableList<E>.printHighlighted(place: Int) {
    println(mapIndexed { index, it ->
        var text = it.toString()
        if (index == place) {
            text = "<$text>"
        }
        text.padStart(4).padEnd(5)
    })
}