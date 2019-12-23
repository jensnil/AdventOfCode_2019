package day11

import java.util.*

fun partOne(instruction : MutableList<Long>) : Int {
    return paint(instruction, 0).count()
}

fun partTwo(instruction : MutableList<Long>) {
    printImage(paint(instruction, 1))
}

data class Program(val instructions: MutableList<Long>, val input: Queue<Long>, val output : Queue<Long>, var ip : Int, var relativeBase : Int, val memory : MutableMap<Int, Long>)

fun paint(instruction : MutableList<Long>, startColor : Long) : Map<Pair<Int,Int>,Long> {
    var memory = mutableMapOf<Int,Long>()
    var output = LinkedList<Long>()
    var input = LinkedList<Long>()
    var image = mutableMapOf<Pair<Int,Int>,Long>()
    var position = Pair(0, 0)
    input.add(image[position] ?: startColor)
    var ip = 0
    var relativeBase = 0
    var direction = '^'

    var theProgram = Program(instruction, input, output, ip, relativeBase, memory)
    while (!program(theProgram)) {
        val color = output.remove()
        image[position] = color
        val turn = output.remove()
        when (direction) {
            '^' -> {
                when (turn) {
                    0L -> {
                        direction = '<'
                        position = Pair(position.first - 1, position.second)
                    }
                    1L -> {
                        direction = '>'
                        position = Pair(position.first + 1, position.second)
                    }
                }
            }
            '<' -> {
                when (turn) {
                    0L -> {
                        direction = 'v'
                        position = Pair(position.first, position.second - 1)
                    }
                    1L -> {
                        direction = '^'
                        position = Pair(position.first, position.second + 1)
                    }
                }
            }
            'v' -> {
                when (turn) {
                    0L -> {
                        direction = '>'
                        position = Pair(position.first + 1, position.second)
                    }
                    1L -> {
                        direction = '<'
                        position = Pair(position.first - 1, position.second)
                    }
                }
            }
            '>' -> {
                when (turn) {
                    0L -> {
                        direction = '^'
                        position = Pair(position.first, position.second + 1)
                    }
                    1L -> {
                        direction = 'v'
                        position = Pair(position.first, position.second - 1)
                    }
                }
            }
        }
        output.clear()
        input.add(image[position] ?: 0L)
    }
    return image
}

fun printImage(image : Map<Pair<Int,Int>, Long>) {
    val minX = image.minBy {it.key.first}!!.key.first
    val maxX = image.maxBy {it.key.first}!!.key.first
    val minY = image.minBy {it.key.second}!!.key.second
    val maxY = image.maxBy {it.key.second}!!.key.second

    println("=========")
    (maxY downTo minY).forEach {y ->
        (minX..maxX).forEach {x ->
            print(if (image[Pair(x, y)] == 1L) "*" else " ")
        }
        println()
    }
    println("=========")

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
    return when (mode) {
        '0' -> {
            if (instructions[ip + offset] < instructions.size) {
                instructions[instructions[ip + offset].toInt()]
            } else {
                memory[instructions[ip + offset].toInt()] ?: 0L
            }
        }
        '1' -> {
            if (ip + offset < instructions.size) {
                instructions[ip + offset]
            } else {
                memory[ip + offset] ?: 0
            }
        }
        else -> {
            if (relativeBase + instructions[ip + offset] < instructions.size) {
                instructions[relativeBase + instructions[ip + offset].toInt()]
            } else {
                memory[relativeBase + instructions[ip + offset].toInt()] ?: 0
            }
        }
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

val testData = listOf(listOf(1,0), listOf(0, 0), listOf(1, 0), listOf(1, 0), listOf(0,1), listOf(1,0), listOf(1,0))
var testDataIndex = 0

fun testProgram(program : Program) : Boolean {
    if (testDataIndex < testData.size) {
        program.output.add(testData[testDataIndex][0].toLong())
        program.output.add(testData[testDataIndex][1].toLong())
        testDataIndex++
        return false
    }
    return true
}
