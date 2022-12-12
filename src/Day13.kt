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
    return input.size
  }

  fun part2(): Int {
    return input.size
  }
}

