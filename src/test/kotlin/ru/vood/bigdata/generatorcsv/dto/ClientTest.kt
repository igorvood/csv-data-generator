package ru.vood.bigdata.generatorcsv.dto

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ClientTest : FunSpec({

    test("pseudo random test") {
        val client = Client("1")
        Client("2").toString() shouldBe client.toString()
        println(client.toString())
    }

    test("name") { }

    test("salary") { }

    test("isWorker") { }

    test("isMarried") { }
})
