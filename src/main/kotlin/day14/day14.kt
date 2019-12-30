package day14

fun partOne(input : Map<String, Pair<Int, List<Pair<Int,String>>>>) : Int {
    return getQuantity(input, "FUEL")
}

fun partTwo(input : Map<String, Pair<Int, List<Pair<Int,String>>>>) : Int {
    return -1
}

fun getQuantity(input : Map<String, Pair<Int, List<Pair<Int,String>>>>, startResource : String) : Int {
    val required = input.keys.map { it to 0 }.toMap().toMutableMap()
    required[startResource] = input[startResource]?.first ?: error("No way this can happen!")
    var ore = 0
    var needMoreResources = true
    while (needMoreResources) {
        val key = required.toList().firstOrNull { it.second > 0 }
        needMoreResources = key?.let { key ->
            val needed = input[key.first] ?: error("Can't happen either!")
            required[key.first] = required[key.first]?.minus(needed.first)!!
            needed.second.forEach { resource ->
                val resourceIsNotOre = required[resource.second]
                resourceIsNotOre?.let {
                    required[resource.second] = resource.first + resourceIsNotOre
                } ?: run {
                    ore += resource.first
                }
            }
            true
        } ?: false
    }
    return ore
}