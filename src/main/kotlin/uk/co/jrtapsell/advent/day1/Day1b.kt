package uk.co.jrtapsell.advent.day1

import uk.co.jrtapsell.advent.SingleLineFilePart

object Day1b : SingleLineFilePart<Int>("day1") {

    override fun calculate(input: String): Int {
        return input.filterIndexed { index, c ->
            val opposite = index + (input.length / 2)
            c == input[opposite % input.length]
        }.map { it.toString().toInt(10) }.sum()

    }
}