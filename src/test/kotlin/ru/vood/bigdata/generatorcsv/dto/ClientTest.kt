package ru.vood.bigdata.generatorcsv.dto

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Assertions
import ru.vood.bigdata.generatorcsv.gen.EntityTemplate
import ru.vood.bigdata.generatorcsv.gen.ext.extracted
import ru.vood.bigdata.generatorcsv.gen.ext.myToString
import ru.vood.bigdata.generatorcsv.runner.chunked
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

    val fileClients = "clients"
    val fileAcc = "acc"
    test("generate 10 $fileClients") {
        (1..10)
            .map { Client(it.toString()).myToString() }
            .forEach { println(it) }
    }
    val cnt = 10
    val subCnt = 10
    val foldCl = "w/${fileClients}"
    val foldAcc = "w/${fileAcc}"

    test("generate ${cnt * subCnt} $fileClients to file") {
        val file = File(foldCl)
        file.printWriter().use { out ->
            for (i in 0..cnt * subCnt) {
                val myToString = Client(i.toString()).myToString()
                out.println(myToString)
            }
        }
        val fileAcc = File(foldAcc)
        fileAcc.printWriter().use { out ->
            for (i in 0..cnt * subCnt) {
                val accont = Client(i.toString()).accont(i.toString())
                val myToString = accont.myToString()
                out.println(myToString)
            }
        }

    }

    test("generate ${cnt * subCnt} $fileClients to file files") {
        (0..cnt).asFlow()
            .collect { i ->
                val map = (i * subCnt..(i * subCnt) + subCnt - 1)
                    .asFlow()
                    .chunked(200)
                    .map { qwe -> qwe.map { Client(it.toString()).myToString() }.joinToString(System.lineSeparator()) }
                    .toList()
                    .joinToString(System.lineSeparator())

                val file = File("${foldCl}_${i}")
                file.printWriter().use { out ->
                    out.println(map)
                }

            }
    }

    test("generate ${cnt * subCnt} $fileClients to file files both") {
        val clientFlow = (0..cnt).asFlow()
            .map { Client(it.toString()) }
//            .chunked(subCnt)

        val accontFlow = clientFlow
            .map { it.accont(it.id()) }
//            .map { it.map { it.accont(it.id()) } }

        val launch = async { extracted(clientFlow, File("${foldCl}_paralel"), EntityTemplate<String>::myToString) }
        val launch1 = async { extracted(accontFlow, File("${foldAcc}_paralel"), EntityTemplate<Client>::myToString) }
        launch.await()
        launch1.await()


    }

})



