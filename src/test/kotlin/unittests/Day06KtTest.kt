package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day06KtTest {

    @Test
    fun partOneTest() {
        val answer = day06.partOne(parse(getInput("day06.txt")))

        println(answer)
        assert(answer == 117672)
    }

    @Test
    fun partTwoTest() {
        val answer = day06.partTwo(parse(getInput("day06.txt")))
        println(answer)
        assert(answer== 277)
    }

    private fun parse(lines : List<String>) : Map<String,String> = lines.map { line ->
        val split = line.split(")")
        split[1] to split[0]
    }.toMap()
}