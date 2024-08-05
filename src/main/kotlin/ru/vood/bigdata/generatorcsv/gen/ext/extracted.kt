package ru.vood.bigdata.generatorcsv.gen.ext

import kotlinx.coroutines.flow.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.vood.bigdata.generatorcsv.gen.EntityTemplate
import java.io.File



suspend inline fun <reified ID_TYPE> extracted(
    flow: Flow<EntityTemplate<ID_TYPE>>,
    file: File,
    crossinline f: (EntityTemplate<ID_TYPE>) -> String
) {
    val log = LoggerFactory.getLogger(ID_TYPE::class.java)
    val everyRecordLog: Int = 10000
    var num: Long = 0
    file.printWriter().use { out ->
        flow
            .map { cl -> f(cl) }
            .collect {

                out.println(it)

                num += 1
                if ((num % everyRecordLog).toInt() == 0) {
                    log.info("Processed $num records")
                }
            }
    }
}

 fun <ID_TYPE> extractedL(
    flow: List<EntityTemplate<ID_TYPE>>,
    file: File,
    f: (EntityTemplate<ID_TYPE>) -> String,
//    cl: Logger
) {
    val printWriter = file.printWriter()
    val log: Logger = LoggerFactory.getLogger(EntityTemplate::class.java)
    val everyRecordLog: Int = 10000
    var num: Long = 0
     printWriter.use { out ->
         for (el in flow) {
             val f1 = f(el)

             out.println(f1)

             num += 1
             if ((num % everyRecordLog).toInt() == 0) {
                 log.info("Processed $num records")
             }
         }
     }
}