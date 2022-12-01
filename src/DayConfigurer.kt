import java.io.File
import java.nio.file.Files
import java.nio.file.Path

private const val testFile = "test.txt"
private const val part1File = "part1.txt"
private const val part2File = "part2.txt"
private const val templateFile = "src/Template.txt"

fun main() {
  val configurer = DayConfigurer(2)
  configurer.createDayInputFiles()
  configurer.createDayKtFile()
}

class DayConfigurer(day: Int) {
  private val dayFolder = "input/day_$day"
  private val dayKtFile = "src/Day$day.kt"

  fun createDayInputFiles() {
    val dir = File(dayFolder)
    dir.mkdir()
    createFileIn(testFile, dir)
    createFileIn(part1File, dir)
    createFileIn(part2File, dir)
  }

  fun createDayKtFile(){
    val template = File(templateFile)
    Files.copy(template.toPath(), Path.of(dayKtFile))
  }

  fun testFile() = "$dayFolder/$testFile"
  fun part1() = "$dayFolder/$part1File"
  fun part2() = "$dayFolder/$part2File"

  private fun createFileIn(fileName: String, dir: File) {
    File(dir, fileName).createNewFile()
  }
}
