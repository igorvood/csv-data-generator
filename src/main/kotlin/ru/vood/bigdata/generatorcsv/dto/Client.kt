package ru.vood.bigdata.generatorcsv.dto

import ru.vood.bigdata.generatorcsv.gen.EntityTemplate
import java.time.LocalDateTime
import kotlin.math.abs

class Client() : EntityTemplate<String>() {
    val identity by string() genVal { id, paramName -> pseudoRandom(id.hashCode(), 20) } //getFun()// stdStr()
    val name by string() genVal { id, paramName -> "name" + id } //getFun()// stdStr()

    val salary by number() genVal { id, paramName -> id.hashCode().toBigDecimal() } //getFun()// stdStr()

    val isWorker by bool() genVal { id, paramName -> true } //getFun()// stdStr()
    val isMarried by bool().genBool()

    val d1 by date() genVal { id, paramName ->
        LocalDateTime.of(1980, 1, 1, 1, 1).plusDays(abs(id.hashCode()).toLong())
    }

}