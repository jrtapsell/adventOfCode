package uk.co.jrtapsell.advent.day16

import uk.co.jrtapsell.advent.SinglelineFilePart

/**
 * @author James Tapsell
 */
object Day16a: SinglelineFilePart<String>("day16") {
    override fun calculate(input: String): String {
        var items = ('a'..'p').toMutableList()
        input.split(",").forEach {
            val move = it[0]
            val command = it.substring(1)
            val tag = when (move) {
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
        return items.joinToString("")
    }

    private fun MutableList<Char>.printTagged(it: String) {
        val movePadded = it.padStart(30, ' ')
        val data = joinToString("") { it.toString().padStart(3, ' ') }
        println("$movePadded $data")
    }

}