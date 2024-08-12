package ru.vood.bigdata.generatorcsv.dto

import ru.vood.bigdata.generatorcsv.gen.EntityTemplate

class ClientAccont(id: String) : EntityTemplate<String>(id) {
    val identity by string() genVal { id, paramName ->
        pseudoRandom(
            id.hashCode(),
            20,
            "0123456789"
        )
    } //getFun()// stdStr()
    val acc by string() genVal { id, paramName -> paramName + id } //getFun()// stdStr()

    val balance by number() genVal { id, paramName -> id.hashCode().toBigDecimal() } //getFun()// stdStr()
}