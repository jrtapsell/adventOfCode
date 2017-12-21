package uk.co.jrtapsell.advent.day18

import uk.co.jrtapsell.advent.MultilineFilePart
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

/**
 * @author James Tapsell
 */

object Day18b : MultilineFilePart<Int>("day18") {
    data class State(
        val registers: MutableMap<Char, Long>,
        var pointer: Int,
        var isWaiting: Boolean,
        val myQueue: MutableList<Long>,
        val otherQueue: MutableList<Long>,
        var sendCount: Int
    )

    sealed class Instruction {
        abstract fun operate(state: State)

        data class Send(val input: List<Input>): Instruction() {
            override fun operate(state: State) {
                state.otherQueue.add(input[0][state])
                state.sendCount++
            }
        }

        data class Set(val input: List<Input>): Instruction() {
            override fun operate(state: State) {
                val value = input[1][state]
                input[0][state] = value
            }
        }

        data class Add(val input: List<Input>): Instruction() {
            override fun operate(state: State) {
                val value = input[1][state]
                input[0][state] = input[0][state] + value
            }
        }

        data class Mul(val input: List<Input>): Instruction() {
            override fun operate(state: State) {
                val value = input[1][state]
                input[0][state] = input[0][state] * value
            }
        }

        data class Mod(val input: List<Input>): Instruction() {
            override fun operate(state: State) {
                val value = input[1][state]
                var ret = input[0][state] % value
                while (ret < 0) ret += value
                input[0][state] = ret
            }
        }

        data class Recieve(val input: List<Input>): Instruction() {
            override fun operate(state: State) {
                if (state.myQueue.isEmpty()) {
                    state.pointer--
                    state.isWaiting = true
                } else {
                    input[0][state] = state.myQueue.removeAt(0)
                }
            }
        }

        data class Jump(val input: List<Input>): Instruction() {
            override fun operate(state: State) {
                val value = input[0][state]
                val jumpAmount = input[1][state]
                if (value > 0) {
                    state.pointer += jumpAmount.toInt()
                    state.pointer -= 1
                }
            }
        }
    }

    fun List<Input>.getValues(state: State) = this.map { it[state] }

    interface Input {
        operator fun get(state: State): Long
        operator fun set(state: State, value: Long)

        companion object {
            val numberRegex = Regex("-?[0-9]+")
            fun read(input: String): Input {
                return if (numberRegex.matches(input)) {
                    Number(input.toLong(10))
                } else {
                    Register(input[0])
                }
            }
        }

    }

    data class Number(val value: Long): Input {
        override operator fun get(state: State) = value
        override fun set(state: State, value: Long) = throw AssertionError()

        override fun toString(): String {
            return "<$value>"
        }
    }

    data class Register(val name: Char): Input {
        override fun get(state: State) = state.registers[name] ?: 0
        override fun set(state: State, value: Long) {
            state.registers[name] = value
        }

        override fun toString(): String {
            return "[$name]"
        }
    }

    fun getInstructions(input: List<String>): List<Instruction> {
        return input.map {
            val parts = it.split(" ")
            val command = parts[0]
            val arguments = parts.subList(1, parts.size).map { Input.read(it) }
            val creator: (List<Input>) -> Instruction = when (command) {
                "snd" -> {{ Instruction.Send(it) }}
                "set" -> {{Instruction.Set(it)}}
                "add" -> {{Instruction.Add(it)}}
                "mul" -> {{Instruction.Mul(it)}}
                "mod" -> {{Instruction.Mod(it)}}
                "rcv" -> {{ Instruction.Recieve(it)}}
                "jgz" -> {{Instruction.Jump(it)}}
                else -> throw AssertionError()
            }
            creator(arguments)
        }
    }

    override fun calculate(input: List<String>): Int {
        val instructions = getInstructions(input)
        val queue0 = mutableListOf<Long>()
        val queue1 = mutableListOf<Long>()
        val state0 = State(mutableMapOf('p' to 0L), 0, false, queue0, queue1, 0)
        val state1 = State(mutableMapOf('p' to 1L), 0, false, queue1, queue0, 0)
        while (!state0.isWaiting || !state1.isWaiting) {
            for (i in listOf(state0, state1)) {
                runStep(instructions, i)
            }
        }
        return state1.sendCount
    }

    private fun runStep(instructions: List<Instruction>, state: State) {
        val pointer = state.pointer
        if (pointer >= 0) {
            val current = instructions[pointer]
            current.operate(state)
            state.pointer++
            if (state.pointer !in instructions.indices) {
                state.pointer = -1
                state.isWaiting = true
            }
        }
    }
}