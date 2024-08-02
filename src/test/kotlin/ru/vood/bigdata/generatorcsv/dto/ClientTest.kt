package ru.vood.bigdata.generatorcsv.dto

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions
import java.io.File

class ClientTest : FunSpec({

    test("pseudo random test") {
        val client1 = Client("1").toString()
        val client2 = Client("1").toString()
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

            (1..cnt)
                .toList()
                .stream()
                .map { qwe ->  Client(qwe.toString()).toString() }
                .forEach { clientStr ->
                    out.println(clientStr)
                }
        }


    }

})
