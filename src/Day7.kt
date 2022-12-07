fun main() {
  val day = 7
  println(Day7(readTest(day)).part1())
  println(Day7(readTest(day)).part2())

  println("Answers:")
  println("Part 1=" + Day7(readInput(day)).part1())
  println("Part 2=" + Day7(readInput(day)).part2())
}


class Day7(private val input: List<String>) {
  private val changeDir = "$ cd"
  private val goToParent = "$ cd .."

  fun part1(): Int {
    val sizeLessThan = 100_000
    return fileSystem()
      .filter { it.totalSize() < sizeLessThan }
      .sumOf { it.totalSize() }
  }

  fun part2(): Int {
    val totalDiskSpace = 70_000_000
    val neededForUpdate = 30_000_000
    val used = fileSystem().sumOf { it.childrenTotalSize }
    val minDeleteForUpdate = neededForUpdate - (totalDiskSpace - used)
    return fileSystem()
      .sortedBy { it.totalSize() }
      .first { it.totalSize() > minDeleteForUpdate }
      .totalSize()
  }

  private fun fileSystem(): List<File> {
    val root = File("/")
    var current = root
    val inputSkippingFirstStaticLine = input.drop(1)
    inputSkippingFirstStaticLine.forEach {
      when {
        it.startsWith(goToParent) -> current = current.parent!!
        it.startsWith(changeDir) -> current = changeDirectory(it.replace(changeDir, ""), current)
        it.first().isDigit() -> addFileToDir(current, it.split(" ").first())
      }
    }
    return root.directories()
  }

  private fun addFileToDir(file: File, fileSize: String) {
    file.childrenTotalSize += fileSize.toInt()
  }

  private fun changeDirectory(dirName: String, current: File): File {
    val newDir = File(dirName, current)
    current.children.add(newDir)
    return newDir
  }

}

data class File(
  val name: String,
  val parent: File? = null,
  val children: MutableList<File> = mutableListOf(),
  var childrenTotalSize: Int = 0,
) {

  fun directories() = flattenChildren(this)

  fun totalSize(): Int {
    return sumChildrenSizes(this)
  }

  private fun sumChildrenSizes(file: File): Int {
    val sizes = file.children.sumOf { sumChildrenSizes(it) }
    return sizes + file.childrenTotalSize
  }

  private fun flattenChildren(file: File): List<File> {
    val children = file.children.flatMap { flattenChildren(it) }
    return children.plus(file)
  }

  override fun toString(): String {
    return "File(name='$name', childrenTotalSize=$childrenTotalSize)"
  }
}