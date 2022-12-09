fun main() {
  val day = 8
  println(Day8(readTest(day)).part1())
  println(Day8(readTest(day)).part2())

  println("Answers:")
  println("Part 1=" + Day8(readInput(day)).part1())
  println("Part 2=" + Day8(readInput(day)).part2())
}

class Day8(private val input: List<String>) {

  fun part1(): Int {
    val treesGrid = input.numberGrid()
    val visibleLeft = treesGrid.map { visibleTreesToTheRight(it) }
    val visibleRight = treesGrid.map { visibleTreesToTheLeft(it) }
    val transposedTreesGrid = treesGrid.transpose()
    val visibleTop = transposedTreesGrid.map { visibleTreesToTheRight(it) }.transpose()
    val visibleBottom = transposedTreesGrid.map { visibleTreesToTheLeft(it) }.transpose()

    return treesGrid.mapIndexed { heightIndex, treeRow ->
      treeRow.filterIndexed { widthIndex, _ ->
        (visibleLeft[heightIndex][widthIndex] || visibleRight[heightIndex][widthIndex]
                || visibleTop[heightIndex][widthIndex] || visibleBottom[heightIndex][widthIndex])
      }
    }.sumOf { it.size }
  }

  fun part2(): Int {
    val treesGrid = input.numberGrid()
    val transposedTreeGrid = treesGrid.transpose()
    val maxScoreByRow = treesGrid.mapIndexed { j, treeRow ->
      treeRow.mapIndexed { i, tree ->
        val rightViewScore = viewingDistanceForTreeWith(tree, treesToTheRightOfIndex(treeRow, i))
        val leftViewScore = viewingDistanceForTreeWith(tree, treesToTheLeftOfIndex(treeRow, i))
        val topViewScore = viewingDistanceForTreeWith(tree, treesToTheRightOfIndex(transposedTreeGrid[i], j))
        val downViewScore = viewingDistanceForTreeWith(tree, treesToTheLeftOfIndex(transposedTreeGrid[i], j))
        leftViewScore*rightViewScore*topViewScore*downViewScore
      }.max()
    }

    return maxScoreByRow.max()
  }
  private fun visibleTreesToTheLeft(it: List<Int>) = visibleTreesToTheRight(it.reversed()).reversed()

  private fun viewingDistanceForTreeWith(height: Int, row: List<Int>): Int {
    val firstTreeBlockingView = row.indexOfFirst { it >= height }
    return if (firstTreeBlockingView == -1) {
      row.size
    } else {
      firstTreeBlockingView + 1
    }
  }

  private fun visibleTreesToTheRight(ints: List<Int>): List<Boolean> {
    var actualHighest = -1
    return ints.map {
      if (it > actualHighest) {
        actualHighest = it
        true
      } else {
        false
      }
    }
  }

  private fun treesToTheLeftOfIndex(treeRow: List<Int>, i: Int) = treeRow.take(i).reversed()

  private fun treesToTheRightOfIndex(treeRow: List<Int>, index: Int) = treeRow.drop(index + 1)
}

