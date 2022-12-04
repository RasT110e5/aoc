fun main() {
  val day = 4
  println(Day4(readTest(day)).part1())
  println(Day4(readTest(day)).part2())

  println("Answers:")
  println("Part 1=" + Day4(readInput(day)).part1())
  println("Part 2=" + Day4(readInput(day)).part2())
}

class Day4(private val input: List<String>) {
  fun part1(): Int {
    return getSectionsByElf().count {
      it[0].containsAll(it[1]) || it[1].containsAll(it[0])
    }
  }

  fun part2(): Int {
    return getSectionsByElf().count {
      it[0].intersect(it[1]).isNotEmpty()
    }
  }

  private fun getSectionsByElf() = input.map { line ->
    line.split(",").map {
      val bounds = it.split("-")
      bounds[0].toInt().rangeTo(bounds[1].toInt()).toSet()
    }
  }
}

