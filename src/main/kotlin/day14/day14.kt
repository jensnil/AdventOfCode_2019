package day14

fun partOne(input : Map<String, Pair<Int, List<Pair<Int,String>>>>) : Int {
    return getQuantity(input, "FUEL")
}

fun partTwo(input : Map<String, Pair<Int, List<Pair<Int,String>>>>) : Int {
    return -1
}

fun getQuantity(input : Map<String, Pair<Int, List<Pair<Int,String>>>>, startResource : String) : Int {
    val required = input.keys.map { it to
            if (it == startResource) input[startResource]?.first ?: 0 else 0
    }.toMap().toMutableMap()
    var ore = 0
    var key = required.toList().firstOrNull { it.second > 0 }
    while (key != null) {
        val needed = input[key.first] ?: error("Can't happen!")
        required[key.first] = required[key.first]?.minus(needed.first) ?: error("Can't happen!")
        needed.second.forEach { resource ->
            val resourceIsNotOre = required[resource.second]
            resourceIsNotOre?.let {
                required[resource.second] = resource.first + resourceIsNotOre
            } ?: run {
                ore += resource.first
            }
        }
        key = required.toList().firstOrNull { it.second > 0 }
    }
    return ore
}