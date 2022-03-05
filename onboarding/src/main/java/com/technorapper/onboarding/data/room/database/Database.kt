package com.technorapper.onboarding.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.technorapper.onboarding.data.data_model.LocationTable
import com.technorapper.onboarding.data.room.database.dao.*
import com.whide.partner.data.repository.utils.RoomConverters


import com.whide.partner.networking.tunnel.database.migration.Migration1To2

@Database(
    entities = [LocationTable::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    RoomConverters::class
)

abstract class Database : RoomDatabase() {

    abstract fun getLocationMaster(): LocationDao


    companion object {
        val MIGRATION_1_2 = Migration1To2()
    }
}


