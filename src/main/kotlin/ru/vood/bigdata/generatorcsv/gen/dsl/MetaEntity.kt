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
    val paramName: FieldName,
    val function: GenerateValueFunction<ID_TYPE, DataType<OUT_TYPE>>
) : (ID_TYPE) -> OUT_TYPE
//,Comparable<MetaProperty<ID_TYPE, OUT_TYPE>>/* Comparator<MetaProperty<ID_TYPE, OUT_TYPE>>*/
{
    override fun invoke(p1: ID_TYPE): OUT_TYPE = function(p1, paramName)()
    /* override fun compareTo(other: MetaProperty<ID_TYPE, OUT_TYPE>): Int {
         return this.name.compareTo(other.name)
     }*/

}

data class MetaCheck<T>(
    val name: ConstraintName,
    val checkFunction: (T) -> Boolean
)

data class MetaFk<T>(
    val name: ConstraintName
)
