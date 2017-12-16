package uk.co.jrtapsell.advent.day16

import uk.co.jrtapsell.advent.SingleLineFilePart

/**
 * @author James Tapsell
 */
object Day16b: SingleLineFilePart<String>("day16") {
    override fun calculate(input: String): String {
        var items = ('a'..'p').toMutableList()
        val split = input.split(",")
        val seen = mutableListOf<String>()
        var string = items.joinToString()
        while (!seen.contains(string)) {
            seen.add(asString(items))
            for (it in split) {
                val move = it[0]
                val command = it.substring(1)
                when (move) {
                    'x' -> {
                        val (first, second) = command.split("/").map { it.toInt(10) }
                        val temp = items[first]
                        items[first] = items[second]
                        items[second] = temp
                        "swapI($first, $second)"
                    }
                    's' -> {
                        val amount = command.toInt(10)
                        val pivot = items.size - amount
                        val temp = items.subList(pivot, items.size) + items.subList(0, pivot)
                        items = temp.toMutableList()
                        "rotate($amount)"
                    }
                    'p' -> {
                        val (first, second) = command.split("/").map { it[0] }
                        val firstIndex = items.indexOf(first)
                        val secondIndex = items.indexOf(second)
                        items[firstIndex] = second
                        items[secondIndex] = first
                        "swapV($first, $second)"
                    }
                    else -> throw AssertionError("Unknown command: `$it`")
                }
            }
            string = asString(items)
        }
        val index = 1000000000L % seen.size
        return seen[index.toInt()]
    }

    private fun asString(items: MutableList<Char>) = items.joinToString("")

    private fun MutableList<Char>.printTagged(it: String) {
        val movePadded = it.padStart(30, ' ')
        val data = joinToString("") { it.toString().padStart(3, ' ') }
        println("$movePadded $data")
    }

}