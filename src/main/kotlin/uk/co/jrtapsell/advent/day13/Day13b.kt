package uk.co.jrtapsell.advent.day13

import uk.co.jrtapsell.advent.MultilineFileDay

/**
 * @author James Tapsell
 */
data class Scanner(val id: Int, val range: Int)
object Day13b: MultilineFileDay<Int>("day13") {
    override fun calculate(input: List<String>): Int {
        val items = input.map {
            val (id, range) = it.split(":")
            Scanner(id.trim().toInt(10), range.trim().toInt(10))
        }
        return (0..Int.MAX_VALUE).first { offset ->
            items.map { (id, range) ->
                val scannerPlace = (offset + id) % ((range * 2) - 2)

                val scannerValue = if (scannerPlace >= range) {
                    (scannerPlace - range) + 2
                } else {
                    scannerPlace
                }
                if (scannerValue == 0) {
                    1
                } else {
                    0
                }
            }.sum() == 0
        }
    }

}