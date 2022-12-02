import java.lang.IllegalArgumentException

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
    var score = 0
    for (round in input) {
      val selections = round.split(" ")
      val opponentPlay = Selection.valueOf(selections[0])
      val yourPlay = Selection.byStrategy(selections[1].single())
      score += yourPlay.value
      if (yourPlay.winsAgainst(opponentPlay)) score += 6
      else if (isDraw(yourPlay, opponentPlay)) score += 3
    }
    return score
  }

  private fun isDraw(yourPlay: Selection, opponentPlay: Selection) = yourPlay == opponentPlay

  fun part2(): Int {
    var score = 0
    for (round in input) {
      val selections = round.split(" ")
      val opponentPlay = Selection.valueOf(selections[0])
      when (selections[1].single()) {
        'X' -> score += opponentPlay.beats().value
        'Y' -> score += opponentPlay.value + 3
        'Z' -> score += opponentPlay.losses().value + 6
      }
    }
    return score
  }
}

enum class Selection(val value: Int) {
  A(1) {
    override fun winsAgainst(opponentPlay: Selection): Boolean = opponentPlay == beats()
    override fun beats(): Selection = C
    override fun losses(): Selection = B
  },
  B(2) {
    override fun winsAgainst(opponentPlay: Selection): Boolean = opponentPlay == beats()
    override fun beats(): Selection = A
    override fun losses(): Selection = C
  },
  C(3) {
    override fun winsAgainst(opponentPlay: Selection): Boolean = opponentPlay == beats()
    override fun beats(): Selection = B
    override fun losses(): Selection = A
  };

  abstract fun winsAgainst(opponentPlay: Selection): Boolean
  abstract fun beats(): Selection
  abstract fun losses(): Selection

  companion object {
    fun byStrategy(strategySelection: Char): Selection {
      return when (strategySelection) {
        'X' -> A
        'Y' -> B
        'Z' -> C
        else -> throw IllegalArgumentException()
      }
    }
  }
}

