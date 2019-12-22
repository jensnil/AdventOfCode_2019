package day09

import java.util.*

fun partOne(instruction : MutableList<Long>) : Long {
    var output = LinkedList<Long>()
    val input = LinkedList<Long>()
    input.add(1)
    val ip = 0
    val relativeBase = 0
    program(Program(instruction, input, output, ip, relativeBase))
    return output.first()
}

fun partTwo(instruction : MutableList<Long>) : Long {
    var output = LinkedList<Long>()
    val input = LinkedList<Long>()
    input.add(2)
    val ip = 0
    val relativeBase = 0
    program(Program(instruction, input, output, ip, relativeBase))
    return output.first()
}

data class Program(val instructions: MutableList<Long>, val input: Queue<Long>, val output : Queue<Long>, var ip : Int, var relativeBase : Int)

fun program(program : Program) : Boolean {
    val memory = mutableMapOf<Int,Long>()
    while (program.instructions[program.ip] != 99L) {
        val instruction = "%05d".format(program.instructions[program.ip])
        when (instruction.substring(3, 5).toInt()) {
            1 -> { //addition
                val sum = getValue(instruction[2], program.instructions, program.ip, 1, program.relativeBase, memory) +
                        getValue(instruction[1], program.instructions, program.ip, 2, program.relativeBase, memory)
                assignValue(instruction[0], program.instructions, program.ip + 3, program.relativeBase, sum, memory)
                program.ip += 4
            }
            2 -> { //multiply
                val product =getValue(instruction[2], program.instructions, program.ip, 1, program.relativeBase, memory) *
                        getValue(instruction[1], program.instructions, program.ip, 2, program.relativeBase, memory)
                assignValue(instruction[0], program.instructions, program.ip + 3, program.relativeBase, product, memory)
                program.ip += 4
            }
            3 -> { //read
                if (program.input.isNotEmpty()) {
                    assignValue(instruction[2], program.instructions, program.ip + 1, program.relativeBase, program.input.remove(), memory)
                    program.ip += 2

                } else {
                    return false
                }
            }
            4 -> { //write
                val value = getValue(instruction[2], program.instructions, program.ip, 1, program.relativeBase, memory)
                program.output.add(value)
                program.ip += 2
            }
            5 -> { //jump-if-true
                val value = getValue(instruction[2], program.instructions, program.ip, 1, program.relativeBase, memory).toInt()
                if (value != 0) {
                    program.ip = getValue(instruction[1], program.instructions, program.ip, 2, program.relativeBase, memory).toInt()
                } else {
                    program.ip += 3
                }
            }
            6 -> { //jump-if-false
                val value = getValue(instruction[2], program.instructions, program.ip, 1, program.relativeBase, memory).toInt()
                if (value == 0) {
                    program.ip = getValue(instruction[1], program.instructions, program.ip, 2, program.relativeBase, memory).toInt()
                } else {
                    program.ip += 3
                }
            }
            7 -> { //less than
                val value1 = getValue(instruction[2], program.instructions, program.ip, 1, program.relativeBase, memory)
                val value2 = getValue(instruction[1], program.instructions, program.ip, 2, program.relativeBase, memory)
                if (value1 < value2) {
                    assignValue(instruction[0], program.instructions, program.ip + 3, program.relativeBase, 1, memory)
                } else {
                    assignValue(instruction[0], program.instructions, program.ip + 3, program.relativeBase, 0, memory)
                }
                program.ip += 4
            }
            8 -> { //equals
                val value1 = getValue(instruction[2], program.instructions, program.ip, 1, program.relativeBase, memory)
                val value2 = getValue(instruction[1], program.instructions, program.ip, 2, program.relativeBase, memory)
                if (value1 == value2) {
                    assignValue(instruction[0], program.instructions, program.ip + 3, program.relativeBase, 1, memory)
                } else {
                    assignValue(instruction[0], program.instructions, program.ip + 3, program.relativeBase, 0, memory)
                }
                program.ip += 4
            }
            9 -> { //adjust the relative base
                val value = getValue(instruction[2], program.instructions, program.ip, 1, program.relativeBase, memory).toInt()
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
            if (ip < instructions.size) {
                if (instructions[ip] < instructions.size) {
                    instructions[instructions[ip].toInt()] = value
                } else {
                    memory[instructions[ip].toInt()] = value
                }
            } else {
                memory[ip] = value
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
