package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day07KtTest {

    @Test
    fun partOneTest() {
        val answer = day07.partOne(parse(getInput("day07.txt")))

        println(answer)
        assert(answer == 46014)
    }

    @Test
    fun partTwoTest() {
        val answer = day07.partTwo(parse(getInput("day07.txt")))

        println(answer)
        assert(answer== 19581200)
    }

    private fun parse(lines : List<String>) : MutableList<Int> = lines.first().split(",").map { it.toInt() }.toMutableList()
}