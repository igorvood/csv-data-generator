package ru.vood.bigdata.generatorcsv.dto

import kotlinx.coroutines.runBlocking
import ru.vood.bigdata.generatorcsv.gen.EntityTemplate
import java.time.LocalDateTime
import kotlin.math.abs

class Client(id: String) : EntityTemplate<String>(id) {
    private val identity by string() genVal { id, paramName ->
        paramName + "_"+id
    }
    private val name by string() genVal { id, paramName -> "name" + pseudoRandom(id.hashCode(), 20) } //getFun()// stdStr()

    private val salary by number() genVal { id, paramName -> abs( identity(id).hashCode() % 1000000) .toBigDecimal() } //getFun()// stdStr()

    private val isWorker by bool() genVal { id, paramName -> salary(id) .hashCode() % 2 ==1 } //getFun()// stdStr()
    private val isMarried by bool().genBool()

    private val birthDate by date() genVal { id, paramName ->
        LocalDateTime.of(1980, 1, 1, 1, 1).plusDays(abs(id.hashCode()).toLong())
    }

//    private val accont by stringRef() genVal { id,paramName->
//         val runBlocking = runBlocking { ClientAccont(this@Client).myToString() }
//        runBlocking
//    }

    private val accont by ref<ClientAccont>() genRef  { id,paramName->
        ClientAccont(this@Client)
    }

}