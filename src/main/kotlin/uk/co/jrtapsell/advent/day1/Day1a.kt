package uk.co.jrtapsell.advent.day1

import uk.co.jrtapsell.advent.SinglelineFilePart

object Day1a: SinglelineFilePart<Int>("day1") {

    override fun calculate(input: String): Int {
        val firstDifferent = input.mapIndexed { index, it -> index to it }
            .firstOrNull { (_, letter) -> letter != input[0] }
            ?.first ?: 0
        val looped = input.substring(firstDifferent) + input.substring(0, firstDifferent)

        val filtered = looped.filterIndexed { index, c ->
            index < (looped.length - 1) && c == looped[index + 1]
        }

        val numbers = filtered.map { it.toString().toInt(10) }
        return numbers.sum()
    }
}