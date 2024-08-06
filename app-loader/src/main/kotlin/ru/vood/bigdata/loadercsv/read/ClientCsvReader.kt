package ru.vood.bigdata.loadercsv.read

import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Service
import ru.vood.bigdata.loadercsv.dto.Client
import java.io.InputStream
import java.time.LocalDateTime

@Service
class ClientCsvReader {
    fun readCsv(inputStream: InputStream)= flow {
        val reader = inputStream.bufferedReader()
//        val header = reader.readLine()

        for ( line in reader.lineSequence()){
            if (line.isNotBlank()){
                val split = line.split(';', ignoreCase = false, limit = 6)
                val birthDate = split[0]
                val identity = split[1]
                val isWorker = split[2]
                val isMarried = split[3]
                val name = split[4]
                val salary = split[5]
                emit(Client(identity, name, salary.toLong(),isWorker.toBoolean(), isMarried.toBoolean(), LocalDateTime.parse(birthDate)))
            }
        }
    }
}