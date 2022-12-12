import util.DayConfigurer
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

fun <E> List<List<E>>.getAtCoordinate(coordinate: Pair<Int, Int>): E {
  fun parseIndex(index: Int, maxBound: Int): Int {
    return if (index < 0)
      if (index * -1 > maxBound)
        throw Exception("Invalid negative index")
      else
        maxBound + index
    else
      index
  }

  val rows = this.size
  val columns = this.first().size
  val r = parseIndex(coordinate.first, rows)
  val c = parseIndex(coordinate.second, columns)
  return this[r][c]
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> =
  this.first + other.first to this.second + other.second

fun <T> List<Pair<T, T>>.getUniqueValuesFromPairs(): Set<T> = this
  .map { (a, b) -> listOf(a, b) }
  .flatten()
  .toSet()

fun <T> List<Pair<T, T>>.getUniqueValuesFromPairs(predicate: (T) -> Boolean): Set<T> = this
  .map { (a, b) -> listOf(a, b) }
  .flatten()
  .filter(predicate)
  .toSet()

fun <E> List<List<E>>.isValidCoordinate(coordinate: Pair<Int, Int>): Boolean =
  this.isValidRow(coordinate.first) && this.isValidColumn(coordinate.second)

fun <E> List<List<E>>.isValidRow(column: Int): Boolean = (this.indices).contains(column)

fun <E> List<List<E>>.isValidColumn(row: Int): Boolean = (0 until this.first().size).contains(row)

fun Pair<Int, Int>.isOrigin(): Boolean = this.first == 0 && this.second == 0
