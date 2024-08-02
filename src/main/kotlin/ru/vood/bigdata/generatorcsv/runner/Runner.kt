package ru.vood.bigdata.generatorcsv.runner

import kotlinx.coroutines.Dispatchers
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import ru.vood.bigdata.generatorcsv.dto.Client
import java.io.File
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

val cnt = 10
    val subCnt = 100000
    fun main() {
        println(LocalDateTime.now())
        runBlocking(Dispatchers.Default) {
            (0..cnt).asFlow()
                .map {  i ->
                    val map = (i * subCnt..(i * subCnt) + subCnt - 1)
                        .asFlow()
                        .chunked(200)
                        .map { qwe -> qwe.map { Client(it.toString()).myToString() } }
                        //                        .reduce { accumulator, value -> accumulator + System.lineSeparator() + value }
                        .toList()
                        .joinToString(System.lineSeparator())

                    val file = File("w/fileName_${i}")
                    file.printWriter().use { out ->
                        out.println(map)
                    }
                    i
                }
                .collect { }
        }
        println(LocalDateTime.now())
    }
