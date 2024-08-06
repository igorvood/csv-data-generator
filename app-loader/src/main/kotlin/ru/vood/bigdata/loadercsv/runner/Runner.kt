package ru.vood.bigdata.loadercsv.runner

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import ru.vood.bigdata.generatorcsv.flow.util.chunked
import ru.vood.bigdata.loadercsv.dto.repo.ClientRepository
import ru.vood.bigdata.loadercsv.read.ClientCsvReader
import java.io.File

@Service
class Runner(
    private val clientCsvReader : ClientCsvReader,
    private val clientRepository: ClientRepository
): CommandLineRunner {
    val logger = LoggerFactory.getLogger(Runner::class.java)
    override fun run(vararg args: String?) {

        val inputStream =
            File("/home/vood/IdeaProjects/csv-data-generator/app-generator/w/clients.csv_paralel").inputStream()
        val readCsv = clientCsvReader.readCsv(inputStream)

        runBlocking(Dispatchers.Default) {
        readCsv
            .take(10)
            .chunked(1000)
            .collect{
                clientRepository.saveAll(it)
                logger.info(it.size.toString())}
        }
        logger.info("end")

    }
}