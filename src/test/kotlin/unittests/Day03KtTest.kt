package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day03KtTest {

    @Test
    fun partOneTest() {
        val answer = day03.partOne(parse(getInput("day03.txt")))

        println(answer)
        assert(answer == 1983)
    }

    @Test
    fun partTwoTest() {
        val answer = day03.partTwo(parse(getInput("day03.txt")))

        println(answer)
        assert(answer== 107754)
    }

    private fun parse(lines : List<String>) = Pair(
            lines[0].split(",").map { Pair(it[0], it.substring(1).toInt()) },
            lines[1].split(",").map { Pair(it[0], it.substring(1).toInt()) }
    )
}