package uk.co.jrtapsell.advent.day7

import uk.co.jrtapsell.advent.MultilineFilePart

/**
 * @author James Tapsell
 */

data class Node(val name: String, val weight: Int, val backingMap: Map<String, Node>) {
    var children = mutableListOf<String>()

    fun nodeChildren() = children.map { backingMap[it]!! }
    fun calcWeight(): Int {
        return weight + children.map { backingMap[it]!!.calcWeight() }.sum()
    }

    override fun toString(): String {
        return "$name | $weight > $children"
    }
}

object Day7b : MultilineFilePart<Int>("day7") {
    override fun calculate(input: List<String>): Int {
        val lineRegex = Regex("""([a-z]+) \(([0-9]+)\)(?: -> ([a-z ,]+))?""")
        val backingMap = mutableMapOf<String, Node>()
        input
            .map {
                val values = lineRegex.matchEntire(it)!!.groupValues
                val name = values[1]
                val weight = values[2].toInt()
                val children = values[3].split(", ").filter { it.trim() != "" }.toMutableList()
                val node = Node(name, weight, backingMap)
                node.children = children
                node
            }.forEach { backingMap[it.name] = it }



        val values = backingMap.map { (name, node) ->
            node.nodeChildren()
                .groupBy { it.calcWeight() }.toList().sortedBy { it.first } to node
        }.filter { (it, node) ->
            it.size > 1
        }.map { (it, node) ->
            val good = it[0].second[0].calcWeight()
            val bad = it[1].second[0]

            bad.weight - (bad.calcWeight() - good) to node
        }

        val names = values.map { it.second.name }
        return values.first {
            val children = it.second.children
            children.none { names.contains(it) }
        }.first

    }
}