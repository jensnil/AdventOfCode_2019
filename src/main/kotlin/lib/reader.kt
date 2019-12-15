package lib

import java.io.File

fun getInput(fileName : String) : List<String> = File("input/$fileName").bufferedReader().readLines()