package com.android.cuvvatest.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.cuvvatest.repositories.policies.cancelled.CancelledPolicyDao
import com.android.cuvvatest.repositories.policies.cancelled.CancelledPolicyEntity
import com.android.cuvvatest.repositories.policies.created.CreatedPolicyDao
import com.android.cuvvatest.repositories.policies.created.CreatedPolicyEntity
import com.android.cuvvatest.repositories.policies.paid.PaidPolicyDao
import com.android.cuvvatest.repositories.policies.paid.PaidPolicyEntity
import com.android.cuvvatest.repositories.vehicle.VehicleDao
import com.android.cuvvatest.repositories.vehicle.VehicleEntity

@Database(
    entities = [
        VehicleEntity::class,
        CreatedPolicyEntity::class,
        PaidPolicyEntity::class,
        CancelledPolicyEntity::class], version = 1, exportSchema = true
)
abstract class PolicyDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
    abstract fun createdPolicyDao(): CreatedPolicyDao
    abstract fun cancelledPolicyDao(): CancelledPolicyDao
    abstract fun paidPolicyDao(): PaidPolicyDao

    companion object {
        fun getInstance(context: Context): PolicyDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                PolicyDatabase::class.java, "policydatabase.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}