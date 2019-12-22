package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day10KtTest {

    @Test
    fun partOneTest() {
        val answer = day10.partOne(parse(getInput("day10.txt")))
        println(answer)
        assert(answer == 314)
    }

    @Test
    fun partTwoTest() {
        val answer = day10.partTwo(parse(getInput("day10.txt")))

        println(answer)
        assert(answer == 1513)
    }

    private fun parse(lines : List<String>) : Set<Pair<Int,Int>> = lines.mapIndexed { line, s ->
        s.mapIndexed { column, c -> if (c == '#') Pair(column, line) else null}.filterNotNull()
    }.flatten().toSet()
}


//910 too low