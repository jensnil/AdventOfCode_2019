package day06

fun partOne(input : Map<String,String>) : Int {
    return countOrbits(input)
}

fun partTwo(input : Map<String,String>) : Int {
    val youPath = getPath(input, "YOU")
    val santaPath = getPath(input, "SAN")

    var i = 0
    while (youPath[i] == santaPath[i]) {
        i++
    }
    return youPath.size + santaPath.size - 2 * i
}

fun countOrbits(input : Map<String,String>) : Int {
    val root = input.values.minus(input.keys).toList().first()
    return input.map {
        entry ->
        var depth = 1
        var item = entry.key
        while (input[item] != root) {
            item = input[item]!!
            depth++
        }
        depth
    }.sum()
}

fun getPath(input : Map<String,String>, name : String) : List<String> {
    val root = input.values.minus(input.keys).toList().first()
    var item = name
    var path = mutableListOf<String>()
    while (input[item] != root) {
        item = input[item]!!
        path.add(0, item)
    }
    path.add(0, input[item]!!)
    return path
}
