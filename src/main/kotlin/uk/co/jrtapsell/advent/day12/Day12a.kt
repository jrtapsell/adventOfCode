package uk.co.jrtapsell.advent.day12

import uk.co.jrtapsell.advent.MultilineFilePart

/**
 * @author James Tapsell
 */
object Day12a: MultilineFilePart<Int>("day12") {
    override fun calculate(input: List<String>): Int {
        val graph = input.associate {
            val (name, connections) = it.split("<->").map { it.trim().replace(" ", "") }
            val id = name.toInt()
            val neighbours = connections.split(",").map { it.toInt() }
            id to neighbours
        }

        val visited = mutableListOf<Int>()
        val toVisit = mutableListOf<Int>()

        toVisit.add(0)
        while (!toVisit.isEmpty()) {
            val current = toVisit.removeAt(0)
            visited.add(current)
            val neightbours = graph[current]!!

            for (i in neightbours) {
                if (i !in visited && i !in toVisit) {
                    toVisit.add(i)
                }
            }
        }
        return visited.size
    }
}