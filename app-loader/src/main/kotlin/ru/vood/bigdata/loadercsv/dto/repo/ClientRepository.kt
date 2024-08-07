package ru.vood.bigdata.loadercsv.dto.repo

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import ru.vood.bigdata.loadercsv.dto.Client

@Repository

interface ClientRepository: JpaRepository<Client, String>, PagingAndSortingRepository<Client, String> {

//    fun findAllByPrice(price: Double, pageable: Pageable): List<Client>
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    override fun <S : Client> saveAll(entities: MutableIterable<S>): MutableList<S>

//    @Query(value = """SELECT c from Client c
//                        where (c.isWorker=?1 or ?1 is null)
//                            and (c.isMarried=?2 or ?2 is null)""")
    @Query(value = """SELECT c from Client c  
                        where (c.isWorker=?1 or ?1 is null ) 
                            and (c.isMarried=?2 or ?2 is null)
                            and (c.name=?3 or ?3 is null)
                            """,
//        nativeQuery = true
    )
    fun fi(isWorker: Boolean?, isM: Boolean? , name: String?, pageable: Pageable): List<Client>
}