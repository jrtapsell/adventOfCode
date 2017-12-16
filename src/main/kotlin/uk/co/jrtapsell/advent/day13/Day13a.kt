package uk.co.jrtapsell.advent.day13

import uk.co.jrtapsell.advent.MultilineFilePart

/**
 * @author James Tapsell
 */
object Day13a: MultilineFilePart<Int>("day13") {
    override fun calculate(input: List<String>): Int {
        return input.map {
            val (id, range) = it.split(":")
            Scanner(id.trim().toInt(10), range.trim().toInt(10))
        }.map { (id, range) ->
            val scannerPlace = id % ((range*2) - 2)

            val scannerValue = if (scannerPlace >= range) {
                (scannerPlace - range) + 2
            } else {
                scannerPlace
            }
            if (scannerValue == 0) {
                id * range
            } else {
                0
            }
        }.sum()
    }

}