package day07

import java.util.*

fun partOne(instruction : MutableList<Int>) : Int {
    var best = Int.MIN_VALUE
    for (i in 0 until 5*5*5*5*5) {
        val setting = int2List(i)
        if (hasSingleDigits(setting.joinToString(separator = ""))) {

            var output : Queue<Int> = LinkedList()
            output.add(0)
            for (item in setting) {
                val copy = instruction.toMutableList()
                val input = LinkedList<Int>()
                input.add(item)
                input.add(output.last())
                output.clear()
                var ip = 0
                program(Program(copy, input, output, ip))
            }
            if (output.last() > best) {
                best = output.last()
            }
        }
    }
    return best
}

fun partTwo(instruction : MutableList<Int>) : Int {
    var best = 0
    for (i in 0 until 5*5*5*5*5) {
        val setting = int2List(i).map { it + 5 }
        if (hasSingleDigits(setting.joinToString(separator = ""))) {
            val queues = setting.map { LinkedList<Int>() }
            val ips = setting.map { 0 }
            setting.mapIndexed {index, item  ->
                queues.elementAt(index).add(item)
            }
            queues.first().add(0)
            val programs = setting.mapIndexed { index, item -> Program(instruction.toMutableList(), queues[index], queues[(index + 1) % queues.size], ips[index]) }

            while (programs.map { !program(it) }.any{it}) {}
            if (queues.first().last() > best) {
                best = queues.first().last()
            }
        }
    }
    return best
}

data class Program(val instructions: MutableList<Int>, val input: Queue<Int>, val output : Queue<Int>, var ip : Int)

fun hasSingleDigits(value : String) : Boolean{
    return value.toCharArray().groupBy { it }.values.all { it.size == 1}
}

fun int2List(value : Int) : List<Int> {
    val toReturn = mutableListOf(0,0,0,0,0)
    var value2 = value
    var i = 4
    while (value2 > 0) {
        toReturn[i] = value2%5
        value2 /= 5
        i--
    }
    return toReturn
}

fun program(program : Program) : Boolean {
    while (program.instructions[program.ip] != 99) {
        val instruction = "%05d".format(program.instructions[program.ip])
        when (instruction.substring(3, 5).toInt()) {
            1 -> { //addition
                program.instructions[program.instructions[program.ip + 3]] = getValue(instruction[2], program.instructions, program.ip, 1) + getValue(instruction[1], program.instructions, program.ip, 2)
                program.ip += 4
            }
            2 -> { //multiply
                program.instructions[program.instructions[program.ip + 3]] = getValue(instruction[2], program.instructions, program.ip, 1) * getValue(instruction[1], program.instructions, program.ip, 2)
                program.ip += 4
            }
            3 -> { //read
                if (program.input.isNotEmpty()) {
                    program.instructions[program.instructions[program.ip + 1]] = program.input.remove()
                    program.ip += 2

                } else {
                    return false
                }
            }
            4 -> { //write
                val value = getValue(instruction[2], program.instructions, program.ip, 1)
                program.output.add(value)
                program.ip += 2
            }
            5 -> { //jump-if-true
                val value = getValue(instruction[2], program.instructions, program.ip, 1)
                if (value != 0) {
                    program.ip = getValue(instruction[1], program.instructions, program.ip, 2)
                } else {
                    program.ip += 3
                }
            }
            6 -> { //jump-if-false
                val value = getValue(instruction[2], program.instructions, program.ip, 1)
                if (value == 0) {
                    program.ip = getValue(instruction[1], program.instructions, program.ip, 2)
                } else {
                    program.ip += 3
                }
            }
            7 -> { //less than
                val value1 = getValue(instruction[2], program.instructions, program.ip, 1)
                val value2 = getValue(instruction[1], program.instructions, program.ip, 2)
                if (value1 < value2) {
                    program.instructions[program.instructions[program.ip + 3]] = 1
                } else {
                    program.instructions[program.instructions[program.ip + 3]] = 0
                }
                program.ip += 4
            }
            8 -> { //equals
                val value1 = getValue(instruction[2], program.instructions, program.ip, 1)
                val value2 = getValue(instruction[1], program.instructions, program.ip, 2)
                if (value1 == value2) {
                    program.instructions[program.instructions[program.ip + 3]] = 1
                } else {
                    program.instructions[program.instructions[program.ip + 3]] = 0
                }
                program.ip += 4
            }
            else -> {
                print("Error")
            }
        }
    }
    return true
}

fun getValue(mode : Char, instructions : MutableList<Int>, ip : Int, offset : Int) : Int {
    return if (mode == '0') {
        instructions[instructions[ip + offset]]
    } else {
        instructions[ip + offset]
    }
}