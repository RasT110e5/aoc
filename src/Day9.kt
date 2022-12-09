fun main() {
  val day = 9
  println(Day9(readTest(day)).part1())
  println(Day9(readTest(day)).part2())

  println("Answers:")
  println("Part 1=" + Day9(readInput(day)).part1())
  println("Part 2=" + Day9(readInput(day)).part2())
}

class Day9(private val input: List<String>) {
  fun part1(): Int {
    return input.size
  }

  fun part2(): Int {
    return input.size
  }
}

