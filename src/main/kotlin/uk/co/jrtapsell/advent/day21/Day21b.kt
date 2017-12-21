package uk.co.jrtapsell.advent.day21

import uk.co.jrtapsell.advent.MultilineFilePart

/**
 * @author James Tapsell
 */
object Day21b : MultilineFilePart<Int>("day21") {
    override fun calculate(input: List<String>): Int {

        val startingGrid: Grid = gridFromString(".#./..#/###")

        val (book2, book3) = getBooks(input)

        var grid = startingGrid

        (0..17).forEach {
            grid = grid.step(book2, book3)
        }

        return grid.flatMap { it.asIterable() }.count { it }
    }
}