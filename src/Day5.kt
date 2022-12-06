import java.util.*
import kotlin.collections.ArrayDeque

fun main() {
  val day = 5
  println(Day5(readTest(day)).part1())
  println(Day5(readTest(day)).part2())

  println("Answers:")
  println("Part 1=" + Day5(readInput(day)).part1())
  println("Part 2=" + Day5(readInput(day)).part2())
}

class Day5(private val input: List<String>) {
  fun part1(): String {
    val stacks = readInitialStacks()
    val movementNumbers = input.subList(1, input.size).toNumbersWithin()
    movementNumbers.forEach { (amount, from, to) ->
      repeat(amount) { moveCratesFromStacksInPopOrder(stacks, from, to) }
    }
    return joinTopOfStackCrates(stacks)
  }

  fun part2(): String {
    val stacks = readInitialStacks()
    val movementNumbers = input.subList(1, input.size).toNumbersWithin()
    movementNumbers.forEach { (a, b, c) ->
      if (a == 1) moveCratesFromStacksInPopOrder(stacks, b, c)
      else {
        val crates = mutableListOf<Char>().apply { repeat(a) { add(stacks[b - 1].removeLast()) } }
        crates.reverse()
        crates.forEach { stacks[c - 1].add(it) }
      }
    }
    return joinTopOfStackCrates(stacks)
  }

  private fun joinTopOfStackCrates(stacks: MutableList<ArrayDeque<Char>>) =
    stacks.map { it.first() }.joinToString("")

  private fun moveCratesFromStacksInPopOrder(
    stacks: MutableList<ArrayDeque<Char>>,
    from: Int,
    to: Int
  ) {
    val stackFrom = stacks[from - 1]
    val crate = stackFrom.removeLast()
    stacks[to - 1].add(crate)
  }

  private fun readInitialStacks(): MutableList<ArrayDeque<Char>> {
    return mutableListOf<ArrayDeque<Char>>().apply {
      input[0].split(",").forEach { line ->
        val stack = ArrayDeque<Char>()
        line.forEach { stack.add(it) }
        add(stack)
      }
    }
  }
}


