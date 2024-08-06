package ru.vood.bigdata.loadercsv.dto.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.vood.bigdata.loadercsv.dto.Client

@Repository
interface ClientRepository: JpaRepository<Client, String> {
}