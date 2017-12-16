package uk.co.jrtapsell.advent.day9

import uk.co.jrtapsell.advent.MultilineFilePart

/**
 * @author James Tapsell
 */
class Node(val parent: Node?) {
    val children = mutableListOf<Node>()

    fun score(outer: Int): Int {
        return outer + (children.map { it.score(outer + 1) }.sum())
    }
}

object Day9a: MultilineFilePart<Int>("day9") {
    override fun calculate(input: List<String>): Int {
        var reduced = input[0]
        while (reduced.contains("!!")) {
            reduced = reduced.replace("!!", "")
        }
        reduced = reduced.replace(Regex("!."), "")
        reduced = reduced.replace(Regex("<([^>]+)?>"), "")
        reduced = reduced.replace(",", "")
        reduced = reduced.substring(1, reduced.length - 1)

        var current = Node(null)

        for (c in reduced) {
            when (c) {
                '{' -> {
                    val upper = current
                    current = Node(upper)
                    upper.children.add(current)
                }
                '}' -> current = current.parent!!
            }
        }
        return current.score(1)
    }

}