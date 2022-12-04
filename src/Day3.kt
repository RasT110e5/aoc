fun main() {
  val day = 3
  println(Day3(readTest(day)).part1())
  println(Day3(readTest(day)).part2())

  println("Answers:")
  println("Part 1=" + Day3(readInput(day)).part1())
  println("Part 2=" + Day3(readInput(day)).part2())
}

class Day3(private val input: List<String>) {
  fun part1(): Int {
    val rucksacksByHalves = getItemsInRucksacks()
      .map { it.chunked(it.size / 2) }
    return rucksacksByHalves.sumOf { (firstHalf, secondHalf) ->
      val repeatedItemBetweenHalves = firstHalf.toSet()
        .intersect(secondHalf.toSet())
        .first()
      priorityValue(repeatedItemBetweenHalves)
    }
  }

  fun part2(): Int {
    val first3Rucksacks = getItemsInRucksacks().chunked(3)
    return first3Rucksacks.sumOf { (first, second, third) ->
      val itemInAll3Sacks = first.toSet()
        .intersect(second.toSet())
        .intersect(third.toSet())
        .first()
      priorityValue(itemInAll3Sacks)
    }
  }

  private fun getItemsInRucksacks() = input.map { rucksack -> rucksack.map { item -> item } }

  //a-z = 1-26 ^ A-Z = 27-52
  private fun priorityValue(item: Char) = if (item.isLowerCase())
    normalizeToLowercasePriority(item)
  else
    normalizeToUppercasePriority(item)

  private fun normalizeToUppercasePriority(item: Char): Int = (item - 38).code

  private fun normalizeToLowercasePriority(item: Char): Int = (item - 96).code
}