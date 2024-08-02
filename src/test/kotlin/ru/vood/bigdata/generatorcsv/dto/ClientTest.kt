package ru.vood.bigdata.generatorcsv.dto

import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.toList
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
        Assertions.assertNotEquals(Client("1").toString(), Client("2").toString())
    }

    test("generate 10 clients") {
        (1..10)
            .map { Client(it.toString()).toString() }
            .forEach { println(it) }
    }
val cnt = 1000
    test("generate $cnt clients to file") {
        val file = File("fileName")
        file.printWriter().use { out ->

            for (i in 0 ..cnt){
                val subCnt = 1000
                val runBlocking = runBlocking {
                    val map = (i * subCnt..(i * subCnt) + subCnt - 1)
                        .asFlow()
                        .map { qwe -> Client(qwe.toString()).myToString() }
                        .toList()
                        .joinToString(System.lineSeparator())
//                        .reduce { accumulator, value -> accumulator + System.lineSeparator() + value }
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
