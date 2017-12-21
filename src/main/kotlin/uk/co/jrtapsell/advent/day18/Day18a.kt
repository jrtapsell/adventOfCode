package uk.co.jrtapsell.advent.day18

import uk.co.jrtapsell.advent.MultilineFilePart

/**
 * @author James Tapsell
 */

object Day18a: MultilineFilePart<Int>("day18") {
    data class State(val registers: MutableMap<Char, Long>, var lastSound: Long, var pointer: Int)

    interface Instruction {
        fun operate(state: State)
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

    data class Sound(val input: List<Input>): Instruction {
        override fun operate(state: State) {
            val (value) = input.getValues(state)
            state.lastSound = value
        }
    }

    data class Set(val input: List<Input>): Instruction {
        override fun operate(state: State) {
            val value = input[1][state]
            input[0][state] = value
        }
    }

    data class Add(val input: List<Input>): Instruction {
        override fun operate(state: State) {
            val value = input[1][state]
            input[0][state] = input[0][state] + value
        }
    }

    data class Mul(val input: List<Input>): Instruction {
        override fun operate(state: State) {
            val value = input[1][state]
            input[0][state] = input[0][state] * value
        }
    }

    data class Mod(val input: List<Input>): Instruction {
        override fun operate(state: State) {
            val value = input[1][state]
            var ret = input[0][state] % value
            while (ret < 0) ret += value
            input[0][state] = ret
        }
    }

    data class Recover(val input: List<Input>): Instruction {
        override fun operate(state: State) {
            val value = input[0][state]
            if (value != 0L) {
                input[0][state] = state.lastSound
            }
        }
    }

    data class Jump(val input: List<Input>): Instruction {
        override fun operate(state: State) {
            val value = input[0][state]
            val jumpAmount = input[1][state]
            if (value > 0) {
                state.pointer += jumpAmount.toInt()
                state.pointer -= 1
            }
        }
    }

    fun getInstructions(input: List<String>): List<Instruction> {
        return input.map {
            val parts = it.split(" ")
            val command = parts[0]
            val arguments = parts.subList(1, parts.size).map { Input.read(it) }
            val creator: (List<Input>) -> Instruction = when (command) {
                "snd" -> {{Sound(it)}}
                "set" -> {{Set(it)}}
                "add" -> {{Add(it)}}
                "mul" -> {{Mul(it)}}
                "mod" -> {{Mod(it)}}
                "rcv" -> {{Recover(it)}}
                "jgz" -> {{Jump(it)}}
                else -> throw AssertionError()
            }
            creator(arguments)
        }
    }

    override fun calculate(input: List<String>): Int {
        val instructions = getInstructions(input)
        val state = State(mutableMapOf(), 0, 0)
        while (state.pointer in instructions.indices) {
            val current = instructions[state.pointer]
            if (current is Jump) {
                if (current.input[1][state] > 0) return state.lastSound.toInt()
            }
            current.operate(state)
            state.pointer++
        }
        return -1
    }
}