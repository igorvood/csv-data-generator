package ru.vood.bigdata.generatorcsv.gen.ext

import io.kotest.core.spec.style.FunSpec
import ru.vood.bigdata.generatorcsv.dto.Client

class JsonStringBigKtTest : FunSpec({
    val cnt = 1_000//_000//_000
    val fileClients = "clients"
    val fileAcc = "acc"
    val foldCl = "w/${fileClients}.csv"
    val foldAcc = "w/${fileAcc}.csv"

    test("jsonStringBig") {


        val client = Client("asdfgdfsgsdfgdfg")
        println(client.jsonString())


    }
})
