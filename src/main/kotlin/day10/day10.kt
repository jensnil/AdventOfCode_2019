package day10

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2

fun partOne(input : Set<Pair<Int,Int>>) : Int {
    val result = getAllAsteroidsLists(input)
    return result.maxBy { item -> item.second.size }!!.second.size
}

fun partTwo(input : Set<Pair<Int,Int>>) : Int {
    val result = getAllAsteroidsLists(input).maxBy { item -> item.second.size }!!

    val origo = result.first
    val order = result.second.map {
        var angle = atan2((it.key.second - origo.second).toDouble(), (it.key.first - origo.first).toDouble() ) + PI/2
        if (angle < 0) {
            angle += 2 * PI
        }
        angle to it.value}.sortedBy { it.first }.map { it.second }.map { it.map { it.second }.toMutableList()
    }
    for (i in 0 until 199) {
        val slot = i % order.size
        val closestAsteriod = order[slot].minBy {
            abs(it.first - origo.first) + abs(it.second - origo.second)
        }
        order[slot].remove(closestAsteriod)
    }
    val toReturn = order[199 % order.size].minBy { abs(it.first - origo.first) + abs(it.second - origo.second) }!!
    return toReturn.first * 100 + toReturn.second
}

fun getAllAsteroidsLists(input : Set<Pair<Int,Int>>) =
        input.map {asteroid ->
            asteroid to
                    input.filter { it != asteroid }.map { anotherAsteroid ->
                        val diff = Pair(anotherAsteroid.first - asteroid.first, anotherAsteroid.second - asteroid.second)
                        val gcd = abs(gcd(diff.first, diff.second))
                        Pair(asteroid.first + diff.first / gcd, asteroid.second + diff.second / gcd) to anotherAsteroid
                    }.groupBy { item -> item.first }}

fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)