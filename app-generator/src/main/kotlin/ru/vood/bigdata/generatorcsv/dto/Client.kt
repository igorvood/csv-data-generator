package ru.vood.bigdata.generatorcsv.dto

import ru.vood.bigdata.generatorcsv.gen.EntityTemplate
import java.time.LocalDateTime
import kotlin.math.abs

class Client(id: String) : EntityTemplate<String>(id) {
    private val identity by string() genVal { id, paramName ->
        paramName + "_" + id
    }
    private val name by string() genVal { id, paramName ->
        "name" + pseudoRandom(
            id.hashCode(),
            20
        )
    } //getFun()// stdStr()

    private val childrenNames by list<String>() genVal { id, paramName ->
        (1.. abs(id.hashCode() %20))
            .map {

        "name" + pseudoRandom(
            id.hashCode() + it.hashCode(),
            20
        )
            }
    }

    private val salary by number() genVal { id, paramName -> abs(identity(id).hashCode() % 1000000).toBigDecimal() } //getFun()// stdStr()

    private val isWorker by bool() genVal { id, paramName -> salary(id).hashCode() % 2 == 1 } //getFun()// stdStr()
    private val isMarried by bool().genBool()

    private val birthDate by date() genVal { id, paramName ->
        LocalDateTime.of(1980, 1, 1, 1, 1).plusDays(abs(id.hashCode() % 10000).toLong())
    }

    val accont by ref<Client, ClientAccont>() genRef { id, paramName ->
        val clientAccont: ClientAccont = ClientAccont(this@Client)
        clientAccont
    }

//    val acconts by refList<Client, ClientAccont>() genRef { id, paramName ->
//        val map = (1..abs(id.hashCode() % 20))
//            .map {
//                ClientAccont(this@Client)
//            }
//
//
//        val clientAccont: ClientAccont = ClientAccont(this@Client)
//        map
//    }

}