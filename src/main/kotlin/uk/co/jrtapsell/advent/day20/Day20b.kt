package uk.co.jrtapsell.advent.day20

import uk.co.jrtapsell.advent.MultilineFilePart

object Day20b: MultilineFilePart<Int>("day20") {
    override fun calculate(input: List<String>): Int {

        i = 1000
        var current = getParticles(input)

        while (current.canCollide()) {
            current = current.map { it.step() }
            current = current.groupBy { it.position }
                .filter { it -> it.value.size == 1 }
                .flatMap { it.value }
        }
        return current.size

    }
}

var i = 10000
private fun <E> List<E>.canCollide(): Boolean {
    return (i-- > 0)
}
