//package com.android.cuvvatest.repositories.policies
//
//import androidx.room.*
//
//@Dao
//interface EventDao {
//    @Insert
//    fun insertEntity(eventEntity: EventEntity)
//
//    @Query("DELETE FROM eventTable")
//    fun deleteAll()
//
//    @Transaction
//    fun deleteAndInsert(eventList: List<EventEntity>) {
//        deleteAll()
//        eventList.forEach { insertEntity(it) }
//    }
//}