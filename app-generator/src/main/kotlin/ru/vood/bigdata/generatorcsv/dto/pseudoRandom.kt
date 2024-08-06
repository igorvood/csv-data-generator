package ru.vood.bigdata.generatorcsv.dto


val symbolsDefault: String = "0123456789ABCDEFGHIJKLMNOPQRSTUVWYZabcdefghijklmnopqrstuvwyz"
val length = symbolsDefault.length
fun pseudoRandom(hash: Int, cnt: Int, symbols: String = symbolsDefault): String {
    return (0 until cnt).map {
        val n = (hash shr it) + (hash.toString() + it).hashCode()
        val abs = kotlin.math.abs(n)
        val length1 = symbols.length
        val i = abs % length1
        try {
            symbols[i]
        } catch (e: Exception) {
// Для минимального Int не корректно работает abs он возвращает этот же отрицательный Int
            symbols[kotlin.math.abs(abs % length1)]
        }
    }.joinToString("")
}
