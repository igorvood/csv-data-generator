package ru.vood.bigdata.generatorcsv.gen.ext

import kotlinx.coroutines.runBlocking
import ru.vood.bigdata.generatorcsv.gen.DataType
import ru.vood.bigdata.generatorcsv.gen.EntityTemplate
import ru.vood.bigdata.generatorcsv.gen.dsl.GenerateValueFunction

fun <ID_TYPE> EntityTemplate<ID_TYPE>.jsonString(/*id: ID_TYPE*/): String {
    val generate = generate { entityTemplate, idVal ->
        val joinToString = entityTemplate.meta.map {
            when (it.value.isSimpleType to it.value.isList) {
                (true to false) -> {
                    val f = it.value.function
                    """"${it.key}"""" + " : " + """"${it.value.function(idVal, it.key)()}""""
                }

                (false to false) -> {
                    val value = it.value
                    val function =
                        value.function as GenerateValueFunction<EntityTemplate<ID_TYPE>, DataType<EntityTemplate<*>>>
                    val function1 = function(
                        this,
                        it.key
                    )
                    val jsonString = function1.invoke().jsonString()
                    val any = function1()
                    val string = runBlocking { any.myToString() }
                    val s = """"${it.key}"""" +":" + jsonString
                    s
//                    ""
                }

                (true to true) -> error("пока массив простых типов не описан")
                (false to true) -> error("пока массив ссылок не описан")
                else -> error("невозможный кез")
            }
        }
            .filter { it.isNotBlank() }
            .joinToString(",\n")
        "{"+joinToString+"}"

    }
    return generate
}