package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day08KtTest {

    @Test
    fun partOneTest() {
        val answer = day08.partOne(parse(getInput("day08.txt")))

        println(answer)
        assert(answer == 2375)
    }

    @Test
    fun partTwoTest() {
        val answer = day08.partTwo(parse(getInput("day08.txt")))

        println(answer)
//        assert(answer== 19581200)
    }

    private fun parse(lines : List<String>) : List<Int> = lines.first().map { it.toString().toInt() }
}