package ru.vood.bigdata.generatorcsv.dto

import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.*
import org.junit.jupiter.api.Assertions
import java.io.File

class ClientTest : FunSpec({

    test("pseudo random test") {
        val client1 = Client("1").myToString()
        val client2 = Client("1").myToString()
        client1 shouldBe client2
    }

//    val client = Client()
    test("pseudo random test, not equals with dif Id") {
        Assertions.assertNotEquals(Client("1").myToString(), Client("2").myToString())
    }

    test("generate 10 clients") {
        (1..10)
            .map { Client(it.toString()).myToString() }
            .forEach { println(it) }
    }
    val cnt = 100
    val subCnt = 100000
    test("generate ${cnt * subCnt} clients to file") {
        val file = File("fileName")
        file.printWriter().use { out ->

            for (i in 0..cnt) {

                val runBlocking = runBlocking {
                    val map = (i * subCnt..(i * subCnt) + subCnt - 1)
                        .asFlow()
                        .chunked(200)
                        .map { qwe -> qwe.map { Client(it.toString()).myToString() } }
//                        .reduce { accumulator, value -> accumulator + System.lineSeparator() + value }
                        .toList()
                        .joinToString(System.lineSeparator())

                    map
                }
                out.println(runBlocking)

            }
//            (1..cnt)
//                .toList()
//                .stream()
//                .map { qwe ->  Client(qwe.toString()).toString() }
//                .forEach { clientStr ->
//                    out.println(clientStr)
//                }
        }


    }

})

fun <T> Flow<T>.chunked(chunkSize: Int): Flow<List<T>> {
    val buffer = mutableListOf<T>()
    return flow {
        this@chunked.collect {
            buffer.add(it)
            if (buffer.size == chunkSize) {
                emit(buffer.toList())
                buffer.clear()
            }
        }
        if (buffer.isNotEmpty()) {
            emit(buffer.toList())
        }
    }
}