package ru.vood.bigdata.generatorcsv.dto

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions
import java.io.File

class ClientTest : FunSpec({

    test("pseudo random test") {
        val client = Client("1")
        Client("1").toString() shouldBe client.toString()
        println(client.toString())
    }

    test("pseudo random test, not equals with dif Id") {
        Assertions.assertNotEquals(Client("1").toString(), Client("2").toString())
    }

    test("generate 10 clients") {
        (1..10)
            .map {  Client(it.toString())}
            .forEach { println(it.toString()) }

    }

    test("generate 1000 clients to file") {

        val file = File("fileName")

        val joinToString = (1..10_000_000)

        file.printWriter().use { out ->
            (1..1_000_000_000)
                .map{Client(it.toString()).toString()}
                .chunked(1000)
                .map { asdasd ->  asdasd.joinToString                    ("\n")}
                .forEach { clientStr ->
                out.println(clientStr)
            }
        }


    }

})
