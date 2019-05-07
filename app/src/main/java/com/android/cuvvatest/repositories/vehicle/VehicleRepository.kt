package com.android.cuvvatest.repositories.vehicle

import com.android.cuvvatest.model.Vehicle
import io.reactivex.Observable

interface VehicleRepository {
    fun getVehicleByVrm(vrm: String): Observable<Vehicle>
}