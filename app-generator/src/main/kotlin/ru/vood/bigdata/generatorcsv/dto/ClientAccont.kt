package ru.vood.bigdata.generatorcsv.dto

import ru.vood.bigdata.generatorcsv.dto.id.ClientAccountId
import ru.vood.bigdata.generatorcsv.gen.EntityTemplate
import ru.vood.bigdata.generatorcsv.util.absHashCode

class ClientAccont(id: ClientAccountId) : EntityTemplate<ClientAccountId>(id) {
    val identity by string() genVal { id, paramName -> id.clientId + "_" + id.accountId }
    val acc by string() genVal { id, paramName ->
        pseudoRandom(
            id.hashCode(),
            20,
            "0123456789"
        )
    }

    val balance by number() genVal { id, paramName -> id.absHashCode().toBigDecimal() }
}