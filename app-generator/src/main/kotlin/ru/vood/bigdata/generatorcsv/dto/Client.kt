package ru.vood.bigdata.generatorcsv.dto

import ru.vood.bigdata.generatorcsv.dto.id.ClientAccountId
import ru.vood.bigdata.generatorcsv.gen.EntityTemplate
import ru.vood.bigdata.generatorcsv.gen.SerialData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs

class Client(id: String) : EntityTemplate<String>(id) {
    private val identity by string { id, paramName ->
        paramName + "_" + id
    }
    private val name by string { id, paramName ->
        "name" + pseudoRandom(
            id.hashCode(),
            20
        )
    } //getFun()// stdStr()

    private val childrenNames by list<String>() genList { id, paramName ->
        (1..abs(id.hashCode() % 20))
            .map {

                "name" + pseudoRandom(
                    id.hashCode() + it.hashCode(),
                    20
                )
            }
    }

    private val salary by number { id, paramName -> abs(identity(id).hashCode() % 1000000).toBigDecimal() }

    private val isWorker by bool { id, paramName -> salary(id).hashCode() % 2 == 1 } //getFun()// stdStr()
    private val isMarried by bool (genBoolDefaultF)

    private val birthDate by date { id, paramName ->
        LocalDateTime.of(1980, 1, 1, 1, 1).plusDays(abs(id.hashCode() % 10000).toLong())
    }

    val accont by ref<ClientAccountId, ClientAccont>() genRef  { id, paramName ->
        val id1 = this.invoke().id.invoke()
        ClientAccont(ClientAccountId(id1, id1))
    }

    val acconts by refList<ClientAccountId, ClientAccont>() genListRef { id, paramName ->
        val map = (1..abs(id.hashCode() % 20))
            .map {
                ClientAccont(ClientAccountId(this.id.invoke(), it.toString()))
            }
        map
    }

    @Suppress("UNCHECKED_CAST")
    override fun customSerializers(): Set<SerialData<String, Any>> {
        return setOf(
            SerialData(this::birthDate, { it.format(DateTimeFormatter.ISO_DATE) }),
            SerialData(this::name, { it + " -> а это добавлено на сериализаторе" }),
        ) as Set<SerialData<String, Any>>

    }


}