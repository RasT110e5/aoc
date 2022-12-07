import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

private val regexPosInt = Regex("""\d+""")
private fun readInput(path: String) = File(path)
  .readLines()

fun readTest(day: Int) = readInput(DayConfigurer(day).testFile())
fun readInput(day: Int) = readInput(DayConfigurer(day).part1())
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
  .toString(16)
  .padStart(32, '0')

fun String.numbers(): List<Int> = regexPosInt.findAll(this).map { it.value.toInt() }.toList()
fun String.toNumbersWithin(): List<Int> = numbers()
fun Iterable<String>.toNumbersWithin(): List<List<Int>> = map { it.toNumbersWithin() }
fun <T> T.log(): T = this.also { println(it) }
fun <T> T.log(msg: String): T = this.also { println("$msg $it") }
