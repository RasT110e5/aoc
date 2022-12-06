fun main() {
  val day = 6
  println(Day6(readTest(day)).part1())
  println(Day6(readTest(day)).part2())

  println("Answers:")
  println("Part 1=" + Day6(readInput(day)).part1())
  println("Part 2=" + Day6(readInput(day)).part2())
}

class Day6(private val input: List<String>) {
  fun part1(): Int {
    input[0].forEachIndexed { index, _ ->
      if (checkIndexForProtocolKey(index, 4)) return index + 4
    }
    return -1
  }

  fun part2(): Int {
    input[0].forEachIndexed { index, _ ->
      if (checkIndexForProtocolKey(index, 14)) return index + 14
    }
    return -1
  }

  private fun checkIndexForProtocolKey(index: Int, sizeOfKey: Int): Boolean {
    return input[0].subSequence(index, index + sizeOfKey).toSet().size == sizeOfKey
  }
}

