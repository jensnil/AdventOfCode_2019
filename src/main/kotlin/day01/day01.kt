package day01

fun partOne(input : List<Int>) : Int {
    return input.map { getFuelPart1(it) }.sum()
}

fun partTwo(input : List<Int>) : Int {
    return input.map { getFuelPart2(it) }.sum()
}

fun getFuelPart1(mass : Int) = mass / 3 - 2

fun getFuelPart2(mass : Int) : Int {
    val fuel = getFuelPart1(mass)
    return if (fuel > 0) {
        fuel + getFuelPart2(fuel)
    } else {
        0
    }
}