package util

import getAtCoordinate
import plus

class NumberGridBfs(
  private val grid: List<List<Int>>,
  private val start: List<Pair<Int, Int>>,
  private val end: Pair<Int, Int>,
  private val stepDelta: Int = 1,
) {
  private val up = -1 to 0
  private val down = 1 to 0
  private val right = 0 to 1
  private val left = 0 to -1
  private val directions = listOf(up, right, down, left)
  private lateinit var deque: ArrayDeque<Pair<Pair<Int, Int>, Int>>
  private val nodes = mutableSetOf<Node>()

  init {
    initQueue()
  }

  private fun initQueue() {
    deque = ArrayDeque()
    start.forEach {
      deque.addLast(it to 0)
      nodes.add(Node(it))
    }
  }

  data class Node(
    val actual: Pair<Int, Int>,
    val previous: Node? = null
  ) {
    var step: Int = 0

    init {
      if (previous != null)
        step = previous.step + 1
    }
  }

  fun shortest(): Int {
    initQueue()
    val visited = mutableSetOf<Pair<Int, Int>>()
    while (deque.isNotEmpty()) {
      val stepWithValue = deque.removeFirst()
      val coordinate = stepWithValue.first
      if (coordinate in visited) continue else visited.add(coordinate)
      if (coordinate == end) return stepWithValue.second
      for (direction in directions) {
        val directedCoordinate = coordinate + direction
        if (isValidCoordinate(directedCoordinate)) addIfValidStep(
          directedCoordinate,
          coordinate,
          stepWithValue.second
        )
      }
    }
    return -1
  }

  fun getShortestPath(): MutableList<Pair<Int, Int>> {
    return mutableListOf<Pair<Int, Int>>().apply {
      val lastNode = nodes.first { it.actual == end }
      add(lastNode.actual)
      var previousNode = lastNode.previous
      while (previousNode != null) {
        add(previousNode.actual)
        previousNode = previousNode.previous
      }
    }
  }

  fun printGridWithPathAsChar(shortestPath: MutableList<Pair<Int, Int>>) {
    println("Grid with path:")
    grid.map { it.map { ('a' - 1) + it } }.forEachIndexed { rowIndex, row ->
      row.forEachIndexed { columnIndex, char ->
        val coordinate = rowIndex to columnIndex
        if (coordinate in shortestPath)
          print(
            when (coordinate) {
              in start -> " S "
              end -> " E "
              else -> " $char "
            }
          )
        else print(" . ")
      }
      println()
    }
  }


  private fun addIfValidStep(
    directedCoordinate: Pair<Int, Int>, coordinate: Pair<Int, Int>, stepCount: Int
  ) {
    val directedValue = grid.getAtCoordinate(directedCoordinate)
    val currentValue = grid.getAtCoordinate(coordinate)
    if (directedValue <= (stepDelta + currentValue)) {
      addNodeForStep(directedCoordinate, coordinate)
      deque.addLast(directedCoordinate to stepCount + 1)
    }
  }

  private fun addNodeForStep(
    actualCoordinate: Pair<Int, Int>,
    previousCoordinate: Pair<Int, Int>
  ) {
    val previous = nodes.find { it.actual == previousCoordinate }
    nodes.add(Node(actualCoordinate, previous))
  }

  private fun isValidCoordinate(coordinate: Pair<Int, Int>) =
    isValidRow(grid, coordinate.first) && isValidColumn(grid, coordinate.second)

  private fun isValidRow(grid: List<List<Int>>, directedColumn: Int) = (grid.indices).contains(directedColumn)

  private fun isValidColumn(grid: List<List<Int>>, directedColumn: Int) =
    (0 until grid.first().size).contains(directedColumn)
}
