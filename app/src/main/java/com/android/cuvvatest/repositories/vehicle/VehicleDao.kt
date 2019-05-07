package com.android.cuvvatest.repositories.vehicle

import androidx.room.*
import io.reactivex.Single

@Dao
interface VehicleDao {
    @Insert
    fun insertVehicle(vehicleEntity: VehicleEntity)

    @Query("SELECT vrm FROM vehicleTable")
    fun loadVehicleAndEvents(): Single<List<VehicleAndAllPolicyIds>>

    @Query("DELETE FROM vehicleTable")
    fun deleteAll()

    @Transaction
    fun deleteAndInsert(vehicleList: List<VehicleEntity>) {
        deleteAll()
        vehicleList.forEach { insertVehicle(it) }
    }
}