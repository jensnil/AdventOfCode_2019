package day05

import java.util.*

fun partOne(instruction : MutableList<Int>) : Int {
    val input = LinkedList<Int>()
    input.add(1)
    return program(instruction, input)
}

fun partTwo(instruction : MutableList<Int>) : Int {
    val input = LinkedList<Int>()
    input.add(5)
    return program(instruction, input)
}

fun program(instructions : MutableList<Int>, input : Queue<Int>) : Int {
    val output = mutableListOf<Int>()
    var ip = 0
    while (instructions[ip] != 99) {
        val instruction = "%05d".format(instructions[ip])
        when (instruction.substring(3, 5).toInt()) {
            1 -> { //addition
                instructions[instructions[ip + 3]] = getValue(instruction[2], instructions, ip, 1) + getValue(instruction[1], instructions, ip, 2)
                ip += 4
            }
            2 -> { //multiply
                instructions[instructions[ip + 3]] = getValue(instruction[2], instructions, ip, 1) * getValue(instruction[1], instructions, ip, 2)
                ip += 4
            }
            3 -> { //read
                instructions[instructions[ip + 1]] = input.remove()
                ip += 2
            }
            4 -> { //write
                val value = getValue(instruction[2], instructions, ip, 1)
                output.add(value)
                ip += 2
            }
            5 -> { //jump-if-true
                val value = getValue(instruction[2], instructions, ip, 1)
                if (value != 0) {
                    ip = getValue(instruction[1], instructions, ip, 2)
                } else {
                    ip += 3
                }
            }
            6 -> { //jump-if-false
                val value = getValue(instruction[2], instructions, ip, 1)
                if (value == 0) {
                    ip = getValue(instruction[1], instructions, ip, 2)
                } else {
                    ip += 3
                }
            }
            7 -> { //less than
                val value1 = getValue(instruction[2], instructions, ip, 1)
                val value2 = getValue(instruction[1], instructions, ip, 2)
                if (value1 < value2) {
                    instructions[instructions[ip + 3]] = 1
                } else {
                    instructions[instructions[ip + 3]] = 0
                }
                ip += 4
            }
            8 -> { //equals
                val value1 = getValue(instruction[2], instructions, ip, 1)
                val value2 = getValue(instruction[1], instructions, ip, 2)
                if (value1 == value2) {
                    instructions[instructions[ip + 3]] = 1
                } else {
                    instructions[instructions[ip + 3]] = 0
                }
                ip += 4
            }
            else -> {
                print("Error")
            }
        }
    }
    return output.last()
}

fun getValue(mode : Char, instructions : MutableList<Int>, ip : Int, offset : Int) : Int {
    return if (mode == '0') {
        instructions[instructions[ip + offset]]
    } else {
        instructions[ip + offset]
    }
}