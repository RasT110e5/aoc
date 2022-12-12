package util

import getEvenWithNegative
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

  init {
    initQueue()
  }

  private fun initQueue() {
    deque = ArrayDeque()
    start.forEach { deque.addLast(it to 0) }
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
        if (isValidCoordinate(directedCoordinate)) addIfValidStep(directedCoordinate, coordinate, stepWithValue.second)
      }
    }
    return -1
  }

  private fun addIfValidStep(
    directedCoordinate: Pair<Int, Int>, coordinate: Pair<Int, Int>, stepCount: Int
  ) {
    val directedValue = grid.getEvenWithNegative(directedCoordinate)
    val currentValue = grid.getEvenWithNegative(coordinate)
    if (directedValue <= (stepDelta + currentValue)) deque.addLast(directedCoordinate to stepCount + 1)
  }

  private fun isValidCoordinate(coordinate: Pair<Int, Int>) =
    isValidRow(grid, coordinate.first) && isValidColumn(grid, coordinate.second)

  private fun isValidRow(grid: List<List<Int>>, directedColumn: Int) = (grid.indices).contains(directedColumn)

  private fun isValidColumn(grid: List<List<Int>>, directedColumn: Int) =
    (0 until grid.first().size).contains(directedColumn)
}
