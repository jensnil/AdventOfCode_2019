package unittests

import lib.circular
import lib.getInput
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class Day01KtTest {

    @Test
    fun partOneTest() {
        val answer = day01.partOne(parse(getInput("day01.txt")))
        println(answer)
        assert(answer == 3394689)
    }

    @Test
    fun partTwoTest() {
        val answer = day01.partTwo(parse(getInput("day01.txt")))
        println(answer)
        assert(answer== 5089160)
    }

    private fun parse(lines : List<String>) : List<Int> = lines.map { it.toInt() }
}