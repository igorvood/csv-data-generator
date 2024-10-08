package ru.vood.bigdata.generatorcsv.gen.ext

import kotlinx.coroutines.runBlocking
import ru.vood.bigdata.generatorcsv.gen.DataType
import ru.vood.bigdata.generatorcsv.gen.EntityTemplate
import ru.vood.bigdata.generatorcsv.gen.dsl.GenerateValueFunction

fun <ID_TYPE> EntityTemplate<ID_TYPE>.myToString(): String {
    val generate = generate { entityTemplate, idVal ->
        entityTemplate.meta.map {
            when (it.value.isSimpleType to it.value.isList) {
                (true to false) -> {
                    val f = it.value.function
                    val dataType = it.value.function(idVal, it.key)
                    it.key + "=" + dataType()
                }

                (false to false) -> {
                    val value = it.value
                    val function =
                        value.function as GenerateValueFunction<EntityTemplate<ID_TYPE>, DataType<EntityTemplate<*>>>
                    val any = function(
                        this,
                        it.key
                    )()
                    val string = runBlocking { any.myToString() }
                    val s = "{" + string + "}"
                    s
                    ""
                }

                (true to true) -> ""
                (false to true) -> ""
                else -> error("невозможный кез")
            }
        }
            .filter { it.isNotBlank() }
            .joinToString(", ")

    }
    return generate
}