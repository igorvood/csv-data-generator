package ru.vood.bigdata.generatorcsv.gen.ext

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

class CsvStringBigKtTest : FunSpec({
    val cnt = 1_000//_000//_000
    val fileClients = "clients"
    val fileAcc = "acc"
    val foldCl = "w/${fileClients}.csv"
    val foldAcc = "w/${fileAcc}.csv"

    test("csvStringBig") {
        withContext(Dispatchers.IO) {
            val clientFlow = (0..cnt).asFlow()
                .map { Client(it.toString()) }

            val accontFlow = clientFlow
                .map { it.accont(it.id()) }

            val asyncs = listOf(
                async {
                    extracted(
                        clientFlow,
                        File("${foldCl}_paralel"), { it.csvStringBig() },
                    )
                },
                async {
                    extracted(
                        accontFlow,
                        File("${foldAcc}_paralel"), EntityTemplate<Client>::csvStringBig
                    )
                }
            )

//            val map = (1..50)
//                .map {
//                    async {
//                        extracted(
//                            clientFlow,
//                            File("${foldCl}${it}_paralel"), { it.csvStringBig() },
//                        )
//                    }
//                }
//            map.awaitAll()
            asyncs.awaitAll()
        }

    }
})
