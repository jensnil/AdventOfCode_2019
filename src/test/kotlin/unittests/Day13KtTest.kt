package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day13KtTest {

    @Test
    fun partOneTest() {
        val answer = day13.partOne(parse(getInput("day13.txt")))

        println(answer)
        assert(answer == 301)
    }

    @Test
    fun partTwoTest() {
        val answer = day13.partTwo(parse(getInput("day13.txt")))

        println(answer)
        assert(answer == 14096)
    }

    private fun parse(lines : List<String>) : MutableList<Long> = lines.first().split(",").map { it.toLong() }.toMutableList()
}