package ru.vood.bigdata.loadercsv.dto

import java.time.LocalDateTime

data class Client(
    val identity: String,
    val name: String,
    val salary: Long,

     val isWorker: Boolean,
    val isMarried: Boolean,

     val birthDate: LocalDateTime
)