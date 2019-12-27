package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day12KtTest {

    @Test
    fun partOneTest() {
        val answer = day12.partOne(parse(getInput("day12.txt")))

        println(answer)
        assert(answer == 6423)
    }

    @Test
    fun partTwoTest() {
        val answer = day12.partTwo(parse(getInput("day12.txt")))

        println(answer)
        assert(answer == 327636285682704L)
    }

    private fun parse(lines : List<String>) : List<MutableList<Int>> = lines.map {
        val regex = """^<x=(-?\d+), y=(-?\d+), z=(-?\d+)>$""".toRegex()
        val matchResult = regex.find(it)
        mutableListOf(matchResult!!.groupValues[1].toInt(), matchResult!!.groupValues[2].toInt(), matchResult!!.groupValues[3].toInt())
    }
}