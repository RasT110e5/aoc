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
    return getParsedInput().sumOf {
      val opponent = Selection.valueOf(it[0].toString())
      val yours = Selection.byStrategy(it[1])
      if (opponent.winsAgainst(yours))
        yours.value + 6
      else if (isDraw(yours, opponent))
        yours.value + 3
      else
        yours.value
    }
  }

  fun part2(): Int {
    return getParsedInput().sumOf {
      val opponentPlay = Selection.valueOf(it[0].toString())
      when (it[1]) {
        'X' -> opponentPlay.beats().value
        'Y' -> opponentPlay.value + 3
        'Z' -> opponentPlay.losses().value + 6
        else -> 0
      }
    }
  }

  private fun getParsedInput() = input.map {
    it.split(" ").map { selection -> selection.single() }
  }

  private fun isDraw(yourPlay: Selection, opponentPlay: Selection) = yourPlay == opponentPlay
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

