package ru.vood.bigdata.generatorcsv.gen.dsl

import ru.vood.bigdata.generatorcsv.gen.DataType
import ru.vood.bigdata.generatorcsv.gen.EntityTemplate

data class MetaEntity<ID_TYPE>
//        where T: EntityTemplate<Q>
    (
    val name: EntityName,
    val property: Set<MetaProperty<ID_TYPE, *>>,
    val ck: Set<MetaCheck<EntityTemplate<ID_TYPE>>> = setOf(),
    val fk: Set<MetaFk<EntityTemplate<ID_TYPE>>> = setOf(),
)

data class MetaProperty<ID_TYPE, OUT_TYPE>(
    val entityName: EntityName,
    val paramName: FieldName,
    val function: GenerateValueFunction<ID_TYPE, DataType<OUT_TYPE>>,
    val isSimpleType: Boolean,
    val isList: Boolean,
) : (ID_TYPE) -> OUT_TYPE {
    override fun invoke(p1: ID_TYPE): OUT_TYPE = function(p1, paramName)()
}

data class MetaCheck<T>(
    val name: ConstraintName,
    val checkFunction: (T) -> Boolean
)

data class MetaFk<T>(
    val name: ConstraintName
)
