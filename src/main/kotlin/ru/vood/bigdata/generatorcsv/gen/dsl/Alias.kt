package ru.vood.bigdata.generatorcsv.gen.dsl

import ru.vood.bigdata.generatorcsv.gen.DataType
import ru.vood.bigdata.generatorcsv.gen.EntityTemplate

typealias GenerateIdValueFunction<T> = () -> DataType<T>
typealias GenerateFieldValueFunction<ID_TYPE, OUT_TYPE> = (EntityTemplate<ID_TYPE>, String) -> OUT_TYPE
typealias GenerateFieldValueFunctionDsl<ID_TYPE, OUT_TYPE> = (EntityTemplate<ID_TYPE>, String) -> OUT_TYPE
typealias GenerateFieldCheckFunction<ID_TYPE> = (EntityTemplate<ID_TYPE>, String) -> Boolean

typealias ConstraintName = String
typealias FieldName = String
typealias EntityName = String


