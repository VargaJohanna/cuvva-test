package com.android.cuvvatest.repositories.vehicle

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface VehicleDao {
    @Insert
    fun insertVehicle(vehicleEntity: VehicleEntity)

    @Query("SELECT vrm FROM vehicleTable")
    fun loadVehicleAndEvents(): Single<List<VehicleAndAllPolicyIds>>
}