package uk.co.jrtapsell.advent.day15

import uk.co.jrtapsell.advent.MultilineFilePart

/**
 * @author James Tapsell
 */
object Day15b : MultilineFilePart<Int>("day15") {
    override fun calculate(input: List<String>): Int {
        val aStart = input[0].split(" ").last().toLong(10)
        val bStart = input[1].split(" ").last().toLong(10)

        val aSeq = makeSequence(aStart, 16807, 4)
        val bSeq = makeSequence(bStart, 48271, 8)

        val aItt = aSeq.iterator()
        val bItt = bSeq.iterator()

        return (0 until 5e6.toLong()).count {
            val a = aItt.next() % 65536L
            val b = bItt.next() % 65536L
            a == b
        }
    }

    private fun makeSequence(aStart: Long, factor: Long, mod: Long): Sequence<Long> {
        var current = aStart
        return generateSequence {
            do {
                current *= factor
                current %= 2147483647
            } while (current % mod != 0L)
            current
        }
    }

}