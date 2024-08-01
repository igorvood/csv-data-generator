package ru.vood.bigdata.generatorcsv.dto

import ru.vood.bigdata.generatorcsv.gen.EntityTemplate
import java.time.LocalDateTime
import kotlin.math.abs

class ClientAccont(id: Client) : EntityTemplate<Client>(id) {
    val identity by string() genVal { id, paramName -> pseudoRandom(id.hashCode(), 20, "0123456789") } //getFun()// stdStr()
    val acc by string() genVal { id, paramName -> paramName + id.id() } //getFun()// stdStr()

    val balance by number() genVal { id, paramName -> id.hashCode().toBigDecimal() } //getFun()// stdStr()
}