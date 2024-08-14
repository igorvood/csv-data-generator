package ru.vood.bigdata.generatorcsv.gen

interface DataType<out T> : () -> T
{
    fun isBoolean(): Boolean = this() is Boolean

    fun isNumber(): Boolean = this() is Number


}