package ru.vood.bigdata.generatorcsv.gen

import ru.vood.bigdata.generatorcsv.gen.dsl.*
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

    protected open fun customSerializers(): Set<SerialData<ID_TYPE, Any>> = setOf()

    val customSerializable by lazy {
        customSerializers().associate { it.prop.name to { it.f(it.prop.get()(id())!!) } }
    }

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

    fun string(f: GenerateValueFunction<ID_TYPE, String>) =
        PropBuilder(
            isSimpleType = true,
            isList = false,
            entityName = this::class.java.canonicalName,
            function = { idVal, parameterName ->
                object : DataType<String> {
                    override fun invoke(): String = f(idVal, parameterName)
                }
            })

    fun number(f: GenerateValueFunction<ID_TYPE, BigDecimal>) =
        PropBuilder(
            isSimpleType = true,
            isList = false,
            entityName = this::class.java.canonicalName,
            function = { idVal, parameterName ->
                object : DataType<BigDecimal> {
                    override fun invoke(): BigDecimal = f(idVal, parameterName)
                }
            })

    fun date(f: GenerateValueFunction<ID_TYPE, LocalDateTime>) =
        PropBuilder(
            isSimpleType = true,
            isList = false,
            entityName = this::class.java.canonicalName,
            function = { idVal, parameterName ->
                object : DataType<LocalDateTime> {
                    override fun invoke(): LocalDateTime = f(idVal, parameterName)
                }
            }
        )

    fun bool(f: GenerateValueFunction<ID_TYPE, Boolean>) =
        PropBuilder(
            isSimpleType = true,
            isList = false,
            entityName = this::class.java.canonicalName,
            function = { idVal, parameterName ->
                object : DataType<Boolean> {
                    override fun invoke(): Boolean = f(idVal, parameterName)
                }
            }
        )

    inline fun <reified Z, E : EntityTemplate<Z>> ref() =
        RefBuilder<E>(isSimpleType = false, isList = false, entityName = this::class.java.canonicalName)

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

    inline fun <reified Z, E : EntityTemplate<Z>> refList() =
        RefBuilder<List<E>>(isSimpleType = false, isList = true, entityName = this::class.java.canonicalName)

    inline fun <reified Z> list() =
        ListBuilder<List<Z>>(isSimpleType = true, isList = true, entityName = this::class.java.canonicalName)


    inline infix fun <reified OUT_TYPE> RefBuilder<List<OUT_TYPE>>.genListRef(
        crossinline f: GenerateValueFunction<EntityTemplate<ID_TYPE>, List<OUT_TYPE>>
    ): PropBuilder<List<OUT_TYPE>> {
        this.function =
            { idVal, parameterName ->
                val value = object : DataType<List<OUT_TYPE>> {
                    override fun invoke(): List<OUT_TYPE> = f(this@EntityTemplate, parameterName) as List<OUT_TYPE>
                }
//                val f1 = f(this@EntityTemplate, parameterName)
                value
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


    val genBoolDefaultF: GenerateValueFunction<ID_TYPE, Boolean> = { id, parameterName ->
        val hc = abs(id.hashCode())
        hc % 2 == 1
    }

    fun PropBuilder<Boolean>.genBoolDefault(
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
        var fieldName: FieldName = "",
        var function: GenerateValueFunction<ID_TYPE, DataType<R>> = { _, _ ->
            error("Необходимо определить ф-цию в мете для поля $fieldName ")
        },
        val isSimpleType: Boolean,
        val isList: Boolean,
        var entityName: EntityName,
    ) : Builder<MetaProperty<ID_TYPE, R>> {

        operator fun provideDelegate(
            thisRef: EntityTemplate<ID_TYPE>,
            property: KProperty<*>
        ): ReadOnlyProperty<EntityTemplate<ID_TYPE>, MetaProperty<ID_TYPE, R>> {
            fieldName = property.name
            val build: MetaProperty<ID_TYPE, R> = this@PropBuilder.build()
            thisRef.addProp(build)
            return ReadOnlyProperty { thisRef, property ->
                return@ReadOnlyProperty build
            }

        }

        override fun build(): MetaProperty<ID_TYPE, R> =
            MetaProperty(entityName, fieldName, function, isSimpleType, isList)
    }

    inner class RefBuilder<R>(
        fieldName: FieldName = "",
        function: GenerateValueFunction<ID_TYPE, DataType<R>> = { _, _ ->
            error("Необходимо определить ф-цию в мете для поля $fieldName ")
        },
        isSimpleType: Boolean,
        isList: Boolean,
        entityName: EntityName,
    ) : PropBuilder<R>(fieldName, function, isSimpleType, isList, entityName)

    open inner class ListBuilder<R>(
        name: FieldName = "",
        function: GenerateValueFunction<ID_TYPE, DataType<R>> = { _, _ ->
            error("Необходимо определить ф-цию в мете для поля $name ")
        },
        isSimpleType: Boolean,
        isList: Boolean,
        entityName: EntityName,
    ) : PropBuilder<R>(name, function, isSimpleType, isList, entityName)

}


