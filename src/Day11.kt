fun main() {
  val day = 11
  println(Day11(readTest(day)).part1())
  println(Day11(readTest(day)).part2())

  println("Answers:")
  println("Part 1=" + Day11(readInput(day)).part1())
  println("Part 2=" + Day11(readInput(day)).part2())
}

class Day11(private val input: List<String>) {
  fun part1(): Long {
    val monkeys = parseMonkeys()
    monkeys.forEach { it.afterMainOperation = { item -> Math.floorDiv(item, 3) } }
    for (round in 1..20) {
      for (monkey in monkeys) monkey.inspectItems()
      printMonkeyStatus(round, monkeys)
    }
    return getFirstTimesBySecondInspections(monkeys)
  }

  fun part2(): Long {
    val monkeys = parseMonkeys()
    val commonDiv: Long = monkeys.map { it.divisible }.reduce { a, b -> a * b }.toLong()
    monkeys.forEach { it.afterMainOperation = { item -> item % commonDiv } }
    for (round in 1..10_000) {
      for (monkey in monkeys) monkey.inspectItems()
      if (round in listOf(1, 20, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10_000)) {
        printMonkeyStatus(round, monkeys)
      }
    }
    return getFirstTimesBySecondInspections(monkeys)
  }

  private fun getFirstTimesBySecondInspections(monkeys: List<Monkey>) =
    monkeys.sortedByDescending { it.inspections }.take(2).map { it.inspections }
      .reduce { first, second -> first * second }

  private fun printMonkeyStatus(round: Int, monkeys: List<Monkey>) {
    println("At end of round $round")
    monkeys.forEachIndexed { index, monkey -> println("Monkey $index, has: ${monkey.items} with ${monkey.inspections} inspections") }
    println()
  }

  private fun toMonkey(it: List<String>) = Monkey(
    it[1].numbersAsLong().toMutableList(),
    it[2].trim().replace(" ", "").replace("new=", ""),
    it[3].numbers().first(),
    it[4].numbers().first(),
    it[5].numbers().first(),
  )

  private fun parseMonkeys(): List<Monkey> {
    val monkeys = input.splitOnEmpty()
      .map { it.map { monkeyProp -> monkeyProp.split(":")[1] } }
      .map { toMonkey(it) }
    monkeys.forEach { monkey ->
      monkey.monkeyToThrowOnTrue = monkeys[monkey.ifTrue]
      monkey.monkeyToThrowOnFalse = monkeys[monkey.ifFalse]
    }
    return monkeys
  }
}

data class Monkey(
  val items: MutableList<Long>,
  val op: String,
  val divisible: Int,
  val ifTrue: Int,
  val ifFalse: Int
) {
  lateinit var afterMainOperation: (Long) -> Long
  private val parsedOp: (Long) -> Long
  var inspections = 0L
  var monkeyToThrowOnTrue: Monkey? = null
  var monkeyToThrowOnFalse: Monkey? = null

  init {
    val numberOp = if (op.substring(4) == "old") null else op.substring(4).toLong()
    parsedOp = when (op[3]) {
      '+' -> { a -> a + (numberOp ?: a) }
      else -> { a -> a * (numberOp ?: a) }
    }
  }

  private fun processFirstItem() {
    val item = items.removeFirst()
    val result = calculateResult(item)
    if (result % divisible == 0L)
      monkeyToThrowOnTrue?.items?.add(result)
    else
      monkeyToThrowOnFalse?.items?.add(result)
  }

  private fun calculateResult(item: Long): Long {
    return afterMainOperation.invoke(parsedOp.invoke(item))
  }

  fun inspectItems() {
    for (i in items.indices) {
      inspections++
      processFirstItem()
    }

  }

}

