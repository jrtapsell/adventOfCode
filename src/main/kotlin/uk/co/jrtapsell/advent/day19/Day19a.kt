package uk.co.jrtapsell.advent.day19

import javafx.scene.input.KeyCode
import uk.co.jrtapsell.advent.MultilineFilePart
import uk.co.jrtapsell.advent.safeMod
import java.awt.Robot

/**
 * @author James Tapsell
 */
val r = Robot()
enum class Direction(val rowDelta: Int, val columnDelta: Int, val track: Char, val keyCode: KeyCode) {
    UP(-1, 0, '|', KeyCode.UP),
    RIGHT(0,1, '-', KeyCode.RIGHT),
    DOWN(1,0, '|', KeyCode.DOWN),
    LEFT(0,-1, '-', KeyCode.LEFT);

    companion object {
        val VALS = Direction.values()
        val COUNT = VALS.count()
    }

    fun sendKeycode() {
        r.keyPress(keyCode.code)
        r.delay(50)
        r.keyRelease(keyCode.code)
        r.delay(100)
    }
}

data class State(val rowNumber: Int, val columnNumber: Int, val facing: Direction) {
    fun go(direction: Direction): State {
        return State(
                rowNumber + direction.rowDelta,
                columnNumber + direction.columnDelta,
                direction
        )
    }
}

fun State.isIn(grid: Array<CharArray>) =
    rowNumber in grid.indices && columnNumber in grid[rowNumber].indices

fun State.lookTo(grid: Array<CharArray>, direction: Direction): Char {
    val newState = go(direction)
    return if (newState.isIn(grid)) {
        grid[newState.rowNumber][newState.columnNumber]
    } else {
        ' '
    }
}

object Day19a: MultilineFilePart<String>("day19") {
    override fun calculate(input: List<String>): String {
        //Thread.sleep(1000)
        val grid = input.map { it.toCharArray() }.toTypedArray()
        val startingColumn = grid[0].indexOf('|')
        var log = ""
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
                        log += cellValue
                        state.go(facing)
                    } else {
                        null
                    }
                }
            }
            //state.facing.sendKeycode()
        }
        return log
    }

    private fun canGo(state: State, grid: Array<CharArray>, direction: Direction): Boolean {
        val test = state.lookTo(grid, direction)
        val canGo = test == '+' || test == direction.track
        return canGo
    }

}