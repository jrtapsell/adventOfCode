import org.testng.Assert
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import uk.co.jrtapsell.advent.Day
import java.io.File

class RunAll {
    @DataProvider(name = "days")
    fun dataProvider(): Array<Array<Any?>> {
        val fileData = File("src/test/resources/data.csv").readLines()
        val header = fileData[0].split(",")
        val dataRows = fileData.subList(1, fileData.size).map { it.split(",") }
        val names = header.subList(2, header.size)
        return dataRows.flatMap {
            val dayNumber = it[0].toInt(10)
            val dayPart = it[1]
            val dayClass = Class.forName("uk.co.jrtapsell.advent.day$dayNumber.Day$dayNumber$dayPart")
            val dayObject = dayClass.kotlin.objectInstance as Day<*, *>
            it.subList(2, it.size).zip(names).map { (value, name) ->
                val data = parse(value)
                arrayOf(dayObject, data, name)
            }
        }.toTypedArray()
    }

    private val numberRegex = Regex("[0-9]+")
    private fun parse(value: String): Any? {
        return when {
            value == "" -> null
            value.matches(numberRegex) -> value.toInt()
            else -> value
        }
    }

    @Test(dataProvider = "days", timeOut = 30000)
    fun run(day: Day<*, *>, result: Any?, tag: String) {
        Assert.assertEquals(day.run(tag), result)
    }
}