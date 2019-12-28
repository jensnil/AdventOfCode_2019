package day13

import java.util.*

fun partOne(instruction : MutableList<Long>) : Int {
    return gameOne(instruction).map { it[2] }.count { it == 2L }
}

fun partTwo(instruction : MutableList<Long>) : Int {
    return gameTwo(instruction)
}

data class Program(val instructions: MutableList<Long>, val input: Queue<Long>, val output : Queue<Long>, var ip : Int, var relativeBase : Int, val memory : MutableMap<Int, Long>)

fun gameOne(instruction : MutableList<Long>) : List<List<Long>> {
    var memory = mutableMapOf<Int,Long>()
    var output = LinkedList<Long>()
    var input = LinkedList<Long>()
    var ip = 0
    var relativeBase = 0
    var theProgram = Program(instruction, input, output, ip, relativeBase, memory)
    program(theProgram)
    val chunks = theProgram.output.chunked(3)
    printImage(chunks.map { Pair(it[0]!!.toInt(), it[1]!!.toInt()) to it[2]!! }.toMap())
    return theProgram.output.chunked(3)
}

fun gameTwo(instruction : MutableList<Long>) : Int {
    var memory = mutableMapOf<Int,Long>()
    var output = LinkedList<Long>()
    var input = LinkedList<Long>()
    instruction[0] = 2
    var ip = 0
    var relativeBase = 0
    var theProgram = Program(instruction, input, output, ip, relativeBase, memory)
    while (!program(theProgram)) {
        var chunks = theProgram.output.chunked(3)
        val paddle = chunks.last { it[2] == 3L}
        val ball = chunks.last { it[2] == 4L}
        input.add(ball[0].compareTo(paddle[0]).toLong())
    }
    var chunks = theProgram.output.chunked(3)
    return chunks.findLast { it[0] == -1L && it[1] == 0L }!![2].toInt()
}

fun printImage(image : Map<Pair<Int,Int>, Long>) {
    val minX = image.minBy {it.key.first}!!.key.first
    val maxX = image.maxBy {it.key.first}!!.key.first
    val minY = image.minBy {it.key.second}!!.key.second
    val maxY = image.maxBy {it.key.second}!!.key.second

    (minY .. maxY).forEach {y ->
        (minX..maxX).forEach {x ->
            print(
                    when (image[Pair(x, y)]) {
                        null, 0L -> " "
                        1L -> "|"
                        2L -> "#"
                        3L -> "_"
                        4L -> "o"
                        else -> image[Pair(x, y)].toString() + "\n"
                    }
            )
        }
        println()
    }
}


fun program(program : Program) : Boolean {
    while (program.instructions[program.ip] != 99L) {
        val instruction = "%05d".format(program.instructions[program.ip])
        //println(program.ip)
        when (instruction.substring(3, 5).toInt()) {
            1 -> { //addition
                val sum = getValue(instruction[2], program.instructions, program.ip, 1, program.relativeBase, program.memory) +
                        getValue(instruction[1], program.instructions, program.ip, 2, program.relativeBase, program.memory)
                assignValue(instruction[0], program.instructions, program.ip + 3, program.relativeBase, sum, program.memory)
                program.ip += 4
            }
            2 -> { //multiply
                val product =getValue(instruction[2], program.instructions, program.ip, 1, program.relativeBase, program.memory) *
                        getValue(instruction[1], program.instructions, program.ip, 2, program.relativeBase, program.memory)
                assignValue(instruction[0], program.instructions, program.ip + 3, program.relativeBase, product, program.memory)
                program.ip += 4
            }
            3 -> { //read
                if (program.input.isNotEmpty()) {
                    assignValue(instruction[2], program.instructions, program.ip + 1, program.relativeBase, program.input.remove(), program.memory)
                    program.ip += 2

                } else {
                    return false
                }
            }
            4 -> { //write
                val value = getValue(instruction[2], program.instructions, program.ip, 1, program.relativeBase, program.memory)
                program.output.add(value)
                program.ip += 2
            }
            5 -> { //jump-if-true
                val value = getValue(instruction[2], program.instructions, program.ip, 1, program.relativeBase, program.memory).toInt()
                if (value != 0) {
                    program.ip = getValue(instruction[1], program.instructions, program.ip, 2, program.relativeBase, program.memory).toInt()
                } else {
                    program.ip += 3
                }
            }
            6 -> { //jump-if-false
                val value = getValue(instruction[2], program.instructions, program.ip, 1, program.relativeBase, program.memory).toInt()
                if (value == 0) {
                    program.ip = getValue(instruction[1], program.instructions, program.ip, 2, program.relativeBase, program.memory).toInt()
                } else {
                    program.ip += 3
                }
            }
            7 -> { //less than
                val value1 = getValue(instruction[2], program.instructions, program.ip, 1, program.relativeBase, program.memory)
                val value2 = getValue(instruction[1], program.instructions, program.ip, 2, program.relativeBase, program.memory)
                if (value1 < value2) {
                    assignValue(instruction[0], program.instructions, program.ip + 3, program.relativeBase, 1, program.memory)
                } else {
                    assignValue(instruction[0], program.instructions, program.ip + 3, program.relativeBase, 0, program.memory)
                }
                program.ip += 4
            }
            8 -> { //equals
                val value1 = getValue(instruction[2], program.instructions, program.ip, 1, program.relativeBase, program.memory)
                val value2 = getValue(instruction[1], program.instructions, program.ip, 2, program.relativeBase, program.memory)
                if (value1 == value2) {
                    assignValue(instruction[0], program.instructions, program.ip + 3, program.relativeBase, 1, program.memory)
                } else {
                    assignValue(instruction[0], program.instructions, program.ip + 3, program.relativeBase, 0, program.memory)
                }
                program.ip += 4
            }
            9 -> { //adjust the relative base
                val value = getValue(instruction[2], program.instructions, program.ip, 1, program.relativeBase, program.memory).toInt()
                program.relativeBase += value
                program.ip += 2
            }
            else -> {
                print("Error")
            }
        }
    }
    return true
}

fun getValue(mode : Char, instructions : MutableList<Long>, ip : Int, offset : Int, relativeBase: Int, memory : Map<Int,Long>) : Long {
    var index = when (mode) {
        '0' ->
            instructions[ip + offset].toInt()
        '1' -> ip + offset
        else -> relativeBase + instructions[ip + offset].toInt()
    }
    return if (index < instructions.size) {
        instructions[index]
    } else {
        memory[index] ?: 0L
    }
}

fun assignValue(mode : Char, instructions : MutableList<Long>, ip : Int, relativeBase: Int, value : Long, memory : MutableMap<Int,Long>) {
    when (mode) {
        '0','1' ->
            if (instructions[ip] < instructions.size) {
                instructions[instructions[ip].toInt()] = value
            } else {
                memory[instructions[ip].toInt()] = value
            }
        '2' ->
            if (relativeBase + instructions[ip] < instructions.size) {
                instructions[relativeBase + instructions[ip].toInt()] = value
            } else {
                memory[relativeBase + instructions[ip].toInt()] = value
            }
        else ->
            print("Error")
    }
}
