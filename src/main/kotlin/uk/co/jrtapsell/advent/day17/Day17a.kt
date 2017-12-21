package uk.co.jrtapsell.advent.day17

import uk.co.jrtapsell.advent.SingleLineFilePart

/**
 * @author James Tapsell
 */
object Day17a: SingleLineFilePart<Int>("day17") {
    override fun calculate(input: String): Int {
        val offset = input.toInt(10)
        val list = mutableListOf(0)
        var place = 0
        (1..2017).forEach {
            list.add(place + 1, it)
            place += 1
            place += offset
            place %= list.size
        }
        val stop = list.indexOf(2017)
        return list[stop + 1]
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
