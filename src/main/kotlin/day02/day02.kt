package day02

fun partOne(input : MutableList<Int>) : Int {
    return program(input, 12, 2)
}

fun partTwo(input : List<Int>, expectedOutput : Int) : Int {
    for (i in 0..99) {
        for (j in 0..99) {
            if (program(input.toMutableList(), i, j) == expectedOutput) {
                return 100 * i + j
            }
        }
    }
    return -1
}

fun program(input : MutableList<Int>, noun : Int, verb : Int) : Int {
    var ip = 0
    input[1] = noun
    input[2] = verb
    while (input[ip] != 99) {
        input[input[ip + 3]] = when (input[ip]) {
            1 -> {
                input[input[ip + 1]] + input[input[ip + 2]]
            }
            2 -> {
                input[input[ip + 1]] * input[input[ip + 2]]
            }
            else -> {
                -1
            }
        }
        ip += 4
    }
    return input[0]
}