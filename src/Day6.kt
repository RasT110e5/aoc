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
    return checkForProtocolMarkerWithSize(4)
  }

  fun part2(): Int {
    return checkForProtocolMarkerWithSize(14)
  }

  private fun checkForProtocolMarkerWithSize(sizeOfKey: Int): Int {
    return input[0].windowed(sizeOfKey).indexOfFirst { it.toSet().size == sizeOfKey } + sizeOfKey
  }
}

