package uk.co.jrtapsell.advent

/**
 * @author James Tapsell
 */
fun Int.safeMod(divisor: Int): Int {
    val v = this % divisor
    return if (v >= 0) v else v + divisor
}