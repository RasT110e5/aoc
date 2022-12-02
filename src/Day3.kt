fun main() {
  val day = 1
  println(Day3(readTest(day)).part1())
  println(Day3(readTest(day)).part2())

  println("Answers:")
  println("Part 1=" + Day3(readInput(day)).part1())
  println("Part 2=" + Day3(readInput(day)).part2())
}

class Day3(private val input: List<String>) {
  fun part1(): Int {
    return input.size
  }

  fun part2(): Int {
    return input.size
  }
}

