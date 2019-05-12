package com.android.cuvvatest.repositories.vehicle

import androidx.room.*
import io.reactivex.Observable

@Dao
interface VehicleDao {
    @Insert
    fun insertVehicle(vehicleEntity: VehicleEntity)

    @Transaction
    @Query("SELECT vrm FROM vehicleTable")
    fun loadVehicleAndEvents(): Observable<List<VehicleAndAllCreatedPolicyEntities>>

    @Query("DELETE FROM vehicleTable")
    fun deleteAll()

    @Transaction
    fun deleteAndInsert(vehicleList: List<VehicleEntity>) {
        deleteAll()
        vehicleList.forEach { insertVehicle(it) }
    }

    @Query("SELECT * FROM vehicleTable WHERE vrm = :vrm")
    fun getVehicleByVrm(vrm: String): Observable<VehicleEntity>
}