package ru.vood.bigdata.generatorcsv.gen

import ru.vood.bigdata.generatorcsv.gen.dsl.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


abstract class EntityTemplate<ID_TYPE>(
    id: ID_TYPE,
) : DataType<EntityTemplate<ID_TYPE>> {

    private val meta: TreeMap<String, GenerateFieldValueFunction<ID_TYPE, DataType<*>>> = TreeMap()

    val id: DataType<ID_TYPE> = object : DataType<ID_TYPE> {
        override fun invoke(): ID_TYPE = id
    }

    override fun toString(): String {
        val joinToString = meta.map {
            val entityTemplate = this
            val id1 = id.invoke()
            val value = it.value(entityTemplate, id1.toString())()
            it.key + "=" + value
        }
            .joinToString(",")
        return joinToString
    }

    override fun invoke(): EntityTemplate<ID_TYPE> = this

    internal fun addProp(build: MetaProperty<ID_TYPE, *>) {
        meta[build.name] = build.function
    }

//    internal fun addProp(build: Any){
////        meta.add(build)
//    }

    fun string() = PropBuilder<String>()
    fun number() = PropBuilder<BigDecimal>()
    fun date() = PropBuilder<LocalDateTime>()
    fun bool() = PropBuilder<Boolean>()
    inline fun <reified Z> ref() = PropBuilder<Z>()
    inline fun <reified Z> set() = PropBuilder<Set<Z>>()

    inline infix fun <reified R> PropBuilder<R>.genVal(
        crossinline f: GenerateFieldValueFunctionDsl<ID_TYPE, R>
    ): PropBuilder<R> {
        this.function =
            { entityTemplate, parameterName ->
                object : DataType<R> {
                    override fun invoke(): R = f(entityTemplate, parameterName)
                }
            }
        return this
    }

    inner class PropBuilder<R>(
        var name: FieldName = "",
        var function: GenerateFieldValueFunction<ID_TYPE, DataType<R>> = { _, _ ->
            error("Необходимо определить ф-цию в мете для поля $name ")
        }
    ) : Builder<MetaProperty<ID_TYPE, R>>
//where ET: EntityTemplate<Any>
    {

        operator fun provideDelegate(
            thisRef: EntityTemplate<ID_TYPE>,
            property: KProperty<*>
        ): ReadOnlyProperty<EntityTemplate<ID_TYPE>, MetaProperty<ID_TYPE, R>> {
            name = property.name
            val build: MetaProperty<ID_TYPE, R> = this@PropBuilder.build()
            thisRef.addProp(build)
            return ReadOnlyProperty { thisRef, property ->
                return@ReadOnlyProperty build
            }

        }

        override fun build(): MetaProperty<ID_TYPE, R> = MetaProperty(name, function)
    }

}


