package ru.vood.bigdata.generatorcsv.gen.ext

import io.kotest.core.spec.style.FunSpec
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import ru.vood.bigdata.generatorcsv.dto.Client
import ru.vood.bigdata.generatorcsv.gen.EntityTemplate
import java.io.File

class CsvStringBigKtTest : FunSpec({
    val cnt = 100
    val subCnt = 10_000
    val fileClients = "clients"
    val fileAcc = "acc"
    val foldCl = "w/${fileClients}.csv"
    val foldAcc = "w/${fileAcc}.csv"

    test("csvStringBig") {
        val clientFlow = (0..cnt).asFlow()
            .map { Client(it.toString()) }
//            .chunked(subCnt)

        val accontFlow = clientFlow
            .map { it.accont(it.id()) }
//            .map { it.map { it.accont(it.id()) } }

        val launch = async {
            extracted(
                clientFlow,
                File("${foldCl}_paralel"), EntityTemplate<String>::csvStringBig
            )
        }
        val launch1 = async {
            extracted(
                accontFlow,
                File("${foldAcc}_paralel"), EntityTemplate<Client>::csvStringBig
            )
        }
        launch.await()
        launch1.await()


    }
})
