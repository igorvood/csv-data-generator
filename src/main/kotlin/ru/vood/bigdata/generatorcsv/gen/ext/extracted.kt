package ru.vood.bigdata.generatorcsv.gen.ext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import org.slf4j.LoggerFactory
import ru.vood.bigdata.generatorcsv.gen.EntityTemplate
import java.io.File



suspend inline fun <reified ID_TYPE> extracted(
    flow: Flow<EntityTemplate<ID_TYPE>>,
    file: File,
    crossinline f: (EntityTemplate<ID_TYPE>) -> String
) {
    val log = LoggerFactory.getLogger(ID_TYPE::class.java)
    val everyRecordLog: Int = 2
    var num: Long = 0
    flow
        .map { cl -> f(cl) }
        .collect {
            file.printWriter().use { out ->
                out.println(it)
            }
            num += 1
            if ((num % everyRecordLog).toInt() == 0){
                log.info("Processed $num records")
            }
        }
}