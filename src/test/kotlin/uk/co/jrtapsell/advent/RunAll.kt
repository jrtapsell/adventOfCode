package uk.co.jrtapsell.advent

import org.testng.Assert
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import java.io.File

class RunAll {
    data class DayRun<out T>(val day: Int, val part: String, val account: String, val partObj: Part<T, *>, val result: T) {
        override fun toString(): String {
            return "Day:$day, Part:$part, Account:$account"
        }
    }
    object FailingDay: Part<Nothing, Nothing>() {
        override fun getInput(s: String) = throw AssertionError("Unimplemented")
        override fun calculate(input: Nothing) = throw AssertionError("Unimplemented")
    }
    @DataProvider(name = "days")
    fun dataProvider(): Array<Array<DayRun<*>>> {
        val fileData = File("data.csv").readLines()
        val header = fileData[0].split(",")
        val dataRows = fileData.subList(1, fileData.size).map { it.split(",") }
        val names = header.subList(2, header.size)
        return dataRows.flatMap {
            val dayNumber = it[0].toInt(10)
            val dayPart = it[1]
            val dayClass = try {
                Class.forName("uk.co.jrtapsell.advent.day$dayNumber.Day$dayNumber$dayPart")
            } catch (ex: ClassNotFoundException) {
                FailingDay::class.java
            }
            val dayObject = dayClass.kotlin.objectInstance as Part<*, *>
            val dataItems = it.subList(2, it.size)
            names.indices.map {
                val value = if (it in dataItems.indices) dataItems[it] else null
                val name = names[it]
                val data = value?.let { parse(it) }
                arrayOf(DayRun(dayNumber, dayPart, name, dayObject, data))
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

    @Test(dataProvider = "days", timeOut = 3000)
    fun <T> run(dayRun: DayRun<T>) {
        val result = dayRun.partObj.run(dayRun.account)
        Assert.assertEquals(result, dayRun.result)
    }
}