package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day11KtTest {

    @Test
    fun partOneTest() {
        val answer = day11.partOne(parse(getInput("day11.txt")))

        println(answer)
        assert(answer == 1681)
    }

    @Test
    fun partTwoTest() {
        val answer = day11.partTwo(parse(getInput("day11.txt")))

        println(answer)
    }

    private fun parse(lines : List<String>) : MutableList<Long> = lines.first().split(",").map { it.toLong() }.toMutableList()
}