import util.NumberGridBfs

fun main() {
  val day = 12
  println(Day12(readTest(day)).part1())
//  println(Day12(readTest(day)).part2())

  println("Answers:")
  println("Part 1=" + Day12(readInput(day)).part1())
  println("Part 2=" + Day12(readInput(day)).part2())
}

class Day12(private val input: List<String>) {
  private var starts: MutableList<Pair<Int, Int>> = mutableListOf()
  private var end: Pair<Int, Int> = -1 to -1

  fun part1(): Int {
    val bfs = NumberGridBfs(parseGrid(listOf('S')), starts, end)
    val stepCount = bfs.shortest()
    bfs.printGridWithPathAsChar(bfs.getShortestPath())
    return stepCount
  }

  fun part2(): Int {
    val bfs = NumberGridBfs(parseGrid(listOf('S', 'a')), starts, end)
    val stepCount = bfs.shortest()
    bfs.printGridWithPathAsChar(bfs.getShortestPath())
    return stepCount
  }

  private fun parseGrid(starChars: List<Char>): List<List<Int>> {
    return input.charGrid().mapIndexed { rowIndex, r ->
      r.mapIndexed { columnIndex, c ->
        when (c) {
          in starChars -> addStart(rowIndex, columnIndex)
          'E' -> addEnd(rowIndex, columnIndex)
          else -> c - 'a' + 1
        }
      }
    }
  }

  private fun addEnd(rowIndex: Int, columnIndex: Int): Int {
    if (end != -1 to -1)
      throw Exception("Only 1 end possible")
    end = rowIndex to columnIndex
    return 26
  }

  private fun addStart(rowIndex: Int, columnIndex: Int): Int {
    starts.add(rowIndex to columnIndex)
    return 1
  }
}

