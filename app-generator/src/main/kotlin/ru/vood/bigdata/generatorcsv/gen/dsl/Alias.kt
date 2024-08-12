package ru.vood.bigdata.generatorcsv.gen.dsl

typealias GenerateValueFunction<ID_TYPE, OUT_TYPE> = (id: ID_TYPE, paramName: String) -> OUT_TYPE

typealias ConstraintName = String
typealias FieldName = String
typealias EntityName = String


