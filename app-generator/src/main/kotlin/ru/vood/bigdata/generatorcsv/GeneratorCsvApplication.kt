package ru.vood.bigdata.generatorcsv

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GeneratorCsvApplication

fun main(args: Array<String>) {
    runApplication<GeneratorCsvApplication>(*args)
}
