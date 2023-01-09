import kotlinx.serialization.*
import kotlinx.serialization.json.*

fun main() {
  val day = 13
  println(Day13(readTest(day)).part1())
  println(Day13(readTest(day)).part2())

  println("Answers:")
  println("Part 1=" + Day13(readInput(day)).part1())
  println("Part 2=" + Day13(readInput(day)).part2())
}

class Day13(private val input: List<String>) {
  fun part1(): Int {
    return input.splitOn { it.isBlank() }.mapIndexed { i, (left, right) ->
      val leftPackage = Json.decodeFromString<JsonArray>(left)
      val rightPackage = Json.decodeFromString<JsonArray>(right)
      println("Pair $i: $leftPackage : $rightPackage ")
      if (compare(leftPackage, rightPackage) == -1) i + 1 else 0
    }.sum()
  }

  fun part2(): Int {
    val dividers = listOf(JsonArray(listOf(JsonPrimitive(2))), JsonArray(listOf(JsonPrimitive(6))))
    return input.asSequence().filter { it.isNotBlank() }.map<String, JsonArray> { Json.decodeFromString(it) }
      .plus(dividers)
      .sortedWith { a, b -> compare(a, b) }
      .mapIndexed { index, packet -> if (packet in dividers) index + 1 else 1 }
      .reduce { div1, div2 -> div1 * div2 }
  }

  private fun compare(leftPackage: JsonElement, rightPackage: JsonElement): Int {
    return if (bothAreItems(leftPackage, rightPackage))
      compareItems(leftPackage as JsonPrimitive, rightPackage as JsonPrimitive)
    else if (bothAreArrays(leftPackage, rightPackage))
      compareArrays(leftPackage as JsonArray, rightPackage as JsonArray)
    else if (leftPackage is JsonPrimitive && rightPackage is JsonArray)
      compare(JsonArray(listOf(leftPackage)), rightPackage)
    else
      compare(leftPackage, JsonArray((listOf(rightPackage))))
  }

  private fun compareArrays(leftPackage: JsonArray, rightPackage: JsonArray): Int {
    var i = 0
    while (i < leftPackage.size && i < rightPackage.size) {
      val itemComparison = compare(leftPackage[i], rightPackage[i])
      if (itemComparison != 0) return itemComparison
      i++
    }
    return if (i == leftPackage.size && i < rightPackage.size) -1
    else if (i < leftPackage.size && i == rightPackage.size) 1
    else 0
  }

  private fun compareItems(leftPackage: JsonPrimitive, rightPackage: JsonPrimitive): Int {
    return if (leftPackage.int < rightPackage.int) -1
    else if (leftPackage.int == rightPackage.int) 0
    else 1
  }

  private fun bothAreItems(leftPackage: JsonElement, rightPackage: JsonElement) =
    leftPackage is JsonPrimitive && rightPackage is JsonPrimitive

  private fun bothAreArrays(leftPackage: JsonElement, rightPackage: JsonElement) =
    leftPackage is JsonArray && rightPackage is JsonArray
}