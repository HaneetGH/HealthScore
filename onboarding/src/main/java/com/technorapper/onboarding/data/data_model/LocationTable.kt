package com.technorapper.onboarding.data.data_model

import androidx.annotation.NonNull
import androidx.databinding.BaseObservable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locationMaster" + "")
data class LocationTable(

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Long,


    @ColumnInfo(name = "Lat")
    var Lat: Double,

    @ColumnInfo(name = "Lng")
    var Lng: Double,
    @ColumnInfo(name = "address")
    var address: String,
    @ColumnInfo(name = "city")
    var city: String


) : BaseObservable() {
    constructor() : this(
        0, 0.0, 0.0, "",""
    )

}