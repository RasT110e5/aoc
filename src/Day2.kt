fun main() {
  val day = 2
  println(Day2(readTest(day)).part1())
  println(Day2(readTest(day)).part2())

  println("Answers:")
  println("Part 1=" + Day2(readPart1(day)).part1())
  println("Part 2=" + Day2(readPart2(day)).part2())
}

class Day2(private val input: List<String>) {
  fun part1(): Int {
    return input.size
  }

  fun part2(): Int {
    return input.size
  }
}

