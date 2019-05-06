package com.android.cuvvatest.repositories.policies

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface EventDao {
    @Insert
    fun insertEntity(eventEntity: EventEntity)
}