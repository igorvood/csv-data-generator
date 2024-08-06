package ru.vood.bigdata.loadercsv.dto

import jakarta.persistence.*
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

//    @EmbeddedId
    @Id
    @Column(name = "id")
    lateinit var  identity: String
    @Column
    lateinit var name: String
    @Column
    var salary: Long=0

    @Column(name = "isworker")
    var isWorker: Boolean=false
    @Column(name = "ismarried")
    var isMarried: Boolean=false

    @Column(name = "birthdate")
    lateinit var birthDate: LocalDateTime

}