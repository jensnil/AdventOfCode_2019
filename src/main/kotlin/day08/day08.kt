package day08

val width = 25
val height = 6

fun partOne(input : List<Int>) : Int {
    val layers = getDeSerializedImage(input)
    val minLayer = layers.minBy { layer ->
        layer.flatten().count{ it == 0 }
    }
    val countOnes = minLayer?.let {
        it.flatten().count { a -> a == 1 }
    } ?: 0
    val countTwos = minLayer?.let {
        it.flatten().count { a -> a == 2 }
    } ?: 0
    return countOnes * countTwos
}

fun partTwo(input : List<Int>) : Int {
    printDecodedImage(getDeSerializedImage(input))
    return -1
}

fun getDeSerializedImage(input : List<Int>) : List<List<List<Int>>> {
    return (0 until input.size / (width * height)).map {layer ->
        (0 until height).map {line ->
            (0 until width).map {column ->
                input[width * height * layer + line * width + column]
            }
        }
    }

}

fun printEncodedImage(layers : List<List<List<Int>>>) {
    layers.forEachIndexed{i, layer ->
        println("Layer $i:")
        layer.forEach { line ->
            print("\t\t")
            line.forEach {cell ->
                print(cell)
            }
            println()
        }
    }
}

fun printDecodedImage(layers : List<List<List<Int>>>) {
    (0 until height).forEach {y ->
        (0 until width).forEach {x ->
            var layerIndex = 0
            while (layers[layerIndex][y][x] == 2) {
                layerIndex++
            }
            print(if (layers[layerIndex][y][x] == 1) 'X' else ' ')
        }
        println()
    }
}
