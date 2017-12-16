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
                items = Moves.run(items, it)
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