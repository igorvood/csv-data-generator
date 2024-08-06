package ru.vood.bigdata.loadercsv.dto

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "client")
public class Client(){

//    constructor(  identity: String,
//         name: String,
//     salary: Long,
//isWorker: Boolean,
//     isMarried: Boolean,
// birthDate: LocalDateTime){
//
//    }

    @EmbeddedId
    lateinit var  identity: String
    @Column
    lateinit var name: String
    @Column
    var salary: Long=0

    @Column
    var isWorker: Boolean=false
    @Column
    var isMarried: Boolean=false

    @Column
    lateinit var birthDate: LocalDateTime

}