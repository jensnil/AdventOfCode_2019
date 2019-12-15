package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day02KtTest {

    @Test
    fun partOneTest() {
        val answer = day02.partOne(parse(getInput("day02.txt")))
        println(answer)
        assert(answer == 12490719)
    }

    @Test
    fun partTwoTest() {
        val answer = day02.partTwo(parse(getInput("day02.txt")), 19690720)
        println(answer)
        assert(answer== 2003)
    }

    private fun parse(lines : List<String>) : MutableList<Int> = lines.first().split(",").map { it.toInt() }.toMutableList()
}