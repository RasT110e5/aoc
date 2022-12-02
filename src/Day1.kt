fun main() {
  val day = 1
  println(Day1(readTest(day)).part1())
  println(Day1(readTest(day)).part2())

  println("Answers:")
  println("Part 1=" + Day1(readInput(day)).part1())
  println("Part 2=" + Day1(readInput(day)).part2())
}

class Day1(private val input: List<String>) {
  fun part1(): Int? {
    return groupFoodByElf().maxOfOrNull { it.totalCalories() }
  }

  fun part2(): Int {
    return groupFoodByElf().map { it.totalCalories() }
      .sorted().takeLast(3).sum()
  }

  private fun groupFoodByElf(): MutableList<Elf> {
    var elf = Elf()
    val elves = mutableListOf(elf)
    for (line in input)
      if (line.isNotBlank())
        elf.addFood(line.toInt())
      else
        elf = addNewElf(elves)
    return elves
  }

  private fun addNewElf(elves: MutableList<Elf>): Elf {
    val elf = Elf()
    elves.add(elf)
    return elf
  }
}

data class Elf(private val foods: MutableList<Int> = mutableListOf()) {
  fun addFood(food: Int) {
    this.foods.add(food)
  }

  fun totalCalories() = foods.sum()
}
