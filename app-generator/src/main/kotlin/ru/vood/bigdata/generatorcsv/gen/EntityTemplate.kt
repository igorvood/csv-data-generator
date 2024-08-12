package ru.vood.bigdata.generatorcsv.gen

import ru.vood.bigdata.generatorcsv.gen.dsl.Builder
import ru.vood.bigdata.generatorcsv.gen.dsl.FieldName
import ru.vood.bigdata.generatorcsv.gen.dsl.GenerateValueFunction
import ru.vood.bigdata.generatorcsv.gen.dsl.MetaProperty
import ru.vood.bigdata.generatorcsv.gen.ext.myToString
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import kotlin.math.abs
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


abstract class EntityTemplate<ID_TYPE>(
    id: ID_TYPE,
) : DataType<EntityTemplate<ID_TYPE>> {

    val meta: TreeMap<String, MetaProperty<ID_TYPE, *>> = TreeMap()

    val id: DataType<ID_TYPE> = object : DataType<ID_TYPE> {
        override fun invoke(): ID_TYPE = id
    }

    override fun hashCode(): Int {
        return myToString().hashCode()
    }

    override fun invoke(): EntityTemplate<ID_TYPE> = this

    fun generate(printFun: (EntityTemplate<ID_TYPE>, ID_TYPE) -> String): String {
        return printFun(this, id())
    }

    internal fun addProp(metaProperty: MetaProperty<ID_TYPE, *>) {
        meta[metaProperty.paramName] = metaProperty
    }

    fun string() = PropBuilder<String>(isSimpleType = true, isList = false)
    fun number() = PropBuilder<BigDecimal>(isSimpleType = true, isList = false)
    fun date() = PropBuilder<LocalDateTime>(isSimpleType = true, isList = false)
    fun bool() = PropBuilder<Boolean>(isSimpleType = true, isList = false)
    inline fun <reified Z, E : EntityTemplate<Z>> ref() = RefBuilder<E>(isSimpleType = false, isList = false)

    inline fun <reified Z, E : EntityTemplate<Z>> refList() = RefBuilder<List<E>>(isSimpleType = false, isList = true)
    inline fun <reified Z> list() = ListBuilder<List<Z>>(isSimpleType = true, isList = true)

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

    inline infix fun <reified OUT_TYPE> ListBuilder<OUT_TYPE>.genList(
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

    fun stringRef() = PropBuilder<String>(isSimpleType = false, isList = false)

    inline infix fun <reified OUT_TYPE> RefBuilder<OUT_TYPE>.genRef(
        crossinline f: GenerateValueFunction<EntityTemplate<ID_TYPE>, OUT_TYPE>
    ): PropBuilder<OUT_TYPE> {
        this.function =
            { idVal, parameterName ->
                val f1 = f(this@EntityTemplate, parameterName)
                f1 as DataType<OUT_TYPE>
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

    open inner class PropBuilder<R>(
        var name: FieldName = "",
        var function: GenerateValueFunction<ID_TYPE, DataType<R>> = { _, _ ->
            error("Необходимо определить ф-цию в мете для поля $name ")
        },
        val isSimpleType: Boolean,
        val isList: Boolean
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

        override fun build(): MetaProperty<ID_TYPE, R> = MetaProperty(name, function, isSimpleType, isList)
    }

    inner class RefBuilder<R>(
        name: FieldName = "",
        function: GenerateValueFunction<ID_TYPE, DataType<R>> = { _, _ ->
            error("Необходимо определить ф-цию в мете для поля $name ")
        },
        isSimpleType: Boolean,
        isList: Boolean
    ) : PropBuilder<R>(name, function, isSimpleType, isList)

    open inner class ListBuilder<R>(
        name: FieldName = "",
        function: GenerateValueFunction<ID_TYPE, DataType<R>> = { _, _ ->
            error("Необходимо определить ф-цию в мете для поля $name ")
        },
        isSimpleType: Boolean,
        isList: Boolean
    ) : PropBuilder<R>(name, function, isSimpleType, isList)

}


