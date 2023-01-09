package util

import java.io.File
import java.nio.file.Files
import java.nio.file.Path

private const val testFile = "test.txt"
private const val part1File = "input.txt"
private const val templateFile = "src/Template.txt"

fun main() {
  val configurer = DayConfigurer(14)
  configurer.createDayInputFiles()
  configurer.createDayKtFile()
}

class DayConfigurer(val day: Int) {
  private val dayKt = "Day$day"
  private val dayFolder = "input/day_$day"
  private val dayKtFile = "src/$dayKt.kt"

  fun createDayInputFiles() {
    val dir = File(dayFolder)
    dir.mkdir()
    createFileIn(testFile, dir)
    createFileIn(part1File, dir)
  }

  fun createDayKtFile() {
    val template = File(templateFile)
    Files.write(Path.of(dayKtFile), template.readLines().map { it.replace("%", "$day") }.toList())
  }

  fun testFile() = "$dayFolder/$testFile"
  fun part1() = "$dayFolder/$part1File"

  private fun createFileIn(fileName: String, dir: File) {
    File(dir, fileName).createNewFile()
  }
}
