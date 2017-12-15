package uk.co.jrtapsell.advent.day8

import uk.co.jrtapsell.advent.MultilineFileDay

/**
 * @author James Tapsell
 */

object Day8a : MultilineFileDay<Int>("day8") {
    override fun calculate(input: List<String>): Int {
        val lineRegex = Regex("([a-z]+) (dec|inc) (-?[0-9]+) if ([a-z]+) (==|<|>=|<=|>|!=) (-?[0-9]+)")
        var values = input
            .map { lineRegex.matchEntire(it)!!.groupValues }
            .map { gp ->
                Instruction(
                        gp[1],
                        gp[3].toInt() * (if (gp[2] == "inc") 1 else -1),
                        gp[4],
                        Operator.values().first { it.symbol == gp[5] },
                        gp[6].toLong()
                )
            }
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
        return memory.maxBy { it.value }!!.value.toInt()
    }

    enum class Operator(val symbol: String, val method: (Long, Long) -> Boolean) {
        GREATER(">", {a,b -> a > b}),
        LESS("<", {a,b -> a < b}),
        GREATER_EQ(">=", {a,b -> a >= b}),
        LESS_EQ("<=", {a,b -> a <= b}),
        EQ("==", {a,b -> a.equals(b)}),
        N_EQ("!=", {a,b -> a != b}),
    }
    data class Instruction(
        val outReg: String,
        val change: Int,
        val sourceReg: String,
        val operator: Operator,
        val amount: Long
    )
}