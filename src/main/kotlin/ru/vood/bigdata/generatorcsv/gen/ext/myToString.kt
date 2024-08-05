package ru.vood.bigdata.generatorcsv.gen.ext

import kotlinx.coroutines.runBlocking
import ru.vood.bigdata.generatorcsv.gen.DataType
import ru.vood.bigdata.generatorcsv.gen.EntityTemplate
import ru.vood.bigdata.generatorcsv.gen.dsl.GenerateValueFunction

fun<ID_TYPE> EntityTemplate<ID_TYPE>.myToString(/*id: ID_TYPE*/): String {
    val generate = generate { entityTemplate, idVal ->
        entityTemplate.meta.map {
            when(it.value.isSimpleType){
                true -> {
                    val f = it.value.function
                    it.key + "=" + it.value.function(idVal, it.key)()
                }
                false -> {
                    val value = it.value
                    val function = value.function as GenerateValueFunction<EntityTemplate<ID_TYPE>, DataType<EntityTemplate<*>>>
                    val any = function(
                        this,
                        it.key
                    )()
                    val string = runBlocking {   any.myToString()}
                    val s = "{" + string + "}"
                    s
                    ""
                }

            }
        }
            .filter { it.isNotBlank() }
            .joinToString(", ")

    }
    return generate
}