package ru.vood.bigdata.loadercsv.dto.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.vood.bigdata.loadercsv.dto.Client

@Repository

interface ClientRepository: JpaRepository<Client, String> {
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    override fun <S : Client> saveAll(entities: MutableIterable<S>): MutableList<S>
}