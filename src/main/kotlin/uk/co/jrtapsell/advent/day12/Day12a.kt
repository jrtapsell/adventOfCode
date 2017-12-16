package uk.co.jrtapsell.advent.day12

import uk.co.jrtapsell.advent.MultilineFilePart

/**
 * @author James Tapsell
 */
fun getGraph(input: List<String>): MutableMap<Int, List<Int>> {
    return input.associate {
        val (name, connections) = it.split("<->").map { it.trim().replace(" ", "") }
        val id = name.toInt()
        val neighbours = connections.split(",").map { it.toInt() }
        id to neighbours
    }.toMutableMap()
}
object Day12a: MultilineFilePart<Int>("day12") {
    override fun calculate(input: List<String>): Int {
        val graph = getGraph(input)

        val visited = mutableListOf<Int>()
        val toVisit = mutableListOf<Int>()

        toVisit.add(0)
        while (!toVisit.isEmpty()) {
            val current = toVisit.removeAt(0)
            visited.add(current)
            val neighbours = graph[current]!!
            neighbours
                .filter { it !in visited && it !in toVisit }
                .forEach { toVisit.add(it) }
        }
        return visited.size
    }
}