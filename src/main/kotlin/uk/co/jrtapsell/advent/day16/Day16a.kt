package uk.co.jrtapsell.advent.day16

import uk.co.jrtapsell.advent.SingleLineFilePart

/**
 * @author James Tapsell
 */
typealias State=MutableList<Char>
enum class Moves(private val run: (State, String)->State) {
    SWAP_BY_INDEX({ items, command ->
        val (first, second) = command.split("/").map { it.toInt(10) }
        val temp = items[first]
        items[first] = items[second]
        items[second] = temp
        items
    }),
    ROTATE({ items, command ->
        val amount = command.toInt(10)
        val pivot = items.size - amount
        val temp = items.subList(pivot, items.size) + items.subList(0, pivot)
        temp.toMutableList()
    }),
    SWAP_BY_VALUE({ items, command ->
        val (first, second) = command.split("/").map { it[0] }
        val firstIndex = items.indexOf(first)
        val secondIndex = items.indexOf(second)
        items[firstIndex] = second
        items[secondIndex] = first
        items
    });
    
    operator fun invoke(list: State, command: String): State {
        return run(list, command)
    }

    companion object {
        fun run(list: State, input: String): State {
            val letter = input[0]
            val command = input.substring(1)
            return when (letter) {
                'x' -> SWAP_BY_INDEX(list, command)
                's' -> ROTATE(list, command)
                'p' -> SWAP_BY_VALUE(list, command)
                else -> throw AssertionError("Bad input: $input")
            }
        }   
    }
    
}

object Day16a: SingleLineFilePart<String>("day16") {
    override fun calculate(input: String): String {
        var items = ('a'..'p').toMutableList()
        input.split(",").forEach {
            items = Moves.run(items, it)
        }
        return items.joinToString("")
    }

    private fun State.printTagged(it: String) {
        val movePadded = it.padStart(30, ' ')
        val data = joinToString("") { it.toString().padStart(3, ' ') }
        println("$movePadded $data")
    }

}