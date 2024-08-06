package ru.vood.bigdata.loadercsv.runner

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import ru.vood.bigdata.loadercsv.read.ClientCsvReader
import java.io.File

@Service
class Runner(
    private val clientCsvReader : ClientCsvReader
): CommandLineRunner {
    val logger = LoggerFactory.getLogger(Runner::class.java)
    override fun run(vararg args: String?) {

        val inputStream =
            File("/home/vood/IdeaProjects/csv-data-generator/app-generator/w/clients.csv_paralel").inputStream()
        val readCsv = clientCsvReader.readCsv(inputStream)

        runBlocking(Dispatchers.IO) {
        readCsv.take(1000_000)
            .collect{ logger.info(it.toString())}
        }
        logger.info("end")

    }
}