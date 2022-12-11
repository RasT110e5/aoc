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

fun String.betweenSpaces() = this.split(" ").filter { it.isNotBlank() }
fun String.numbers(): List<Int> = regexPosInt.findAll(this).map { it.value.toInt() }.toList()
fun String.numbersAsLong(): List<Long> = this.numbers().map { it.toLong() }
fun String.toNumbersWithin(): List<Int> = numbers()
fun Iterable<String>.toNumbersWithin(): List<List<Int>> = map { it.toNumbersWithin() }
private fun String.charGrid() = this.map { it }
fun Iterable<String>.charGrid(): List<List<Char>> = map { it.charGrid() }
private fun String.numberGrid(): List<Int> = this.map { it.code }
fun Iterable<String>.numberGrid(): List<List<Int>> = map { it.numberGrid() }

fun <T> List<List<T>>.transpose(): List<List<T>> =
  List(this[0].size) { rowIndex -> List(this.size) { columnIndex -> this[columnIndex][rowIndex] } }

fun Iterable<String>.splitOnEmpty(): List<List<String>> = this.splitOn { it.isEmpty() }

inline fun <T> Iterable<T>.splitOn(predicate: (T) -> Boolean): List<List<T>> {
  val otherThis = this
  return mutableListOf<List<T>>().apply {
    var newNode = mutableListOf<T>()
    for (i in otherThis) if (predicate(i)) {
      this += newNode
      newNode = mutableListOf()
    } else newNode.add(i)
    this += newNode
  }
}




