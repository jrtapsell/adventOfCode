package uk.co.jrtapsell.advent

import java.io.File

/**
 * Base class for any day.
 *
 * @param T The output type of the day
 * @param I The type of the input of the day
 */
abstract class Part<T, I> {
    /** Gets the input for the given day. */
    abstract fun getInput(s: String): I
    /** Calculates the value for the given day. */
    abstract fun calculate(input: I): T

    fun run(s: String): T {
        val data = getInput(s)
        return calculate(data)
    }

    override fun toString(): String {
        return javaClass.simpleName
    }
}

abstract class MultilineFilePart<T>(val filename: String): Part<T, List<String>>() {
    override fun getInput(s: String): List<String> {
        return File("inputFiles/$s/$filename.txt")
            .readLines()
            .filter { it.trim() != "" }
    }
}

abstract class SinglelineFilePart<T>(val filename: String): Part<T, String>() {
    override fun getInput(s: String): String {
        return File("inputFiles/$s/$filename.txt")
            .readLines()[0]
    }
}