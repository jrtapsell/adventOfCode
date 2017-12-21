package uk.co.jrtapsell.advent.day19

import javafx.scene.input.KeyCode
import uk.co.jrtapsell.advent.MultilineFilePart
import uk.co.jrtapsell.advent.safeMod
import java.awt.Robot

/**
 * @author James Tapsell
 */

object Day19b: MultilineFilePart<Int>("day19") {
    override fun calculate(input: List<String>): Int {
        //Thread.sleep(1000)
        val grid = input.map { it.toCharArray() }.toTypedArray()
        val startingColumn = grid[0].indexOf('|')
        var log = 0
        var state: State? = State(0, startingColumn, Direction.DOWN)
        while (state != null && state.isIn(grid)) {
            val rowNumber = state.rowNumber
            val columnNumber = state.columnNumber
            val facing = state.facing
            val cellValue = grid[rowNumber][columnNumber]
            state = when (cellValue) {
                '|', '-' -> {
                    state.go(facing)
                }
                '+' -> {
                    val forwards = facing
                    val leftOrdinal = (forwards.ordinal - 1).safeMod(Direction.COUNT)
                    val rightOrdinal = (forwards.ordinal + 1).safeMod(Direction.COUNT)
                    val left = Direction.VALS[leftOrdinal]
                    val right = Direction.VALS[rightOrdinal]
                    when {
                        canGo(state, grid, forwards) -> state.go(facing)
                        canGo(state, grid, left) -> state.go(left)
                        canGo(state, grid, right) -> state.go(right)
                        else -> throw AssertionError(state)
                    }

                }
                else -> {
                    if (cellValue.isLetter()) {
                        state.go(facing)
                    } else {
                        null
                    }
                }
            }
            log++
            //state.facing.sendKeycode()
        }
        log--
        return log
    }

    private fun canGo(state: State, grid: Array<CharArray>, direction: Direction): Boolean {
        val test = state.lookTo(grid, direction)
        val canGo = test == '+' || test == direction.track
        return canGo
    }

}