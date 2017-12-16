package uk.co.jrtapsell.advent.day7

import uk.co.jrtapsell.advent.MultilineFilePart

/**
 * @author James Tapsell
 */

object Day7a: MultilineFilePart<String>("day7") {
    override fun calculate(input: List<String>): String {
        val lineRegex = Regex("""([a-z]+) \([0-9]+\)( -> ([a-z ,]+))?""")
        val values = input
            .associate {
                val values = lineRegex.matchEntire(it)!!.groupValues
                values[1] to values[3].split(", ").filter { it.trim() != "" }.toMutableList()
            }.toMutableMap()

        while (values.size > 1) {
            values.filter { it.value.isEmpty() }.forEach { (removalName, _) ->
                values.forEach { t, u -> u.remove(removalName) }
                values.remove(removalName)
            }
        }
        return values.entries.toList()[0].key
    }
}