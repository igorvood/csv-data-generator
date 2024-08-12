package ru.vood.bigdata.generatorcsv.gen.ext

import io.kotest.assertions.print.print
import io.kotest.core.spec.style.FunSpec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.vood.bigdata.generatorcsv.dto.Client
import ru.vood.bigdata.generatorcsv.gen.EntityTemplate
import java.io.File

class JsonStringBigKtTest : FunSpec({
    val cnt = 1_000//_000//_000
    val fileClients = "clients"
    val fileAcc = "acc"
    val foldCl = "w/${fileClients}.csv"
    val foldAcc = "w/${fileAcc}.csv"

    test("jsonStringBig") {


            println(Client("asdfgdfsgsdfgdfg").jsonString())



    }
})
