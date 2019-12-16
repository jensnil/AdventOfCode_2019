package day04

fun partOne(input : Pair<Int,Int>) : Int {
    var count = 0
    for (i in input.first .. input.second) {
        val asString = i.toString()
        if ((0 .. asString.length - 2).all { asString[it] <= asString[it + 1]} &&
                (0 .. asString.length - 2).any { asString[it] == asString[it + 1]}

                ) {
            count++
        }
    }
    return count
}


fun partTwo(input : Pair<Int,Int>) : Int {
    var count = 0
    for (i in input.first .. input.second) {
        val asString = i.toString()
        if ((0 .. asString.length - 2).all { asString[it] <= asString[it + 1]} &&
                (0 .. asString.length - 2).any { asString[it] == asString[it + 1]} &&
                asString.toCharArray().groupBy { it }.values.any { it.size == 2 }) {
            count++
        }
    }
    return count
}
