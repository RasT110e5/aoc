import kotlin.math.abs

fun main() {
  val day = 10
//  println(Day10(readTest(day)).part1())
//  println(Day10(readTest(day)).part2())

  println("Answers:")
  println("Part 1=" + Day10(readInput(day)).part1())
  println("Part 2=" + Day10(readInput(day)).part2())
}

class Day10(private val input: List<String>) {
  private val scoreCicles = mutableListOf<Int>().apply {
    var counter = 20
    for (i in 1..999) {
      add(counter)
      counter += 40
    }
  }

  fun part1(): Int {
    var register = 1
    var cycle = 0
    var score = 0
    input.forEach {
      if (it.startsWith("addx")) {
        for (i in 1..2)
          if (i == 1) {
            cycle += 1
            score += checkScore(cycle, register)
          } else {
            cycle += 1
            score += checkScore(cycle, register)
            register += it.split(" ")[1].trim().toInt()
          }
      } else {
        cycle += 1
        score += checkScore(cycle, register)
      }
    }
    return score
  }

  private fun checkScore(cycle: Int, register: Int): Int {
    return if (scoreCicles.contains(cycle)) {
      println("entered in cycle $cycle")
      val scoreOnCycle = cycle * register
      println("score: $scoreOnCycle")
      scoreOnCycle
    } else
      0
  }

  fun part2(): Int {
    var register = 1
    var cycle = 0
    input.forEach {
      if (it.startsWith("addx")) {
        for (i in 1..2)
          if (i == 1) {
            cycle += 1
            drawCrtPixel(register, cycle)
          } else {
            cycle += 1
            register += it.split(" ")[1].trim().toInt()
            drawCrtPixel(register, cycle)
          }
      } else {
        cycle += 1
        drawCrtPixel(register, cycle)
      }
    }
    return -1
  }

  private fun drawCrtPixel(register: Int, cycle: Int) {
    if (abs(register - (cycle % 40)) < 2)
      print("#")
    else
      print(".")
    if (cycle % 40 == 0)
      println()
  }
}

