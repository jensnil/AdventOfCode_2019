package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day14KtTest {

    @Test
    fun partOneTest() {
        val answer = day14.partOne(parse(getInput("day14.txt")))

        println(answer)
        assert(answer == 892207)
    }

    @Test
    fun partTwoTest() {
        val answer = day14.partTwo(parse(getInput("day12.txt")))

        println(answer)
//        assert(answer == 327636285682704L)
    }

    private fun parse(lines : List<String>) : Map<String, Pair<Int, List<Pair<Int,String>>>> = lines.map {
        val regex = """^(\d+) (\w+)(, (\d+) (\w+))* => (\d+) (\w+)$""".toRegex()
        val matchResult = regex.find(it)

        val (from, to) = it.split("=>")
        val splittedFrom = from.split(",")
        to.trim().split(" ")[1] to
                Pair(to.trim().split(" ")[0].toInt(),
                        splittedFrom.map { Pair(it.trim().split(" ")[0].toInt(), it.trim().split(" ")[1]) })

    }.toMap()
}
