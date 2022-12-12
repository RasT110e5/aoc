fun main() {
  val day = 12
//  println(Day12Dijkstra(readTest(day)).part1())
  println(Day12Dijkstra(readTest(day)).part2())

  println("Answers:")
//  println("Part 1=" + Day12Dijkstra(readInput(day)).part1())
//  println("Part 2=" + Day12Dijkstra(readInput(day)).part2())
}

class Day12Dijkstra(private val input: List<String>) {
  private val grid = input.charGrid()
  private var starts: MutableList<Pair<Int, Int>> = mutableListOf()
  private var end: Pair<Int, Int> = -1 to -1
  private var weights = mutableMapOf<Pair<Pair<Int, Int>, Pair<Int, Int>>, Int>()

  fun part2(): Int {
    parseGrid()
    val graph = Graph(weights)
    val pathTree = dijkstra(graph, starts[0])
    val shortestPath = shortestPath(pathTree, starts[0], end)
    printPath(shortestPath)
    return shortestPath.size - 1
  }

  private fun printPath(shortestPath: List<Pair<Int, Int>>) {
    println("Grid with path:")
    grid.forEachIndexed { rowIndex, row ->
      row.forEachIndexed { columnIndex, char ->
        print(if (rowIndex to columnIndex in shortestPath) " $char " else " . ")
      }
      println()
    }
  }

  fun part1(): Int {
    return 0
  }

  private fun parseGrid() {
    grid.mapIndexed { rowIndex, r ->
      List(r.size) { columnIndex ->
        val coordinate = rowIndex to columnIndex
        val char = parseForStartOrEnd(coordinate)
        for (direction in listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)) {
          val dirCoordinate = coordinate + direction
          if (grid.isValidCoordinate(dirCoordinate)) {
            val dirChar = parseForStartOrEnd(dirCoordinate)
            if (dirChar in listOf(char, char + 1))
              weights[coordinate to dirCoordinate] = 1
          }
        }
      }
    }
  }

  private fun parseForStartOrEnd(coordinate: Pair<Int, Int>): Char {
    var char = grid.getAtCoordinate(coordinate)
    if (char == 'S' || char == 'a') {
      starts.add(coordinate)
      char = 'a'
    } else if (char == 'E') {
      end = coordinate
      char = 'z'
    }
    return char
  }

}


data class Graph<T>(
  val vertices: Set<T>,
  val edges: Map<T, Set<T>>,
  val weights: Map<Pair<T, T>, Int>
) {
  constructor(weights: Map<Pair<T, T>, Int>) : this(
    vertices = weights.keys.toList().getUniqueValuesFromPairs(),
    edges = weights.keys
      .groupBy { it.first }
      .mapValues { it.value.getUniqueValuesFromPairs { x -> x !== it.key } }
      .withDefault { emptySet() },
    weights = weights
  )
}

fun <T> dijkstra(graph: Graph<T>, start: T): Map<T, T?> {
  val S: MutableSet<T> = mutableSetOf()
  val delta = graph.vertices.map { it to Int.MAX_VALUE }.toMap().toMutableMap()
  delta[start] = 0
  val previous: MutableMap<T, T?> = graph.vertices.map { it to null }.toMap().toMutableMap()
  while (S != graph.vertices) {
    val v: T = delta
      .filter { !S.contains(it.key) }
      .minBy { it.value }
      .key

    graph.edges.getValue(v).minus(S).forEach { neighbor ->
      val newPath = delta.getValue(v) + graph.weights.getValue(Pair(v, neighbor))

      if (newPath < delta.getValue(neighbor)) {
        delta[neighbor] = newPath
        previous[neighbor] = v
      }
    }

    S.add(v)
  }

  return previous.toMap()
}

fun <T> shortestPath(shortestPathTree: Map<T, T?>, start: T, end: T): List<T> {
  fun pathTo(start: T, end: T): List<T> {
    if (shortestPathTree[end] == null) return listOf(end)
    return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
  }

  return pathTo(start, end)
}