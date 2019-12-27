package day12

import kotlin.math.abs

fun partOne(input : List<MutableList<Int>>) : Int {
    val velocities = input.map { mutableListOf(0, 0, 0) }
    (1..1000).forEach{ _ ->
        moveMoons(input, velocities)
    }
    return totalEnergy(input, velocities)
}

fun partTwo(input : List<MutableList<Int>>) : Long {
    val startValues = input.flatten().withIndex().groupBy { it.index % 3 }.values.map { indexedValue -> indexedValue.map { it.value } }
    var velocities = input.map { mutableListOf(0, 0, 0) }
    val period = (1..startValues.size).map { -1L }.toMutableList()
    var step = 1L
    while (period.any { it == -1L }) { //period.any { it == -1L }
        moveMoons(input, velocities)
        val newValues = input.flatten().withIndex().groupBy { it.index % 3 }.values.map { indexedValue -> indexedValue.map { it.value } }
        period.indices.filter {dimension ->  period[dimension] == -1L && newValues[dimension] == startValues[dimension]}.forEach {dimension ->
            period[dimension] = step + 1
        }
        step++
    }
    return period.reduce{ acc, l -> lcm(acc, l)}
}

fun moveMoons(positions : List<MutableList<Int>>, velocities : List<MutableList<Int>>) {
    positions.forEachIndexed {moon1Index, moon1 ->
        positions.forEachIndexed {moon2Index, moon2 ->
            if (moon1Index < moon2Index) {
                moon1.indices.forEach { dimensionIndex ->
                    when (moon1[dimensionIndex].compareTo(moon2[dimensionIndex])) {
                        -1 -> {
                            velocities[moon1Index][dimensionIndex]++
                            velocities[moon2Index][dimensionIndex]--
                        }
                        1 -> {
                            velocities[moon1Index][dimensionIndex]--
                            velocities[moon2Index][dimensionIndex]++
                        }
                    }
                }
            }
        }
    }
    positions.forEachIndexed {moonIndex, moon ->
        moon.indices.forEach { dimensionIndex ->
            moon[dimensionIndex] += velocities[moonIndex][dimensionIndex]
        }
    }
}

fun totalEnergy(positions : List<MutableList<Int>>, velocities : List<MutableList<Int>>) : Int {
    return positions.map { position ->
        position.map { abs(it) }.sum()
    }.mapIndexed { index, position ->
        position * velocities[index].map { abs(it) }.sum()
    }.sum()
}

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

fun lcm(a: Long, b: Long): Long = a * b / gcd(a, b)
