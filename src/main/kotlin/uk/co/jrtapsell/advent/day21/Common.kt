package uk.co.jrtapsell.advent.day21

/**
 * @author James Tapsell
 */
import java.util.*

/**
 * @author James Tapsell
 */
typealias Grid = Array<Array<Boolean>>
typealias Book = Map<Int, Grid>

fun Grid.display() {
    println("---------")
    forEach {
        it.forEach {
            if (it) print('#') else print('.')
        }
        println()
    }
    println("---------")
}
fun Grid.toNumber(): Int {
    return flatMap { it.asIterable() }
        .mapIndexed { index, value -> index to value }
        .filter { (_, value) -> value }
        .map { Math.pow(2.0, it.first.toDouble()).toInt() }
        .sum()
}

fun Grid.subGrid(rowIndicies: IntRange, columnIndicies: IntRange): Grid {
    return this.filterIndexed { index, _ -> index in rowIndicies }
        .map { Arrays.copyOfRange(it, columnIndicies.first, columnIndicies.endInclusive + 1) }
        .toTypedArray()
}

fun gridFromString(input: String): Grid {
    val cleaned = input.replace("/", "")
    val size = when (cleaned.length) {
        4 -> 2
        9 -> 3
        16 -> 4
        else -> throw AssertionError(cleaned)
    }
    val output: Grid = (0 until size).map { Array(size, { false }) }.toTypedArray()
    cleaned.forEachIndexed { index, c ->
        if (c == '#') {
            output[index / size][index % size] = true
        }
    }
    return output
}

fun Grid.step(book2: Book, book3: Book): Grid {
    val chunkSize = if (size % 2 == 0) 2 else 3
    val grids = (0 until size step chunkSize).map { row -> (0 until size step chunkSize).map { row to it } }
        .map {
            it.map { (row, column) ->
                val subGrid = subGrid((row until row + chunkSize), (column until column + chunkSize))
                subGrid.toNumbers()
            }
        }
    val book = if (chunkSize == 2) book2 else book3
    val newGrids = grids.map {
        it.map {
            getCrossover(it, book)
        }
    }
    val newChunkSize = chunkSize + 1
    val newSize = newChunkSize * newGrids.size
    val newGrid = Array(newSize, {Array(newSize, {false} )})

    newGrids.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, grid ->
            grid.forEachIndexed { gridRowIndex, gridRow ->
                gridRow.forEachIndexed { gridColumnIndex, value ->
                    if (value) {
                        val rowNumber = (rowIndex * newChunkSize) + gridRowIndex
                        val columnNumber = (columnIndex * newChunkSize) + gridColumnIndex
                        newGrid[rowNumber][columnNumber] = true
                    }
                }
            }
        }
    }
    return newGrid
}

fun getCrossover(grids: List<Int>, book: Book): Grid {
    for (i in grids) {
        if (i in book) {
            return book[i]!!
        }
    }
    throw AssertionError()
}

fun <T, U> Pair<T, T>.map(function: (T) -> U) = listOf(function(first), function(second))

val lineRegex = Regex("([#./]+) +=> +([#./]+)")

fun getBooks(input: List<String>): List<Map<Int, Grid>> {
    val grids = input.map { lineRegex.matchEntire(it)!! }
        .map { it.groupValues[1] to it.groupValues[2] }
        .map { (start, end) -> gridFromString(start) to gridFromString(end) }
    return grids
        .partition { it.first.size == 2 }
        .map { it }
        .map {
            it.flatMap { (start, end) -> start.toNumbers().map { it to end } }
                .associate { it }
        }
}

fun Grid.rotate(): Grid {
    return when (size) {
        2 -> {
            arrayOf(
                    arrayOf(this[1][0], this[0][0]),
                    arrayOf(this[1][1], this[0][1])
            )
        }
        3 -> {
            arrayOf(
                    arrayOf(this[2][0], this[1][0], this[0][0]),
                    arrayOf(this[2][1], this[1][1], this[0][1]),
                    arrayOf(this[2][2], this[1][2], this[0][2])
            )
        }
        else -> throw AssertionError(size)
    }
}

fun Grid.toNumbers(): List<Int> {
    val original = toNumber()
    val x = flipX()
    val flippedX = x.toNumber()
    val y = flipY()
    val flippedY = y.toNumber()
    val flips = listOf(original, flippedX, flippedY)
    var current = this
    val rotates = (0..3).map {
        current = current.rotate()
        val v =current.toNumber()
        v
    }
    return rotates + flips
}

fun Grid.flipY(): Grid = reversedArray()

fun Grid.flipX(): Grid = map { it.reversedArray() }.toTypedArray()
