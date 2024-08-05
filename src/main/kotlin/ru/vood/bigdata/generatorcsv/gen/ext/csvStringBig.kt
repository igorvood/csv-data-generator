package ru.vood.bigdata.generatorcsv.gen.ext

import ru.vood.bigdata.generatorcsv.gen.EntityTemplate

fun<ID_TYPE> EntityTemplate<ID_TYPE>.csvStringBig(): String {
    val generate = generate { entityTemplate, idVal ->
        entityTemplate.meta.map {
            when(it.value.isSimpleType){
                true -> {
                    it.value.function(idVal, it.key)().toString()
                }
                false -> {""}
            }
        }
            .filter { it.isNotBlank() }
            .joinToString(";")

    }
    return generate
}