package day03

import kotlin.math.abs

fun partOne(input : Pair<List<Pair<Char, Int>>, List<Pair<Char, Int>>>) : Int {
    val firstWire = getWire(input.first)
    val secondWire = getWire(input.second)

    return firstWire.intersect(secondWire).map { abs(it.first) + abs(it.second) }.min()!!
}


fun partTwo(input : Pair<List<Pair<Char, Int>>, List<Pair<Char, Int>>>) : Int {
    val firstWire = getWireSteps(input.first)
    val secondWire = getWireSteps(input.second)

    val firstWirePositions = getWireSteps(input.first).map { it.first }
    val secondWirePositions = getWireSteps(input.second).map { it.first }

    val intersections = firstWirePositions.intersect(secondWirePositions)

    return intersections.map { firstWire.first { first -> first.first == it }.second  + secondWire.first { first -> first.first == it }.second }.min()!!
}

fun getWire(path : List<Pair<Char, Int>>) : MutableSet<Pair<Int, Int>> {
    val wire = mutableSetOf<Pair<Int,Int>>()

    var position = Pair(0, 0)
    path.forEach {
        val direction = getDirection(it.first)
        for (i in 1..it.second) {
            position = Pair(position.first + direction.first, position.second + direction.second)
            wire.add(position)
        }
    }
    return wire
}

fun getWireSteps(path : List<Pair<Char, Int>>) : MutableSet<Pair<Pair<Int, Int>, Int>> {
    val wire = mutableSetOf<Pair<Pair<Int,Int>,Int>>()
    val wire2 = mutableSetOf<Pair<Int,Int>>()

    var position = Pair(Pair(0, 0), 0)
    var step = 0
    path.forEach {
        val direction= getDirection(it.first)
        for (i in 1..it.second) {
            step++
            position = Pair(Pair(position.first.first + direction.first, position.first.second + direction.second), step)
            if (!wire2.contains(position.first)) {
                wire.add(position)
                wire2.add(position.first)
            } else {
                val i = 0
            }
        }
    }
    return wire
}

fun getDirection(command : Char) = when (command) {
    'U' -> {
        Pair(0, 1)
    }
    'D' -> {
        Pair(0, -1)
    }
    'L' -> {
        Pair(-1, 0)
    }
    'R' -> {
        Pair(1, 0)
    }
    else -> {
        Pair(0, 0)
    }
  }

fun printBothWire(wire1 : MutableSet<Pair<Int, Int>>, wire2 : MutableSet<Pair<Int, Int>>) {
    val union = wire1.union(wire2)
    val minX = union.map { it.first }.min()!!
    val minY = union.map { it.second }.min()!!
    val maxX = union.map { it.first }.max()!!
    val maxY = union.map { it.second }.max()!!

    for (y in maxY+1 downTo minY) {
        for (x in minX .. maxX) {
            when {
                wire1.contains(Pair(x,y)) && wire2.contains(Pair(x,y))  -> print('X')
                wire1.contains(Pair(x,y)) -> print('*')
                wire2.contains(Pair(x,y)) -> print('#')
                else -> print ('.')
            }
        }
        println()
    }
    println()
}
