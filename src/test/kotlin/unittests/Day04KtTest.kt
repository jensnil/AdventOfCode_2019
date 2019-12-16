package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day04KtTest {

    @Test
    fun partOneTest() {
        val answer = day04.partOne(parse(getInput("day04.txt")))

        println(answer)
        assert(answer == 475)
    }

    @Test
    fun partTwoTest() {
        val answer = day04.partTwo(parse(getInput("day04.txt")))

        println(answer)
        assert(answer== 297)
    }

    private fun parse(lines : List<String>) : Pair<Int,Int> {
        val split = lines[0].split("-")
        return Pair(split[0].toInt(), split[1].toInt())
    }
}