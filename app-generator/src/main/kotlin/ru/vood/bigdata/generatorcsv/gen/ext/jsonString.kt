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
                     "\"${it.key}\":$jsonString"
                }

                (true to true) -> {
                    val function1 = it.value.function(idVal, it.key)() as List<Any>
                    val joinToString = function1
                        .map { "\"${it.toString()}\"" }
                        .joinToString(",\n")

                    "\"${it.key}\":[$joinToString]"
                }
                (false to true) -> {
                    val value = it.value

                    val function =
                        it.value.function as GenerateValueFunction<EntityTemplate<ID_TYPE>, DataType<List<EntityTemplate<*>>>>
//                    GenerateValueFunction<EntityTemplate<ID_TYPE>, List<OUT_TYPE>>
                    val invoke = function(this, it.key).invoke()
                    val joinToString = invoke
                        .map { it.jsonString() }
//                        .map { "{$it}" }
                        .joinToString(",")
                    "\"${it.key}\":[$joinToString]"
//                    val function1 = function(this, it.key) //as List<Any>
//                    println(value.isList)
//                    error("пока массив ссылок не описан")
                }
                else -> error("невозможный кез")
            }
        }
            .filter { it.isNotBlank() }
            .joinToString(",\n")
        "{$joinToString}"

    }
    return generate
}