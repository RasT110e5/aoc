import kotlin.math.abs

fun main() {
  val day = 9
  println(Day9(readTest(day)).part1())
  println(Day9(readTest(day)).part2())

  println("Answers:")
  println("Part 1=" + Day9(readInput(day)).part1())
  println("Part 2=" + Day9(readInput(day)).part2())
}

class Day9(private val input: List<String>) {
  private val offsetOnRow = hashMapOf('R' to 0, 'L' to 0, 'U' to -1, 'D' to 1)
  private val offsetOnColumn = hashMapOf('R' to 1, 'L' to -1, 'U' to 0, 'D' to 0)

  fun part1(): Int {
    var tail = Pair(0, 0)
    var head = Pair(0, 0)
    val visited = mutableSetOf<Pair<Int, Int>>()
    input.map { it.betweenSpaces() }
      .map { it[0].first() to it[1].toInt() }
      .forEach { (dir, count) ->
        repeat(count) {
          head = moveHead(head, dir)
          tail = MoveAdjuster(head, tail).follow()
          visited.add(tail)
        }
      }
    return visited.size
  }

  private fun moveHead(head: Pair<Int, Int>, dir: Char) =
    head.first + offsetOnRow.getValue(dir) to head.second + offsetOnColumn.getValue(dir)

  fun part2(): Int {
    val tails = mutableListOf<Pair<Int, Int>>().apply { for (i in 1..9) this.add(0 to 0) }
    var head = Pair(0, 0)
    val visited = mutableSetOf<Pair<Int, Int>>()
    input.map { it.betweenSpaces() }
      .map { it[0].first() to it[1].toInt() }
      .forEach { (dir, count) ->
        repeat(count) {
          head = moveHead(head, dir)
          tails[0] = MoveAdjuster(head, tails[0]).follow()
          for (i in 1..8)
            tails[i] = MoveAdjuster(tails[i - 1], tails[i]).follow()
          visited.add(tails[8])
        }
      }
    return visited.size
  }
}

class MoveAdjuster(private val knot: Pair<Int, Int>, private val followerKnot: Pair<Int, Int>) {
  private val columnOffset: Int = if (followerKnot.second < knot.second) -1 else 1
  private val rowOffset: Int = if (followerKnot.first < knot.first) -1 else 1
  private val deltaOnColumn: Int = knot.second - followerKnot.second
  private val deltaOnRow: Int = knot.first - followerKnot.first

  fun follow(): Pair<Int, Int> {
    return if (verticalDistanceIsAtLeast2() && horizontalDistanceIsAtLeast2())
      knot.first + rowOffset to knot.second + columnOffset
    else if (verticalDistanceIsAtLeast2()) knot.first + rowOffset to knot.second
    else if (horizontalDistanceIsAtLeast2()) knot.first to knot.second + columnOffset
    else followerKnot
  }

  private fun horizontalDistanceIsAtLeast2() = abs(deltaOnColumn) >= 2

  private fun verticalDistanceIsAtLeast2() = abs(deltaOnRow) >= 2
}


