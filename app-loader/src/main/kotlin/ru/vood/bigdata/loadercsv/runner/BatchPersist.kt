package ru.vood.bigdata.loadercsv.runner

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.vood.bigdata.loadercsv.dto.Client

@Service
open class BatchPersist(
    private val  entityManagerFactory: EntityManagerFactory,
    private val  entityManager: EntityManager
) {

    val logger = LoggerFactory.getLogger(BatchPersist::class.java)

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    open fun <T>extracted(
        clients: List<T>,
    ) {
//        val entityManager: EntityManager = entityManagerFactory.createEntityManager()
        clients.forEach { d -> entityManager.persist(d) }
        logger.info(clients.size.toString())
        logger.info("end")
        entityManager.flush()

    }
}