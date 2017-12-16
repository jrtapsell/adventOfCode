package uk.co.jrtapsell.advent.day8

import uk.co.jrtapsell.advent.MultilineFilePart

/**
 * @author James Tapsell
 */

object Day8b : MultilineFilePart<Int>("day8") {
    override fun calculate(input: List<String>): Int {
        val values = getInstructions(input)
        var maxEver = Long.MIN_VALUE
        val memory = mutableMapOf<String, Long>()
        for (i in values) {
            val inputValue = memory.getOrDefault(i.sourceReg, 0L)
            if (i.operator.method(inputValue, i.amount)) {
                val startValue = memory.getOrDefault(i.outReg, 0L)
                val endValue = startValue + i.change
                maxEver = maxOf(maxEver, endValue)
                memory[i.outReg] = endValue
            }
        }
        return maxEver.toInt()
    }
}