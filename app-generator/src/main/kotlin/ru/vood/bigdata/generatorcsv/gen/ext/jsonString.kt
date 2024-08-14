package ru.vood.bigdata.generatorcsv.gen.ext

import ru.vood.bigdata.generatorcsv.gen.DataType
import ru.vood.bigdata.generatorcsv.gen.EntityTemplate
import ru.vood.bigdata.generatorcsv.gen.dsl.GenerateValueFunction

fun <ID_TYPE> EntityTemplate<ID_TYPE>.jsonString(): String {
    val generate = generate { entityTemplate, idVal ->
        val joinToString = entityTemplate.meta.map {
            when (it.value.isSimpleType to it.value.isList) {
                (true to false) -> {
                    val dataType = it.value.function(idVal, it.key)
                    if (dataType.isBoolean()){
                        "\"${it.key}\" : ${dataType()}"
                    } else if(dataType.isNumber()){
                        "\"${it.key}\" : ${dataType()}"
                    }  else {
                        "\"${it.key}\" : \"${dataType()}\""
                    }
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

                    val function =
                        it.value.function as GenerateValueFunction<EntityTemplate<ID_TYPE>, DataType<List<EntityTemplate<*>>>>
                    val invoke = function(this, it.key).invoke()
                    val joinToString = invoke
                        .map { it.jsonString() }
                        .joinToString(",")
                    "\"${it.key}\":[$joinToString]"
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