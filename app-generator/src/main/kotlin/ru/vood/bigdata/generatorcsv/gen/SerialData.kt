package ru.vood.bigdata.generatorcsv.gen

import ru.vood.bigdata.generatorcsv.gen.dsl.MetaProperty
import kotlin.reflect.KProperty0

data class SerialData<ID, RES>(
    val prop: KProperty0<MetaProperty<in ID, in RES>>,
    val f: (RES) -> String
)