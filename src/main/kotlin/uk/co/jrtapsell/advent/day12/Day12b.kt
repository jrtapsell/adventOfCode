package uk.co.jrtapsell.advent.day12

import uk.co.jrtapsell.advent.MultilineFilePart

/**
 * @author James Tapsell
 */
object Day12b : MultilineFilePart<Int>("day12") {
    override fun calculate(input: List<String>): Int {
        val graph = getGraph(input)

        val visited = mutableListOf<Int>()
        val toVisit = mutableListOf<Int>()

        var groups = 0
        while (graph.isNotEmpty()) {
            groups++
            visited.clear()
            toVisit.clear()
            val start = graph.entries.toList()[0].key
            toVisit.add(start)
            while (!toVisit.isEmpty()) {
                val current = toVisit.removeAt(0)
                visited.add(current)
                val neighbours = graph[current]!!

                for (i in neighbours) {
                    if (i !in visited && i !in toVisit) {
                        toVisit.add(i)
                    }
                }
            }
            for (i in visited) {
                graph.remove(i)
            }
        }
        return groups
    }
}