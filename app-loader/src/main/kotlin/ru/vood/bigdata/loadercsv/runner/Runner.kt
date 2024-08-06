package ru.vood.bigdata.loadercsv.runner

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vood.bigdata.generatorcsv.flow.util.chunked
import ru.vood.bigdata.loadercsv.dto.Client
import ru.vood.bigdata.loadercsv.dto.repo.ClientRepository
import ru.vood.bigdata.loadercsv.read.ClientCsvReader
import java.io.File
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
@Transactional
open class Runner(
    private val clientCsvReader : ClientCsvReader,
    private val clientRepository: ClientRepository,
    private val batchPersist: BatchPersist
): CommandLineRunner {

    val logger = LoggerFactory.getLogger(BatchPersist::class.java)

    override fun run(vararg args: String?) {

        val inputStream =
            File("/home/vood/IdeaProjects/csv-data-generator/app-loader/src/main/resources/clients.csv_paralel").inputStream()

        clientRepository.deleteAllInBatch()
        val readCsv = clientCsvReader.readCsv(inputStream)
        val begin = LocalDateTime.now()
        runBlocking(Dispatchers.IO) {
        readCsv
//            .take(10)
            .chunked(1000)
            .collect{clients->
//                clientRepository.saveAll(clients)
                batchPersist.extracted(clients)
            }
        }
        val between = ChronoUnit.SECONDS.between(begin, LocalDateTime.now())
        logger.info("end ${between}")


    }


}