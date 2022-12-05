import java.util.*

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
        val crates = mutableListOf<Char>().apply { repeat(a) { add(stacks[b - 1].pop()) } }
        crates.reverse()
        crates.forEach { stacks[c - 1].push(it) }
      }
    }
    return joinTopOfStackCrates(stacks)
  }

  private fun joinTopOfStackCrates(stacks: MutableList<Stack<Char>>) =
    stacks.map { it.peek() }.joinToString("")

  private fun moveCratesFromStacksInPopOrder(
    stacks: MutableList<Stack<Char>>,
    from: Int,
    to: Int
  ) {
    val stackFrom = stacks[from - 1]
    val crate = stackFrom.pop()
    stacks[to - 1].push(crate)
  }

  private fun readInitialStacks(): MutableList<Stack<Char>> {
    return mutableListOf<Stack<Char>>().apply {
      input[0].split(",").forEach { line ->
        val stack = Stack<Char>()
        line.forEach { stack.push(it) }
        add(stack)
      }
    }
  }
}


