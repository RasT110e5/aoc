fun main() {
  val day = 1
  println(Day1(readTest(day)).part1())
  println(Day1(readTest(day)).part2())

  println("Answers:")
  println("Part 1=" + Day1(readPart1(day)).part1())
  println("Part 2=" + Day1(readPart2(day)).part2())
}

class Day1(private val input: List<String>) {
  fun part1(): Int {
    return input.size
  }

  fun part2(): Int {
    return input.size
  }
}

