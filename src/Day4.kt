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
    return getSectionsByElf().count { (firstRangeSections, lastRangeSections) ->
      firstRangeSections.containsAll(lastRangeSections) || lastRangeSections.containsAll(firstRangeSections)
    }
  }

  fun part2(): Int {
    return getSectionsByElf().count { (firstRangeSections, lastRangeSections) ->
      firstRangeSections.intersect(lastRangeSections).isNotEmpty()
    }
  }

  private fun getSectionsByElf() = input.map { line ->
    line.split(",").map {
      val bounds = it.split("-").map { bound -> bound.toInt() }
      bounds[0].rangeTo(bounds[1]).toSet()
    }
  }
}

