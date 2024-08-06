package ru.vood.bigdata.loadercsv

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class LoaderCsvApplication

fun main(args: Array<String>) {
    runApplication<LoaderCsvApplication>(*args)
}
