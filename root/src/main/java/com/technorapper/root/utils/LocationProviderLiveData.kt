package com.technorapper.root.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import com.google.android.gms.location.*
import java.util.concurrent.TimeUnit

class LocationProviderLiveData private constructor(var context: Context) : LiveData<Location>() {
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private val locationRequest = LocationRequest()
        .setInterval(TimeUnit.SECONDS.toMillis(10L))
        .setFastestInterval(TimeUnit.SECONDS.toMillis(5L))
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            var location = result?.locations?.firstOrNull()
            if (location != null) {
                if (result?.locations?.size!! > 1) {
                    for (i in 0 until result.locations.size) {
                        location = returnBestLocation(result.locations[i], location!!)
                    }
                }
                value = location!!
            } else {
                value = result?.lastLocation
            }
        }
    }

    init {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        } else
            mFusedLocationProviderClient?.lastLocation?.addOnSuccessListener {
                value = it
            }
    }


    override fun onInactive() {
        super.onInactive()
        mFusedLocationProviderClient?.removeLocationUpdates(mLocationCallback)
    }

    fun getMeLocation(): LocationProviderLiveData {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        } else
            mFusedLocationProviderClient!!.requestLocationUpdates(
                locationRequest,
                mLocationCallback,
                Looper.myLooper()!!
            )
        return this
    }


    private fun returnBestLocation(l1: Location, l2: Location): Location {
        return if (l1.accuracy > l2.accuracy) {
            l1
        } else {
            l2
        }

    }

    companion object {
        @Volatile
        private var INSTANCE: LocationProviderLiveData? = null

        @JvmStatic
        fun getInstance(context: Context): LocationProviderLiveData =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildLocationUpdateListener(context).also { INSTANCE = it }
            }

        private fun buildLocationUpdateListener(context: Context) =
            LocationProviderLiveData(context)
    }

}