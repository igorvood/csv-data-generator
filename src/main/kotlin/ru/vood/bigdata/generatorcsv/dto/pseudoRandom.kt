package ru.vood.bigdata.generatorcsv.dto


val symbols: String = "0123456789ABCDEFGHIJKLMNOPQRSTUVWYZabcdefghijklmnopqrstuvwyz"
val length = symbols.length
fun pseudoRandom(hash: Int, cnt: Int): String {
    return (0 until cnt).map {
        val n = (hash shr it) + (hash.toString() + it).hashCode()
        val abs = kotlin.math.abs(n)
        val i = abs % length
        try {
            symbols[i]
        } catch (e: Exception) {
// Для минимального Int не корректно работает abs он возвращает этот же отрицательный Int
            symbols[kotlin.math.abs(abs % length)]
        }
    }.joinToString("")
}
