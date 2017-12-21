package uk.co.jrtapsell.advent.day20

import uk.co.jrtapsell.advent.MultilineFilePart
import uk.co.jrtapsell.advent.day20.Day20a.component6
import uk.co.jrtapsell.advent.day20.Day20a.component7
import uk.co.jrtapsell.advent.day20.Day20a.component8
import uk.co.jrtapsell.advent.day20.Day20a.component9

/**
 * @author James Tapsell
 */
fun Int.abs() = if (this >= 0) this else -this

data class Tri(val x: Int, val y: Int, val z: Int) {
    fun magnitude() = x.abs()+y.abs()+z.abs()

    operator fun plus(other: Tri): Tri {
        return Tri(x + other.x, y + other.y, z + other.z)
    }
}
data class Particle(val id: Int, val position: Tri, val velocity: Tri, val acceleration: Tri) {
    fun step(): Particle {
        val newVel = velocity + acceleration
        val newPos = position + newVel
        return Particle(id, newPos, newVel, acceleration)
    }
}

val number = "(-?[0-9]+)"
val LINE_REGEX = Regex("""p=<$number,$number,$number>, v=<$number,$number,$number>, a=<$number,$number,$number>""")


private fun parse(id: Int, lineValues: List<String>): Particle {
    val values = lineValues.filterIndexed { index, _ -> index != 0 }
        .map { it.toInt() }
    val (pX,pY,pZ,vX,vY,vZ,aX,aY,aZ) = values
    return Particle(id, Tri(pX, pY, pZ), Tri(vX, vY, vZ), Tri(aX, aY, aZ))
}

fun getParticles(input: List<String>) =
    input.map { LINE_REGEX.matchEntire(it)?.groupValues }
        .mapIndexed { index, it -> parse(index, it!!) }

object Day20a: MultilineFilePart<Int>("day20") {
    override fun calculate(input: List<String>): Int {

        var current = getParticles(input)


        for (f in listOf<(Particle)->Int>(
                { it.acceleration.magnitude() },
                { it.velocity.magnitude() },
                { it.position.magnitude() }
        )) {
            val (_, items) = minimalBy(current, f)

            if (items.size == 1) {
                return items[0].id
            }

            current = items
        }
        return -1

    }

    private fun minimalBy(particles: List<Particle>, reduceBy: (Particle) -> Int): Pair<Int, List<Particle>> {
        return particles.groupBy(reduceBy)
            .toList()
            .sortedBy { it.first }[0]
    }

    operator fun List<Int>.component6(): Int = this[5]
    operator fun List<Int>.component7(): Int = this[6]
    operator fun List<Int>.component8(): Int = this[7]
    operator fun List<Int>.component9(): Int = this[8]
}