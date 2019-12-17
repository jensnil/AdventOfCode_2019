package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day05KtTest {

    @Test
    fun partOneTest() {
        val answer = day05.partOne(parse(getInput("day05.txt")))

        println(answer)
        assert(answer == 9775037)
    }

    @Test
    fun partTwoTest() {
        val answer = day05.partTwo(parse(getInput("day05.txt")))

        println(answer)
        assert(answer== 15586959)
    }

    private fun parse(lines : List<String>) : MutableList<Int> = lines.first().split(",").map { it.toInt() }.toMutableList()
}