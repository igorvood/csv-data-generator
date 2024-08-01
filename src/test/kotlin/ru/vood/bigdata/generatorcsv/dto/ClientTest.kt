package ru.vood.bigdata.generatorcsv.dto

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions
import java.io.File

class ClientTest : FunSpec({

    test("pseudo random test") {
        val client = Client()
        Client().toString("1") shouldBe client.toString("1")
        println(client.toString())
    }

    val client = Client()
    test("pseudo random test, not equals with dif Id") {
        Assertions.assertNotEquals(client.toString("1"), client.toString("2"))
    }

    test("generate 10 clients") {
        (1..10)
            .map { client.toString(it.toString()) }
            .forEach { println(it) }

    }
val cnt = 1000
    test("generate $cnt clients to file") {
        val file = File("fileName")
        file.printWriter().use { out ->

            (1..cnt)
                .toList()
                .stream()
                .map { qwe ->  client.toString(qwe.toString()) }
                .forEach { clientStr ->
                    out.println(clientStr)
                }
        }


    }

})
