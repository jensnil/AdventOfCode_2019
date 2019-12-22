package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day09KtTest {

    @Test
    fun partOneTest() {
        val answer = day09.partOne(parse(getInput("day09.txt")))

        println(answer)
        assert(answer == 2350741403L)
    }

    @Test
    fun partTwoTest() {
        val answer = day09.partTwo(parse(getInput("day09.txt")))

        println(answer)
        assert(answer == 53088L)
    }

    private fun parse(lines : List<String>) : MutableList<Long> = lines.first().split(",").map { it.toLong() }.toMutableList()
}