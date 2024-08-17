package ru.vood.bigdata.generatorcsv.util


inline fun <reified A : Any> A.absHashCode(): Long {
    val n = this.hashCode()
    return if (n < 0) {
        (-n.toLong()) * 2L
    } else n.toLong()

}
