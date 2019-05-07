
package com.android.cuvvatest.repositories.vehicle

import com.android.cuvvatest.model.Vehicle
import com.android.cuvvatest.repositories.policies.created.CreatedPolicyRepository
import io.reactivex.Observable
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

class VehicleRepositoryImpl(
    private val vehicleDao: VehicleDao
) : VehicleRepository {
    override fun getVehicleByVrm(vrm: String): Observable<Vehicle> {
        return vehicleDao.getVehicleByVrm(vrm)
            .map {
                mapVehicle(it)
            }
    }

    private fun mapVehicle(entity: VehicleEntity) : Vehicle {
        return Vehicle(
            vrm = entity.vrm,
            prettyVrm = entity.prettyVrm,
            make = entity.make,
            color = entity.color,
            model = entity.model,
            updated = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(entity.updated), ZoneOffset.UTC)
        )
    }

}