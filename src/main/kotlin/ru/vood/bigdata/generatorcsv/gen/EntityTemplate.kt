package ru.vood.bigdata.generatorcsv.gen

import ru.vood.bigdata.generatorcsv.gen.dsl.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import kotlin.math.abs
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


abstract class EntityTemplate<ID_TYPE>(
//    id: ID_TYPE,
) : DataType<EntityTemplate<ID_TYPE>> {

    private val meta: TreeMap<String, GenerateValueFunction<ID_TYPE, DataType<*>>> = TreeMap()

//    val id: DataType<ID_TYPE> = object : DataType<ID_TYPE> {
//        override fun invoke(): ID_TYPE = id
//    }

    fun toString(id: ID_TYPE): String {
        val generate = generate(id) { entityTemplate, idVal ->
            entityTemplate.meta.map {
                val value = it.value(idVal, idVal.toString())()
                it.key + "=" + value
            }
                .joinToString(", ")

        }
    return generate
    }

    override fun invoke(): EntityTemplate<ID_TYPE> = this

    fun generate(id: ID_TYPE, printFun: (EntityTemplate<ID_TYPE>, ID_TYPE )-> String ): String {
        return printFun(this, id)
    }

    internal fun addProp(build: MetaProperty<ID_TYPE, *>) {
        meta[build.name] = build.function
    }

    fun string() = PropBuilder<String>()
    fun number() = PropBuilder<BigDecimal>()
    fun date() = PropBuilder<LocalDateTime>()
    fun bool() = PropBuilder<Boolean>()
    inline fun <reified Z> ref() = PropBuilder<Z>()
    inline fun <reified Z> set() = PropBuilder<Set<Z>>()

    inline infix fun <reified OUT_TYPE> PropBuilder<OUT_TYPE>.genVal(
        crossinline f: GenerateValueFunction<ID_TYPE, OUT_TYPE>
    ): PropBuilder<OUT_TYPE> {
        this.function =
            { idVal, parameterName ->
                object : DataType<OUT_TYPE> {
                    override fun invoke(): OUT_TYPE = f(idVal, parameterName)
                }
            }
        return this
    }

    fun PropBuilder<Boolean>.genBool(
    ): PropBuilder<Boolean> {
        this.function =
            { id, parameterName ->
                object : DataType<Boolean> {
                    override fun invoke(): Boolean {
                        val hc = abs(id.hashCode())
                        return hc % 2 == 1
                    }
                }
            }
        return this
    }

    inner class PropBuilder<R>(
        var name: FieldName = "",
        var function: GenerateValueFunction<ID_TYPE, DataType<R>> = { _, _ ->
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


