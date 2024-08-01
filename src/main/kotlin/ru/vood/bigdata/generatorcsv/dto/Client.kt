package ru.vood.bigdata.generatorcsv.dto

import ru.vood.bigdata.generatorcsv.gen.EntityTemplate
import java.time.LocalDateTime
import kotlin.math.abs

class Client(id: String) : EntityTemplate<String>(id) {
    val identity by string() genVal { q, w -> pseudoRandom(id.hashCode(), 20) } //getFun()// stdStr()
    val name by string() genVal { q, w -> "name" + id } //getFun()// stdStr()

    val salary by number() genVal { q, w -> id.hashCode().toBigDecimal() } //getFun()// stdStr()

    val isWorker by bool() genVal { q, w -> true } //getFun()// stdStr()
    val isMarried by bool().genBool()

    val d1 by date() genVal { q, w ->
        LocalDateTime.of(1980, 1, 1, 1, 1).plusDays(abs(id.hashCode()).toLong())
    }

}