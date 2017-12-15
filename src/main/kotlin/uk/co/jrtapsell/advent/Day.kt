package uk.co.jrtapsell.advent

abstract class Day<T, I> {
    abstract fun getInput(s: String): I
    abstract fun calculate(input: I): T

    fun run(s: String): T {
        val data = getInput(s)
        return calculate(data)
    }

    override fun toString(): String {
        return javaClass.simpleName
    }
}

abstract class MultilineFileDay<T>(val filename: String): Day<T, List<String>>() {
    override fun getInput(s: String): List<String> {
        return MultilineFileDay::class.java.getResourceAsStream("/$s/$filename.txt").
            reader()
            .readLines()
            .filter { it.trim() != "" }
    }
}

abstract class SinglelineFileDay<T>(val filename: String): Day<T, String>() {
    override fun getInput(s: String): String {
        return SinglelineFileDay::class.java.getResourceAsStream("/$s/$filename.txt").
            reader()
            .readLines()[0]
    }
}