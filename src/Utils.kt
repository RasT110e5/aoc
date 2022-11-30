import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
private fun readInput(path: String) = File(path)
    .readLines()

fun readTest(day:Int) = readInput(DayConfigurer(day).testFile())
fun readPart1(day:Int) = readInput(DayConfigurer(day).part1())
fun readPart2(day:Int) = readInput(DayConfigurer(day).part2())

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
