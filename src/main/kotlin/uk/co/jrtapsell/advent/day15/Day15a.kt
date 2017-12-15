package uk.co.jrtapsell.advent.day15

import uk.co.jrtapsell.advent.MultilineFileDay

/**
 * @author James Tapsell
 */
object Day15a: MultilineFileDay<Int>("day15") {
    override fun calculate(input: List<String>): Int {
        val aStart = input[0].split(" ").last().toLong(10)
        val bStart = input[1].split(" ").last().toLong(10)

        val aSeq = makeSequence(aStart, 16807).iterator()
        val bSeq = makeSequence(bStart, 48271).iterator()

        var count = 0
        for (i in (0 until 4e7.toLong())) {
            val a = aSeq.next() % 65536L
            val b = bSeq.next() % 65536L
            if (a == b) {
                count++
            }
        }
        return count
    }

    private fun makeSequence(aStart: Long, factor: Long): Sequence<Long> {
        var current = aStart
        return generateSequence {
            current *= factor
            current %= 2147483647
            current
        }
    }

}